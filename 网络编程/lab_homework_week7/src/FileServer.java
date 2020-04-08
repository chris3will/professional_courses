import com.sun.security.ntlm.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


//这是一个文件服务器，
public class FileServer {

    class Worker implements Runnable{
        //为连入客户端而打开一个套接口
        Socket socket;

        Worker(Socket socket){this.socket = socket;};

        @Override
        public void run(){
            try{
                //打开sock输入流,获取client输入的内容
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                while(true){
                    //读取来自客户端的字符输入
                    String msg = reader.readLine();
                    if(msg == null){
                        break;
                    }
                    System.out.println(msg + " ["+(new Date())+"]");
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                try{
                    System.out.println("关闭server端的socket");
                    socket.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        FileServer server = new FileServer();
        server.launch();
    }

    void launch() throws IOException{
        int portNumber = 12001;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);  //注意，一个进程只对应唯一确定的port编号
            System.out.println("文件传输服务器已启动");
            while(true){
                Socket clientSocket = serverSocket.accept();
                (new Thread(new Worker(clientSocket))).start();
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally {
            serverSocket.close();
        }

    }


}
