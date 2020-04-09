import java.io.*;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Hashtable;

public class MyFTPServer {
    //服务器端

    //需要一个全局格式化统一的内容
    public static DecimalFormat df = null;

    static{
        //设置数字格式
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }

    private String getFormatFileSize(long length){
        double size = ((double)length) / (1<<30);  //去零取整
        if(size >= 1){
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1<<20);
        if(size>=1){
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1<<10);
        if(size>=1){
            return df.format(size) + "KB";
        }
        return length + "B";

    }
    public class Waiter implements Runnable {
        Socket clientSocket;


        Waiter(Socket clientSocket){
            this.clientSocket = clientSocket;  //完成初始化
        }

        @Override
        public void run(){
            try{
                DataInputStream dis;
                FileOutputStream fos;  //从客户端读取文件写入

                String filepath=System.getProperty("user.dir");
                System.out.println("***有新的客户端正在接入***");
                PrintWriter writer = new PrintWriter((clientSocket.getOutputStream()),true);
                writer.println("欢迎接入本简易文件服务器，且服务器地址为: " + filepath + ". 请输入你的选择.\n 1:上传文件至终端; 2: 观察服务器信息 " +
                        "3:下载终端某文件.");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String ans1 = reader.readLine();
                System.out.println("[client]: 我要执行该编号对应的操作! "+ ans1);

                if(ans1.equals("1")){
                    //要执行上传文件的基本操作
                    //询问要上传文件的绝对路径
                    writer.println("请输入您要上传文件在您电脑中的绝对路径");

                    //等待回复,即消息一过来就要开始接收数据了
                    dis = new DataInputStream(clientSocket.getInputStream());  //这句话不会起到阻塞的作用

                    //获取文件头部信息
                    String fileName = dis.readUTF();  //小心IO报错，要不然是读取不出来的
                    long fileLength = dis.readLong();  //这两个操作和客户端都是相匹配的
                    System.out.println("***等待用户上传数据中***");
                    File directory = new File("user.dir");
                    if(!directory.exists()){
                        directory.mkdir();
                    }

                    File file = new File(directory.getAbsolutePath()+
                            File.separatorChar + fileName);  //处理好待写入文件的绝对路径

                    fos = new FileOutputStream(file);  //定义文件输出流

                    //开始接收文件余下的具体信息
                    byte[] bytes = new byte[1024];
                    int length = 0;
                    long progress = 0 ;//进度条控制
//                    int pos = 0;
//                    Hashtable notate = new Hashtable();
//                    notate.put(0,"|");
//                    notate.put(1,"\\");
//                    notate.put(2,"-");
//                    notate.put(3,"/");
                    while((length = dis.read(bytes, 0, bytes.length))!=-1){
//                        pos++;
                        //  | \ - / |
                        fos.write(bytes, 0, length);
                        fos.flush();
                        progress+=length;
                        System.out.print("| "+ (100*progress / fileLength) +" |\r");
                    }
                    System.out.println("***文件接收成功 [File Name: "+ fileName +" ]" +
                            " [Size: "+getFormatFileSize(fileLength)+"] ***");

                    //这个阻塞信息代表文件传输成功
//                    writer.println("Yes");
                    if(fos!=null)
                        fos.close();
                    if(dis!=null)
                        dis.close();
                }
                if(ans1.equals("2")){
                    File f = new File(filepath);
                    String fileList[] = f.list();  //调用不带参数的list()方法
                    for(int i=0;i<fileList.length;i++){
                        System.out.print(fileList[i]+"\t\t||");
                        System.out.print((new File(filepath,fileList[i]).isFile()? "文件夹"
                                +"\t\t||":"文件"+"\t\t||"));
                        System.out.println((new File(filepath,fileList[i]).length() + "字节"));
                    }
                }


                //定义一个输入缓冲区，接收客户端发过来的请求
            }catch (Exception e){
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

    public static void main(String[] argv) throws IOException {
        MyFTPServer server = new MyFTPServer();
        server.launch();
    }

    public void launch() throws IOException {
        int portNumber = 12001;
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("***服务器端已经启动***");

            while(true){
                //开始接收过来的client动作
                Socket clientSocket = serverSocket.accept();
                System.out.println("***已经与client建立新的连接***");
                (new Thread(new Waiter(clientSocket))).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }


    }
}
