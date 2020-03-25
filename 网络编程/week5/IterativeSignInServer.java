import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class IterativeSignInServer {
	public static void main(String[] args) throws IOException {
		int portNumber = 12001;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(portNumber);  // socket+listen

			while(true){ //7x24
				Socket clientSocket = serverSocket.accept();
				//打开sock输入输出流
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				//读取来自客户端的字符串
				String inputLine = "Sign in - " + reader.readLine() + " at " + (new Date());
				System.out.println(inputLine);
				Thread.sleep(10000);
				//回复客户端签到信息
				writer.println(inputLine);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			serverSocket.close();
		}
	}
}