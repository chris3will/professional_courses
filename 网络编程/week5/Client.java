import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	public static void main(String[] args) throws IOException {
		String hostName = "127.0.0.1";
		int portNumber = 12001;
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(hostName, portNumber);
			//打开sock输入输出流
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(clientSocket.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			//向svr签到
			writer.println(stdIn.readLine());
			//读取svr返回的签到结果
			System.out.println("server answers: "+reader.readLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



//Socket[] theclientSocket = new Socket[100];
//for(int i=0;;i++){
//	theclientSocket[i]= new Socket(hostName, portNumber);
//	System.out.println(i+" "+theclientSocket[i]);
//}
