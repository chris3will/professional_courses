import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//这个是总结CSDN与老师课堂提供的迭代服务器组合的代码，初步测试有效，等待更多的情形来验证
//目前来说这个多线程部分的原理不太清晰，但是大概能懂
//java控制输入输出的容器有很多，在这里就和老师课堂上保持一致的即可
public class tryServer {
    public static void main(String[] args) throws IOException{
        int portNumber = 12001;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);
            Executor executor = Executors.newFixedThreadPool(100);
            while(true)
            {
                try{
                    Socket connection = serverSocket.accept();

                    //启动线程池
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            Socket conn = connection;

                            try{
                                PrintWriter writer = new PrintWriter(conn.getOutputStream(),true);
                                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                                //读取来自客户端的字符串
                                String inputLine = "Sign in - " +reader.readLine() + " at "+(new Date());
                                System.out.println(inputLine);

                                Thread.sleep(10*100);

                                writer.println(inputLine);

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch(IOException e){
                    e.printStackTrace();
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            serverSocket.close();
        }

    }

}
