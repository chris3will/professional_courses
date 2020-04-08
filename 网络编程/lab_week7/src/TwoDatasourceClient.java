import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class TwoDatasourceClient {
	class Datasource implements Runnable {
		//datasource的名称
		String name;
		//套接口的输出对象，向server发送数据
		Socket socket;
		Datasource(String name,Socket socket) {
			this.name = name;
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				System.out.println(writer.hashCode());
				for(int i=0;i<100;i++)
					writer.println(i+": "+name);  //不同的writer会导致没有锁住的情况发生
			} catch (Exception e) {
				e.printStackTrace();
			}			finally {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		TwoDatasourceClient twoDSClient = new TwoDatasourceClient();
		twoDSClient.start();
	}
	
	void start() throws IOException {
		String hostName = "localhost";
		int portNumber = 12001;
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(hostName, portNumber);  //直接去连接serverSocket
			//打开sock输入输出流
//			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			//创建第一个数据源
			(new Thread(new Datasource("Alice",clientSocket))).start();
			//创建第二个数据源
			(new Thread(new Datasource("Tom",clientSocket))).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


