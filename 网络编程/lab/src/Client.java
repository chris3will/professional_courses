import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class Client {

    public static void main(String[] args) throws IOException{
        String hostName = "127.0.0.1";  //目标ip地址
        int portNumber = 12001; //目标svr端口号
        Socket clientSocket = null;

        try{
            clientSocket = new Socket(hostName, portNumber);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                    System.in));  //键盘的标准输入

            //向svr签到
            writer.println(stdIn.readLine());  //写入的东西来自键盘的标准输入，运行之后会发现控制台中有光标在等待
            //读取svr返回的签到结果


            System.out.println("server answers: "+reader.readLine());  //返回客户端的确认信息
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
