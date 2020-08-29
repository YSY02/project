package lab;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.File;
import java.io.IOException;
import java.util.List;

//请求列表页，并解析出详情页的URL和数量
public class HtmlUnitDemo {
    public static void main(String[] args) throws IOException {
        // 无界面的浏览器(HTTP 客户端)
        WebClient webClient = new WebClient(BrowserVersion.CHROME);//创建对象
        // 关闭了浏览器的 js 执行引擎，不再执行网页中的 js 脚本
        webClient.getOptions().setJavaScriptEnabled(false);
        // 关闭了浏览器的 css 执行引擎，不再执行网页中的 css 布局
        webClient.getOptions().setCssEnabled(false);
        HtmlPage page = webClient.getPage("https://so.gushiwen.org/gushi/tangshi.aspx");//获取页面
        System.out.println(page);//System.out.println(page.asText());-->获取页面所有文本信息

        System.out.printf("-----------------------------------------------------------------");

        File file = new File("唐诗三百首\\列表页.html");
        file.delete();
        //page.save(file);
        page.save(new File("唐诗三百首\\列表页.html"));

        // 如何从 html 中提取我们需要的信息
        HtmlElement body = page.getBody();//获取html文件中的body标签中的内容
        List<HtmlElement> elements = body.getElementsByAttribute(
                "div",
                "class",//在body标签的内容中，获取div标签中，class属性为typecont的元素
                "typecont");
        for (HtmlElement element : elements) {
            System.out.println(element);
        }
        /*
        HtmlDivision[<div class="typecont">]    //五言绝句
        HtmlDivision[<div class="typecont">]    //七言绝句
        HtmlDivision[<div class="typecont">]
        HtmlDivision[<div class="typecont">]
        */
        System.out.println("--------------------------------------------------------------------------------");
        HtmlElement divElement = elements.get(0);//取第一个模块
        List<HtmlElement> aElements = divElement.getElementsByAttribute(
                "a",
                "target",
                "_blank");
        for (HtmlElement e : aElements) {
            System.out.println(e);
        }
        System.out.println(aElements.size());
        System.out.println(aElements.get(0).getAttribute("href"));

    }

}
