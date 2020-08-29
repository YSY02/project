package lab;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import java.util.List;

//查询每个作者的诗作数量，
//获取字的使用频率
public class 分词Demo {
    public static void main(String[] args) {
        String sentence="中华人民共和国今天成立了！中国人民从此站起来了";
        List<Term> termList=NlpAnalysis.parse(sentence).getTerms();//可认为一个terms就是一个词
        for(Term term:termList){
            //词性：字
            System.out.println(term.getNatureStr()+":"+term.getRealName());
        }
    }
    /**
        ns:中华人民共和国
        t:今天
        v:成立
        u:了
        w:！
        ns:中国
        n:人民
        d:从此
        v:站
        v:起来
        u:了
     */
}
