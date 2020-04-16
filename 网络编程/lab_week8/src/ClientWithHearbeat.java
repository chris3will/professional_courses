import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWithHearbeat {
	static class HeartbeatWorker implements Runnable {
		Socket clientSocket;
		HeartbeatWorker(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run() {
			try {
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
				while(true){
					Thread.sleep(10000); //sleep 10秒
					writer.println("I am OK.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	}
	
	public static void main(String[] args) throws IOException {
		String hostName = "localhost";
		int portNumber = 12001;
		Socket clientSocket = null;
		try {
			clientSocket = new Socket(hostName, portNumber);
			//打开sock输出流和console标准输入流
			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(
					System.in));
			//启动心跳线程(改进：workerThread -> Timer  ->ScheduledExecutorService)
			//(new Thread(new HeartbeatWorker(clientSocket))).start();
			Thread t0 = new Thread(new HeartbeatWorker(clientSocket));
			t0.setDaemon(true);
			t0.start();

			for(;;){
				String msg = stdIn.readLine();
				if (msg.toLowerCase().equals("exit")) {
					break;
				}
				writer.println(msg);
			}
			writer.close();  //这一句加上之后，可以解决server端的reset报错问题，但是还未解决双重close的问题
			System.out.println("client exit");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
}