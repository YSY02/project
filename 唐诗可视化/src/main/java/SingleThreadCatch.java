import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomText;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SingleThreadCatch {
    // 1.获取列表页
    // 2.获取详情页
    // 3.sha256（用于计算每一首诗的特殊编号，避免重复新插入数据库）
    // 4.分词（用于项目后期的计算词语出现个数功能）
    // 5.将数据插入数据库


    public static void main(String[] args) throws Exception {
        //创造无界面的浏览器
        WebClient client=new WebClient(BrowserVersion.CHROME);
        //禁用脚本和样式
        //关闭了浏览器中的js执行引擎
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        String baseUrl="https://so.gushiwen.org";
        String pathUr1="/gushi/tangshi.aspx";

        List<String> detailUrlList=new ArrayList<>();
        //列表页的解析
        {
            String url=baseUrl+pathUr1;
            HtmlPage page=client.getPage(url);
            //getElementsByAttribute:通过属性名称 查找当前html下的所有元素
            List<HtmlElement> divs=page.getBody().getElementsByAttribute(
                    "div",
                    "class",
                    "typecont");
            System.out.println ("获取到的每首唐诗的url：");

            for (HtmlElement div:divs){
                System.out.println(div.getAttribute ("href"));
                List<HtmlElement> as=div.getElementsByTagName("a");//a标签
                for (HtmlElement a:as){
                    String detailUrl=a.getAttribute("href");
                    detailUrlList.add(baseUrl+detailUrl);
                }
            }
        }

        MysqlConnectionPoolDataSource dataSource=new MysqlConnectionPoolDataSource();
        dataSource.setServerName("127.0.0.1");//设置服务器
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("123456789");
        dataSource.setDatabaseName("mytangshi");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF8");
        Connection connection=dataSource.getConnection();

        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");

        String sql="INSERT INTO tangshi " +
                "(sha256, dynasty, title, author, " +
                "content, words) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement=connection.prepareStatement(sql);

        //详情页的请求和解析
        for (String url:detailUrlList){
            HtmlPage page=client.getPage(url);
            String xpath;
            DomText domText;

            xpath="//div[@class='cont']/h1/text()";
            domText=(DomText)page.getBody().getByXPath(xpath).get(0);
            String title=domText.asText();

            xpath="//div[@class='cont']/p[@class='source']/a[1]/text()";
            domText=(DomText)page.getBody().getByXPath(xpath).get(0);
            String dynasty=domText.asText();

            xpath="//div[@class='cont']/p[@class='source']/a[2]/text()";
            domText=(DomText)page.getBody().getByXPath(xpath).get(0);
            String author=domText.asText();

            xpath="//div[@class='cont']/div[@class='contson']";
            HtmlElement element=(HtmlElement)page.getBody().getByXPath(xpath).get(0);
            String content=element.getTextContent().trim();

            //1.计算sha256
            String s=title+content;
            messageDigest.update(s.getBytes("UTF-8"));
            byte[] result=messageDigest.digest();//digest():通过执行最后的操作（如填充）来完成哈希计算
            StringBuilder sha256=new StringBuilder();
            for (byte b:result) {
                sha256.append(String.format("%02x", b));
            }
            //2.计算分词
            List<Term> termList=new ArrayList<>();
            termList.addAll(NlpAnalysis.parse(title).getTerms());//把标题中的词加进去
            termList.addAll(NlpAnalysis.parse(content).getTerms());//把正文中的词加进去
            List<String> words=new ArrayList<>();
            for (Term term:termList){
                if (term.getNatureStr().equals("w")) {//w：标点符号
                    continue;
                }
                if (term.getNatureStr().equals("null")) {//特殊字符会返回字符串“null”
                    continue;
                }
                if (term.getNatureStr().length()<2) {
                    continue;
                }
                words.add(term.getRealName());//分词加入words中
            }
            String insertWords=String.join(",",words);//把词用，连接起来

            statement.setString(1,sha256.toString());
            statement.setString(2,dynasty);
            statement.setString(3,title);
            statement.setString(4,author);
            statement.setString(5,content);
            statement.setString(6,insertWords);//分好的词

            com.mysql.jdbc.PreparedStatement mysqlStatement=(com.mysql.jdbc.PreparedStatement)statement;
            System.out.println(mysqlStatement.asSql());
            statement.executeUpdate();
            System.out.println(title+"插入成功");
        }
    }
}
