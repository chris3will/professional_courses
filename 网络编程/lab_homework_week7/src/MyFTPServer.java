import java.io.*;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Hashtable;

public class MyFTPServer {
    //服务器端

    //需要一个全局格式化统一的内容
    public static DecimalFormat df = null;
    public static int portNumber = 12001;

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
                //System.out.println("***有新的客户端正在接入***");
                PrintWriter writer = new PrintWriter((clientSocket.getOutputStream()),true);
                writer.println("成功接入本简易文件服务器，当前时间是 " + (new Date())+
                        ". 服务器地址为: " + filepath + ". 请输入你的选择. \n 1:上传文件至终端; 2: 观察服务器信息; " +
                        "3:下载终端某文件(维护中); 0: 退出本程序");

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String ans1 = reader.readLine();
                
                System.out.println("[client "+clientSocket.getPort()+"]: 我要执行该编号 "+ans1+ " 对应的操作! ");

                if(ans1.equals("1")){
                    //要执行上传文件的基本操作
                    //询问要上传文件的绝对路径
                    writer.println("请输入您要上传文件在您电脑中的绝对路径" +
                            "(默认忽略大小写差异，我们将遵循您本地实际的文件存储格式)");

                    //如果客户端操作正确的话，就等待接收，所以我在这里再加一重检测标记
                    String ans = reader.readLine();
                    System.out.println("打印服务器端ans: "+ans);
                    if(ans.equals("Exists")) {

                        /* 等待回复,即消息一过来就要开始接收数据了 */
                        dis = new DataInputStream(clientSocket.getInputStream());  //这句话不会起到阻塞的作用

                        //获取文件头部信息
                        String fileName = dis.readUTF();  //小心IO报错，要不然是读取不出来的
                        System.out.println("***准备接收 " + fileName + " ***");
                        long fileLength = dis.readLong();  //这两个操作和客户端都是相匹配的
                        System.out.println("***等待客户端上传 "+fileName+" 的完整数据中***");
                        File directory = new File("user.dir");
                        if (!directory.exists()) {
                            directory.mkdir();
                        }

                        //新建一个文件对象，用来存储上传的文件
                        File file = new File(directory.getAbsolutePath() +
                                File.separatorChar + fileName);  //处理好待写入文件的绝对路径

                        fos = new FileOutputStream(file);  //定义文件输出流

                        //开始接收文件余下的具体信息
                        byte[] bytes = new byte[1024];
                        int length = 0;
                        long progress = 0;//进度条控制

                        long startTime = System.currentTimeMillis();
                        while ((length = dis.read(bytes, 0, bytes.length)) != -1) {
                            fos.write(bytes, 0, length);
                            fos.flush();
                            //考虑到服务器的业务需求，就不显示百分比了
//                        progress+=length;
//                        System.out.print("| "+ (100*progress / fileLength) +" |\r");
                        }
                        long endTime = System.currentTimeMillis();
                        System.out.println("***文件接收成功 [File Name: " + fileName + " ]" +
                                " [Size: " + getFormatFileSize(fileLength) + "] [Time:" + (endTime - startTime) / 1000 + "s ]***");

                        if (fos != null)
                            fos.close();
                        if (dis != null)
                            dis.close();
                    }else{
                        //在服务器端无操作或者直接提示出错
                        System.out.println("***客户端操作终止，服务器取消当次响应***");
                    }

                }
                if(ans1.equals("2")){
                    File f = new File(filepath);
                    String fileList[] = f.list();  //调用不带参数的list()方法
                    writer.println(fileList.length);  //把这个列表长度参数传过去
                    for(int i=0;i<fileList.length;i++){
                        //System.out.print(fileList[i]+"\t\t||");
                        writer.print(fileList[i]+"\t\t||");
                        //System.out.print((new File(filepath,fileList[i]).isFile()? "文件夹"
                        //        +"\t\t||":"文件"+"\t\t||"));
                        writer.print((new File(filepath,fileList[i]).isFile()? "文件夹"
                                +"\t\t||":"文件"+"\t\t||"));
                        //System.out.println((new File(filepath,fileList[i]).length() + "字节"));
                        writer.println((new File(filepath,fileList[i]).length() + "字节"));
                    }
                }
                if(ans1.equals("3")){

                }
                if(ans1.equals("0")){
                    System.out.println("***客户端终止操作***");
                }


                //定义一个输入缓冲区，接收客户端发过来的请求
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    System.out.println("***已与该client "+clientSocket.getPort()+"线程断开连接***");
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

        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(portNumber);
            System.out.println("***服务器端已经启动***");

            while(true){
                //开始接收过来的client动作
                Socket clientSocket = serverSocket.accept();
                System.out.println("***已经与client "+ clientSocket.getPort()+"建立新的连接" +".接入时间为:"+(new Date())+
                        " ***");
                (new Thread(new Waiter(clientSocket))).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }


    }
}
