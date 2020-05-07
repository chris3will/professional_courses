import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;


public class Server {

    public static int portNumber = 12001;
    public static String filePath= "user.csv";
    public static void main(String[] argv) throws IOException{
        Server server = new Server();
//        CsvRead csvreader = new CsvRead(filePath);
//        csvreader.read();  //可以打印当前记录的用户名即对应密码
        server.launch();
    }

    public class Waiter implements Runnable{
        //解包的程序还是要在这里面来执行
        //处理的程序分两种，即分别为注册信息和登录信息
        //我先建立一个通用的msg类，来分别应对登录和注册的两种情况
        Socket clientSocket;
        Waiter(Socket clientSocket){this.clientSocket = clientSocket;}

        @Override
        public void run(){
            try{
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(),true);
                BufferedReader reader = new BufferedReader((new InputStreamReader(clientSocket.getInputStream())));
                writer.println("欢迎来到登录注册训练程序。您是想登陆(1)，还是想注册(2)");   //1
                String fromClt = reader.readLine();  //2 收到回复
                System.out.println(fromClt);

                int choice = Integer.parseInt(fromClt);
                File file = new File(filePath);
                if(!file.exists()){
                    file.createNewFile();
                }
                HashMap<String,String> userList = MsgTool.GetUserMap(file);  //无论是登陆注册，这个map都要建立，索性将它提前处理好

                if(choice==1){
                    //用户选择了登陆行为

                    writer.println("请根据具体的规则开始您的数据包创建"); //3
                    //先读取消息头部
                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    byte[] ByteCommetId = new byte[4];
                    int length = 0; //记录当前读取信息的字节长度
                    if((length = dis.read(ByteCommetId,0,4))!=-1){
                        //读取指令编号
                        length = dis.read(ByteCommetId,0,4);
                        System.out.println("当前客户端指令编号: " + MsgTool.byteArrayToStr(ByteCommetId));

                        //接下来要读消息体
                        byte[] ByteUsername = new byte[20];
                        byte[] BytePassword = new byte[30];
                        length = dis.read(ByteUsername,0,20);
                        length = dis.read(BytePassword,0,30);
                        String username = MsgTool.byteArrayToStr(ByteUsername);
                        String password = MsgTool.byteArrayToStr(BytePassword);

                        if(!userList.containsKey(username)){
                            //这里要报错，登陆错误
                            byte[] msg = MsgTool.FormStoCMsg(4,"0","登陆失败，账号不存在!");
                            MsgTool.sendMsg(msg,clientSocket);
                            System.out.println("登陆失败，账号不存在");
                        }else{
                            String theHash = userList.get(username);
                            //System.out.println("数据库中的哈希值: "+Integer.parseInt(theHash)+"; 新来的哈希值: "+password.hashCode());
                            if(Integer.parseInt(theHash)==password.hashCode()){
                                //登陆成功
                                byte[] msg = MsgTool.FormStoCMsg(4,"1","恭喜，登陆成功!");
                                MsgTool.sendMsg(msg,clientSocket);
                                System.out.println(username+" 登陆成功!");
                            }else
                            {
                                //因为密码错误而登录失败
                                byte[] msg = MsgTool.FormStoCMsg(4,"0","登陆失败，密码不正确!");
                                MsgTool.sendMsg(msg,clientSocket);
                                System.out.println("登陆失败，密码错误!");
                            }
                        }

                    }

                }else if(choice == 2){
                    /*
                    writer.println("欢迎来到注册界面。请输入您的账号和密码"); //3
                    writer.println("请输入您的账号，注意不超过10个字符");  //4
                    fromClt = reader.readLine();//5
                    System.out.println(fromClt+" 这是从客户端接收到的账号信息");

                    //在这里做一些约束性检测

                    writer.println("请输入您的只由数字和字母组成的密码，且不超过15个字符");//6
                    fromClt = reader.readLine();//7
                    System.out.println(fromClt+" 这是从客户端接收到的密码信息");
                    */

                    //提示用户可以自己创建自己的消息包了
                    //此 选择下用户的操作是注册
                    writer.println("请根据具体的规则开始您的数据包创建"); //3
                    //下面即等待客户端直接将整个消息包串过来，固定自己的那种
                    //下面是注册，即4 + 4 + 20 + 30

                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());

                    //定义缓冲区，开始接收
                    byte[] ByteCommetID = new byte[4];
                    if((dis.read(ByteCommetID,0,4))!=-1){
                        //上一步已经把头部读取出来了
                        //开始读取具体的信息，具体的形势信息
                        dis.read(ByteCommetID,0,4); //
                        String commetId = MsgTool.byteArrayToStr(ByteCommetID);
                        System.out.println(commetId);
                        //开始处理用户的输入信息

                        byte[] ByteUsername = new byte[20];
                        byte[] BytePassword = new byte[30];  //注意我存的都是哈希值
                        dis.read(ByteUsername,0,20);
                        dis.read(BytePassword,0,30);
                        String username = MsgTool.byteArrayToStr(ByteUsername);
                        String password = MsgTool.byteArrayToStr(BytePassword);

                        //建立一个哈希map，与文件对应起来。将其写入文件
                        //但是这个哈希表也是根据本地的一个文件创建的
                        //但是目前好像还没有考虑到阻塞的问题，不知道File这个流对象自身是否考虑到了这个问题

                        if(userList.containsKey(username)){
                            System.out.println("该用户名重复，注册失败");
                            byte[] msg = MsgTool.FormStoCMsg(2,"0","因用户名已注册，该申请失效");
                            MsgTool.sendMsg(msg,clientSocket);
                        }else{
                            //注册未失效，将其直接追加到文件的末尾
                            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
                            bw.write(username+","+password.hashCode()+"\n");  //我在这里将密码的哈希值存到数据库中，这样可以一定程度上提高安全性
                            bw.flush();
                            bw.close();
                            byte[] msg = MsgTool.FormStoCMsg(2,"1","success");
                            MsgTool.sendMsg(msg,clientSocket);
                            System.out.println("该用户 "+username+"注册成功");
                        }

                    }
                }else{
                    writer.println("您输入的请求异常，请重新输入正确选项后再使用本程序!");  //3
                    System.out.println("客户端输入异常，已退出!");
                }
                System.out.println("当前有客户端退出连接!");

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void launch() throws IOException{
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("服务器已启动");

            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("新的连接已经建立");
                (new Thread(new Waiter(clientSocket))).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }
    }

}
