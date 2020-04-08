import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class BlackHoleServer {

	class Worker implements Runnable {
		// 为连入的客户端打开的套接口
		Socket socket;

		Worker(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// 打开sock输入流
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				while(true){
					// 读取来自客户端的字符串并输出
					String msg = reader.readLine();
					if (msg==null){
						System.out.println("msg为空");
						Thread.sleep(1000);
						break;
					}
					System.out.println(msg+" ["+(new Date())+"]");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					System.out.println("关闭服务器连接的一个socket");
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BlackHoleServer server = new BlackHoleServer();
		server.launch();
	}

	void launch() throws IOException {
		int portNumber = 12001;
		ServerSocket serverSocket = null;
		try {
			System.out.println("客户端已经启动");
			serverSocket = new ServerSocket(portNumber);

			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("和客户建立新连接");
				(new Thread(new Worker(clientSocket))).start();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			serverSocket.close();
		}
	}
}