package lab;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Java中读取配置 {
    public static void main(String[] args) throws IOException {
        //相对于编译出的类所在的根路径找    相对路径
        InputStream is=Java中读取配置.class.getClassLoader()
                .getResourceAsStream("some.properties");
        Properties properties=new Properties();
        properties.load(is);
        String v=properties.getProperty("mysql.host");
        System.out.println(v);
    }
}
