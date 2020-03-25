import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class tryAns{
    //服务器IP
    public static final String SERVER_IP = "127.0.0.1";

    //服务器端口号
    public static final int SERVER_PORT = 12001;

    //请求终结字符串
    public static final char REQUEST_END_CHAR = '#';

    /***
     * 启动服务器
     * @param 服务器监听的端口号，服务器ip无需指定，系统自动分配
     */
    public void startServer(String serverIP, int serverPort){
        //创建服务器地址对象
        InetAddress serverAddr;
        try{
            serverAddr = InetAddress.getByName(serverIP);
        }catch(UnknownHostException e1){
            e1.printStackTrace();
            return;
        }

        //java提供了ServerSocket作为服务器
        try(ServerSocket serverSocket = new ServerSocket(SERVER_PORT,5,serverAddr)){
            Executor executor = Executors.newFixedThreadPool(100);
            while(true){
                StringBuilder recvStrBuilder = new StringBuilder();

                try {
                    //有客户端发起tcp连接时，accept会返回一个Socket
                    //该Socket的对端就是客户端的Socket
                    //具体过程参照TCP三次握手
                    Socket connection = serverSocket.accept();  //先接受第一个抵达q队列的socket

                    //利用线程池，启动线程； 这个是没有学过的知识

                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            //使用局部引用，防止connection被回收
                            Socket conn = connection;  //相当于不用原始数据，不对其产生不必要的影响
                            try{
                                InputStream in = conn.getInputStream();

                                //读取客户端的请求字符串，
                                for(int c = in.read(); c!= REQUEST_END_CHAR; c=in.read()){
                                    recvStrBuilder.append((char)c);
                                }
                                recvStrBuilder.append('#');

                                String recvStr = recvStrBuilder.toString();

                                //向客户端写出处理后的字符串
                                OutputStream out = conn.getOutputStream();
                                out.write(recvStr.toUpperCase().getBytes());

                            }catch(IOException e){
                                e.printStackTrace();
                            }finally {
                                try{
                                    if (conn!=null)
                                    {
                                        conn.close();
                                    }
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        tryAns server = new tryAns();
        server.startServer(SERVER_IP,SERVER_PORT);
    }
}