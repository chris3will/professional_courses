import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;

public class FileTransferServer extends ServerSocket {
    private  static final int SERVER_PORT = 12001;

    private static DecimalFormat df = null; //这个是什么还不知道,用来统一数据格式的东西

    static{
        //设置数字格式，保留一位有效小数
        df = new DecimalFormat("#0.0");
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    }

    public FileTransferServer() throws Exception{
        super(SERVER_PORT);
    }

    /**
     * 使用多线程的方式处理每个客户端传输文
     */

    public void load() throws Exception{
        while(true){
            //目前采用的是阻塞是方式，即一个人的操作会让另一个人的操作进行等待
            Socket socket = this.accept();  //this 即本身这个进程是个临界资源
            new Thread(new Task(socket)).start();  //主要的任务还是在线程中执行
        }
    }

    class Task implements Runnable{

        private  Socket socket;

        private DataInputStream dis;  //服务器端，数据输入流

        private FileOutputStream fos;

        public Task(Socket socket){
            this.socket = socket;
        }

        @Override
        public void run(){
            try{
                dis = new DataInputStream(socket.getInputStream());

                //获取文件名和长度
                String fileName = dis.readUTF();
                long fileLength = dis.readLong();
                File directory = new File("E:\\thirdSpring");
                if(!directory.exists()){
                    directory.mkdir();
                }
                File file = new File(directory.getAbsolutePath() +File.separatorChar+fileName);
                fos = new FileOutputStream(file);

                //开始接受文件
                byte[] bytes = new byte[1024];
                int length =0;
                while((length = dis.read(bytes,0,bytes.length))!=-1){
                    fos.write(bytes,0,length);
                    fos.flush();
                }
                System.out.println("***文件接收成功 [File Name: "+fileName + "] [Size: "+getFormatFileSize(fileLength)+"] ***");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try{
                    if(fos!=null)
                        fos.close();
                    if(dis!=null)
                        dis.close();
                    socket.close();
                }catch (Exception e){}
            }
        }
    }

    /**
     * 格式化文件大小
     */
    private String getFormatFileSize(long length){
        double size = ((double) length) / (1<<30);
        if(size >=1){
            return df.format(size) + "GB";
        }
        size = ((double) length) / (1<<20);
        if(size >=1){
            return df.format(size) + "MB";
        }
        size = ((double) length) / (1<<10);
        if(size>=1){
            return df.format(size) + "KB";
        }
        return length + "B";
    }

    /**
     * 入口函数
     */

    public  static  void main(String[] args){
        try{
            FileTransferServer server = new FileTransferServer();
            server.load();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
