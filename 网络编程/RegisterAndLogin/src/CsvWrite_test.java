import java.io.*;

public class CsvWrite_test {
    public final String filePath= "E:\\thridSpring\\网络编程\\RegisterAndLogin\\user.csv";

    public  void write(String[] args) throws IOException {
        try{
            File csv = new File(filePath);

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));
            //添加新的数据行
            bw.write("ss"+","+"123");
            bw.newLine();  //写入换行符
            bw.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }


    }
}
