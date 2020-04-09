import sun.awt.windows.ThemeReader;

import java.io.*;
import java.net.Socket;

public class MyFTPClient {

    class Worker implements Runnable{
        String name;
        Socket socket;

        private FileInputStream fis;
        private DataOutputStream dos;

        Worker(String name, Socket socket){
            this.name = name;
            this.socket = socket;
        }

        //虽然是单线程，但是还是用并发方式实现
        @Override
        public void run() {
            try{
                //实际上开始阶段应该先接收服务器端的一个提示
                System.out.println("***正在与服务器连接***");
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("当前写入头部的哈希值为: "+ writer.hashCode() + " 已经与服务器连接成功");

                //下面是两条提示语
                System.out.println("[svr]: "+reader.readLine());
                System.out.println("[svr]: "+reader.readLine());


                //定义一个标准输入，以对应服务器端的询问
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(
                        System.in));  //键盘的标准输入

                String msg = null;  //对应标准输入的暂时缓冲内容
                while((msg = stdIn.readLine()) != null){
                    //向svr发送回应数据；这个while是具体的请求大循环
                    //目前下属指令均发生在情形1下，即仅有用户向客户端发出请求上传文件

                    writer.println(msg);

                    //需要收到服务器端的具体相应，以做出接下来的判断
                    msg = reader.readLine();
                    System.out.println("[svr]: "+msg);

                    //客户输入要上传的绝对路径
                    msg = stdIn.readLine();
                    File file =new File(msg);
                    if(file.exists()){
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
                        while((length = fis.read(bytes, 0, bytes.length))!=-1){
                            dos.write(bytes,0,length);  //从文件输入流中读取的bytes写入data输出流
                            dos.flush();
                            progress+=length;
                            System.out.print("| "+(100*progress / file.length())+" |\r");
                        }
                        Thread.sleep(1000);

                        socket.close();  //这一步很关键，避免了一方的提前为断连式程序退出。

                        System.out.println();
                        //因为服务器socket或者是svr的socket提前断连了，我在这里做一个阻塞测试
                        //下面必须接收服务器的正向反馈才能结束工作
//                        msg = reader.readLine();
//                        if(msg.equals("Yes"))
//                        {
//                            System.out.println("***本地文件传输成功***");
//                        }else{
//                            System.out.println("***服务器端未接收到数据，传输失败***");
//                        }


                        System.out.println("***本地文件传输成功***");


                        //指明要传输的文件名和文件长度信息

                    }else{
                        //先保证不会发生意外情况
                    }


                    break;
                }


                System.out.println("***程序正常关闭中***");

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
        int portNumber = 12001;
        String serverIp = "localhost";
        Socket clientSocket = null;
        try{
            clientSocket = new Socket(serverIp,portNumber);

            (new Thread(new Worker("file1",clientSocket))).start();

            //(new Thread((new Worker("file2",clientSocket)))).start();
        }catch(Exception e){
            e.printStackTrace();
        }
        //注意，我在这里不主动添加finally来让该clinet socket关闭

    }

}
