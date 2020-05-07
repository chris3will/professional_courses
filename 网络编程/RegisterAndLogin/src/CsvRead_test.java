import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CsvRead_test {
    public  String filePath= "E:\\thridSpring\\网络编程\\RegisterAndLogin\\user.csv";
    public CsvRead_test(String filePath){
        this.filePath = filePath;
    }

    public void read() throws IOException{
       try{
           BufferedReader reader = new BufferedReader(new FileReader(filePath));
           reader.readLine(); //先将标题的一行读取完毕
           String line = null;  //每一次当前行的缓冲区
           while((line=reader.readLine())!=null){
               String item[] = line.split(",");
               if(item.length!=2)break;
               //System.out.println(item.length+" 被分割长长度");
               String zhanghao = item[0];
               String mima = item[1];
               //int value = Integet.parseInt(last);
               System.out.println(zhanghao+","+mima);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }
}
