import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class DualClient {

    class DataSource implements Runnable{
        String name;
        Socket socket;
        PrintWriter writer;
        DataSource(String name,PrintWriter writer )
        {
            this.name = name;
            this.writer = writer;
        }

        @Override
        public void run(){
            try{
                System.out.println(writer.hashCode());
                for(int i=0;i<100;i++)
                    writer.println(i+": "+name);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    socket.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] argv) throws IOException{
        DualClient dualClient = new DualClient();
        dualClient.start();
    }

    void start() throws IOException{
        String hostName = "localhost";
        int portNumber = 12001;
        Socket clientSocket = null;

        try{
            clientSocket = new Socket(hostName, portNumber);
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);

            (new Thread(new DataSource("Jake",writer))).start();
            (new Thread(new DataSource("Lora",writer))).start();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
