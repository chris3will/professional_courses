import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class IterativeSignInServer {
    public static void main(String[] args) throws IOException{
        int portNumber = 12001;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);

            while(true)
            {
                Socket clientSocket = serverSocket.accept();

                //打开sock输入输出流
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  //在server进程这边为client创建你的对应的进程

                //读取来自客户端的字符串
                String inputLine = "Sign in - "+reader.readLine() + " at " + (new Date());
                System.out.println(inputLine);
                //需要在这里，进行操作之前，判断是否有需要accept的对象？
                Thread.sleep(10000);  //模拟服务器的处理时间

                writer.println(inputLine);

            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            serverSocket.close();
        }
    }


}

