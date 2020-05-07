import com.sun.corba.se.spi.orbutil.threadpool.Work;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static int portNumber = 12001;
    public static String serverIp = "localhost";

    public static boolean hasSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static void main(String[] argv) throws IOException{
        Client client = new Client();
        client.launch();
    }

    class Worker implements Runnable{
//        int status; //注意到客户端发给服务器端的都是request命令
//        String name;
//        String pwd;
//        String hashCode;
        Socket socket;
        Worker(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try{
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(),true);

                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                String fromSvr = reader.readLine();
                System.out.println(fromSvr); //1

                String toSvr = stdIn.readLine();
                writer.println(toSvr); // 2 打印具体的请求，即表明是要登陆，还是要注册

                int commetID = 0;
                if(Integer.parseInt(toSvr)==2){
                    commetID = 1;  //我这是要注册
                }else if(Integer.parseInt(toSvr) == 1){
                    commetID = 3;  //我这是要登陆
                }


                fromSvr = reader.readLine(); //3 这一步返回自己的请求，即具体是注册还是登陆
                System.out.println(fromSvr);
                if(commetID!=0) {

                    //开始一连串的本地提示工作
                    //先输入账号
                    System.out.println("先输入您的账号，注意长度不能超过十个字符，且仅由数字和字母构成");
                    String tmpUsername = stdIn.readLine();

                    //再输入密码
                    System.out.println("请再输入您的密码，注意长度不能超过15个字符，且仅由数字和字母构成");

                    String tmpPassword = stdIn.readLine();
                    while (hasSpecialChar(tmpPassword)) {
                        System.out.println("请按照要求输入密码，即只含有数字和字母!");
                        tmpPassword = stdIn.readLine();
                    }
                    //对已经输入的包进行一定的封装处理

                    //需要知道的有，总长度
                    //消息类型识别号
                    //将userName存满20字节
                    //将password存满30字节
                    //所以说，这个消息包的总长度是固定的即4+4+20+30字节。
                    //但无论登陆还是注册，均共用一套流程

                    byte[] msg = MsgTool.FormCtoSMsg(commetID, tmpUsername, tmpPassword);
                    MsgTool.sendMsg(msg, socket);

                    //等待接收服务器的响应
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    byte[] header = new byte[8];  //先开一个头部缓冲区
                    if (dis.read(header, 0, 8) != -1) {
                        //已经知道这个response包体的响应结构了，直接读内容
                        byte[] ByteStatus = new byte[1];
                        byte[] ByteDescription = new byte[64];
                        dis.read(ByteStatus, 0, 1);
                        dis.read(ByteDescription, 0, 64);
                        String status = MsgTool.byteArrayToStr(ByteStatus);
                        String description = MsgTool.byteArrayToStr(ByteDescription);
                        System.out.println("Status: " + status);
                        System.out.println("Tips: " + description);
                    }


                    //System.out.println(msg);
                    //我现在这一侧直接检测解包问题
                    //先读一个字节的头部信息
                /*
                ByteArrayInputStream bis = new ByteArrayInputStream(msg);
                byte[] commentedIDBytes = new byte[4]; //先读取总长度
                if(bis.read(commentedIDBytes,0,4)!=-1){
                    //能读出长度，且已经读出长度
                    System.out.println(MsgTool.byteArrayToInt(commentedIDBytes));
                    bis.read(commentedIDBytes,0,4);  //流中的数据在bis是移动的，现在在最前4个的字节即代表哦了标识符号
                    commetID = MsgTool.byteArrayToInt(commentedIDBytes);

                    //读取包体
                    byte[] userNameArray = new byte[20];
                    byte[] passwordArray = new byte[30];
                    bis.read(userNameArray,0,20);
                    bis.read(passwordArray,0,30);

                    String userName = MsgTool.byteArrayToStr(userNameArray);
                    String password = MsgTool.byteArrayToStr(passwordArray);
                    System.out.println(userName+" "+password);

                    File file = new File("E:\\thridSpring\\网络编程\\RegisterAndLogin\\user.csv");
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    HashMap<String, String> users = MsgTool.databaseToMap(file);
                    for(String key:users.keySet()){
                        System.out.println(key+" "+users.get(key));
                    }

                    //检测有没有重名
                    if(users.containsKey(userName)){
                        System.out.println("注册重名");

                    }else{
                        System.out.println("没有重名，服务器正在操作");
                        BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
                        out.write(userName+","+password.hashCode()+"\n");
                        out.flush();
                        out.close();
                    }
                }
*/
                /*
//                fromSvr = reader.readLine();//4
//                System.out.println(fromSvr);
//
//                toSvr = stdIn.readLine(); //在这里不做约束性检测
//                writer.println(toSvr);//5
//
//                fromSvr = reader.readLine(); //6
//                System.out.println(fromSvr);
//
//                toSvr = stdIn.readLine();
//                writer.println(toSvr);  //7
                */
                /*
                boolean flag = true;
                while(flag){
                    System.out.print("输入信息: ");
                    String str = stdIn.readLine();

                    writer.println(str);
                    if("bye".equals(str)){
                        flag = false;
                    }else{
                        try{
                            String echo = reader.readLine();
                            System.out.println(echo);
                        }catch(SocketTimeoutException e){
                            System.out.println("Time out, No response");
                        }
                    }
                }
                */
                }else{
                    //操作失败的选择
                }
                System.out.println("客户端正在关闭!");
                stdIn.close();

                if(socket!=null){
                    socket.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public void launch() throws IOException{

        Socket clientSocket = null;
        try{
            clientSocket = new Socket(serverIp,portNumber);
            (new Thread(new Worker(clientSocket))).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
