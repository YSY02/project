package lab;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class 求SHA256Demo {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //MD5 (快被淘汰，因为没有 SHA-256安全)
        MessageDigest messageDigest=MessageDigest.getInstance("SHA-256");
        String s="你好世界";
        byte[] bytes=s.getBytes("UTF-8");
        messageDigest.update(bytes);//先放进去（此方法要求传入一个字节数组，所以要把字符串进行转换）
        byte[] result=messageDigest.digest();//通过执行最后的操作（如填充）来完成哈希计算
        System.out.println(result.length);
        for(byte b:result){
            System.out.printf("%02x",b);//UTF-8一个字节占两位,一个字节一个字节打 十六进制
        }
        System.out.println();
    }
}
