import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//简单的，不能并发处理的
public class Server {
    public static void main(String[] args) throws IOException{
        int portNumber = 12001;
        ServerSocket listenSocket = null;

        try {
            listenSocket = new ServerSocket(portNumber, 10,
                    InetAddress.getByName("127.0.0.1"));
            Socket toClientSocket = listenSocket.accept();

            System.out.println("client comes in, " +
                    toClientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    toClientSocket.getInputStream()
            ));
            String inputLine = null;
            while((inputLine = in.readLine()) != null){
                System.out.println("recv from client : " + inputLine);
                if(inputLine.equals("Bye"))
                {
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(listenSocket!=null)
                listenSocket.close();
        }

        }
}

