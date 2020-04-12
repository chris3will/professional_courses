import sun.awt.windows.ThemeReader;

import java.io.*;
import java.net.Socket;

public class MyFTPClient {

    //private static boolean tryAgain = true;  //是否循环操作请求
    public static int portNumber = 12001;
    public static String serverIp = "localhost";

    class Worker implements Runnable{
        String name;
        boolean arrived;
        Socket socket;

        private FileInputStream fis;
        private DataOutputStream dos;

        Worker(String name, Socket socket,boolean arrived){
            this.name = name;
            this.socket = socket;
            this.arrived = arrived;
        }

        //虽然是单线程，但是还是用并发方式实现
        @Override
        public void run() {
            try{
                //实际上开始阶段应该先接收服务器端的一个提示
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                if(!arrived) {  //之前没有来过
                    System.out.println("***客户端正在与服务器连接***: " + socket.getPort());
                    System.out.println("***当前PrintWriter的哈希值为: " + writer.hashCode() + " 已经与服务器连接成功***");
                }
                //下面是两条提示语
                //欢迎接入本简易文件服务器，且服务器地址为: " + filepath + ". 请输入你的选择.\n 1:上传文件至终端; 2: 观察服务器信息 " +
                //                        "3:下载终端某文件.
                System.out.println("[svr " + socket.getPort() + "]: "+reader.readLine());
                System.out.println("[svr " + socket.getPort() + "]: "+reader.readLine());


                //定义一个标准输入，以对应服务器端的询问
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                        System.in));  //键盘的标准输入

                String msg = null;  //对应标准输入的暂时缓冲内容
                // while(!(msg = stdIn.readLine()).equals("0")){
                if(!(msg = stdIn.readLine()).equals("0")){
                    //向svr发送回应数据；这个while是具体的请求大循环
                    //目前下属指令均发生在情形1下，即仅有用户向客户端发出请求上传文件

                      //做出你的选择

                    if(msg.equals("1")) {
                        writer.println(msg);
                        //需要收到服务器端的具体相应，以做出接下来的判断
                        msg = reader.readLine();  //"请输入您要上传文件在您电脑中的绝对路径"
                        System.out.println("[svr " + socket.getPort() + "]: "+ msg);

                        //客户输入要上传的绝对路径
                        msg = stdIn.readLine();
                        File file = new File(msg);
                        if (file.exists()) {
                            writer.println("Exists");
                            //仅当文件存在时执行
                            //我这个时候再写，是直接写到它的datainputstream里了
                            fis = new FileInputStream(file);
                            dos = new DataOutputStream(socket.getOutputStream());

                            //先指明要传输的文件名和文件长度等的基本信息
                            dos.writeUTF(file.getName());
                            dos.flush();  //强制刷新数据流
                            dos.writeLong(file.length());
                            dos.flush();

                            //开始传输文件
                            System.out.println("***本地上上传启动***");
                            byte[] bytes = new byte[1024];
                            int length = 0;
                            long progress = 0;  //当前进度

                            long startTime = System.currentTimeMillis();

                            while ((length = fis.read(bytes, 0, bytes.length)) != -1) {
                                dos.write(bytes, 0, length);  //从文件输入流中读取的bytes写入data输出流
                                dos.flush();
                                progress += length;
                                System.out.print("| " + (100 * progress / file.length()) + " |\r");
                            }

                            long endTime =System.currentTimeMillis();

                            Thread.sleep(1000);
                            System.out.println("***文件发送成功 [File Name: "+ file.getName() +" ]" +
                                    " [time: "+(endTime-startTime)/1000 +"s ] ***");
                            if(!socket.isClosed()) {
                                writer.println(" 客户端正在关闭.");
                                socket.close();  //这一步很关键，避免了一方的提前为断连式程序退出。
                            }
                            System.out.println("***本地文件传输成功***");

                        } else {
                            writer.println("Nop");
                            if(!socket.isClosed()) {
                                writer.println(" 客户端正在关闭.");
                                socket.close();  //这一步很关键，避免了一方的提前为断连式程序退出。
                            }
                            System.out.println("***您好，您输入的路径非法或出现文件异常，请在本地核查后重新操作.***");
                        }
                    }
                    else if(msg.equals("2")){
                        writer.println(msg);
                        //打印服务器目录文件与文件夹列表
                        //还是通过reader来读取参数
                        //可以先接收一个长度参数，再利用这个参数进行while循环读取每一条列表项，进而进行打印操作

                        System.out.println("名\t\t||文件或文件夹\t\t||大小");
                        msg = reader.readLine();  //先读取长度参数
                        int len = Integer.parseInt(msg);
                        while(len-- > 0){
                            msg = reader.readLine();
                            System.out.println(msg);
                        }

                    }
                    else{
                        writer.println(msg);
                        System.out.println("***抱歉，您的操作非法或因正在维护，请重新选择***");
                    }
                }




                //该次操作结束之后，询问用户是否继续使用改程序
                System.out.println("***您是否希望继续使用本程序进行相关操作？(1/0)***");
                msg = stdIn.readLine();

                if(!socket.isClosed()) {
                    socket.close();  //这一步很关键，避免了一方的提前为断连式程序退出。
                }

                if(!msg.equals("1")){
                    System.out.println("***客户端线程正常关闭中***");
                    System.out.println("***欢迎您再次使用本程序***");
                }else{
                    Socket Client = new Socket(serverIp,portNumber);
                    (new Thread(new Worker((String.valueOf(Client.getPort())),Client,true))).start();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] argv) throws IOException {
        //这是一个入口函数
        MyFTPClient client = new MyFTPClient();
        client.launch();

    }

    public void launch() throws IOException {
        //必要的启动逻辑

        Socket clientSocket = null;
        try{
            clientSocket = new Socket(serverIp, portNumber);
            (new Thread(new Worker((String.valueOf(clientSocket.getLocalPort())), clientSocket,false))).start();  //默认为每个用户进程开一个线程
            //(new Thread((new Worker("file2",clientSocket)))).start();
        }catch(Exception e){
            e.printStackTrace();
        }
        //注意，我在这里不主动添加finally来让该client socket关闭

    }

}
