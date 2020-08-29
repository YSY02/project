package lab;
import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class 插入诗词Demo {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String 朝代="唐代";
        String 作者="白居易";
        String 标题="问刘十九";
        String 正文="绿蚁新醅酒，红泥小火炉。晚来天欲雪，能饮一杯无？";

        /*
        //获取connection的第一种方法
        //1.注册Driver
         Class.forName("com.mysql.jdbc.Driver");
        //2.通过DriverManager获取Connection
        String url= "jdbc:mysql://127.0.0.1/tangshi?useSSL=false&characterEncoding=utf8";
        Connection connection=DriverManager.getConnection(url,"root","2240535436");
        System.out.println(connection);
        Statement statement=connection.createStatement();
        String sql="insert into tangshi(sha256,dynasty,author,content,words) values()";
        statement.executeUpdate(sql);
        */

        //通过DataSource获取Connection
        //不带有连接池
        //DataSource dataSource1=new MysqlDataSource();
        //带有连接池
        //MysqlConnectionPoolDataSource dataSource2=new MysqlConnectionPoolDataSource();

        //获取connection的第二种方法
        Class.forName("com.mysql.jdbc.Driver");
        //DataSource dataSource1=new MysqlDataSource();
        MysqlConnectionPoolDataSource dataSource=new MysqlConnectionPoolDataSource();
        dataSource.setServerName("127.0.0.1");//设置服务器
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("123456789");
        dataSource.setDatabaseName("tangshi");
        dataSource.setUseSSL(false);
        dataSource.setCharacterEncoding("UTF8");

        try(Connection connection=dataSource.getConnection()){  //拿到连接
            String sql="insert into tangshi(sha256,dynasty,author,title,content,words) values(?,?,?,?,?,?)"; //占位符

            //另一种插入方式
            //Statement statement=connection.createStatement();
            //String sql="insert into tangshi(sha256, dynasty, title, author,content, words)"
            //statement.executeUpdate(sql);
            try(PreparedStatement statement=connection.prepareStatement(sql)){
                statement.setString(1,"sha256");
                statement.setString(2, 朝代);
                statement.setString(3, 标题);
                statement.setString(4, 作者);
                statement.setString(5, 正文);
                statement.setString(6, "");

                statement.executeUpdate();//插入
            }
        }
    }
}
