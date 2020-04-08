/*
 * Java socket文件传输过程学习
 *  2020/4/8日
 */

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class FileTransferClient extends Socket{
    private static final String SERVER_IP = "127.0.0.1";
    private  static final int SERVER_PORT = 12001;

    private FileTransferClient client;

    private FileInputStream fis;

    private DataOutputStream dos;

    /*
     * 构造函数
     * 与服务器简历连接
     * @throws Exception
     */
    public FileTransferClient() throws Exception{
        super(SERVER_IP, SERVER_PORT);
        this.client = this;
        System.out.println("Client [port: "+client.getLocalPort() +" ] 成功连接服务器端");
    }

    /**
     * 向服务器端传输文件
     */
    public void sendFile() throws Exception{
        try{
            File file = new File("E:\\thridSpring\\网络编程\\网络编程.iml");
            if(file.exists()){
                //如果文件存在
                fis = new FileInputStream(file); //将文件装入文件输入流中
                dos = new DataOutputStream(client.getOutputStream()); //明确文件输出的接口

                //指明要传输的文件名和文件基本长度信息
                dos.writeUTF(file.getName());
                dos.flush();
                dos.writeLong(file.length());
                dos.flush();

                //开始传输文件
                System.out.println("***开始传输文件***");
                byte[] bytes = new byte[1024];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes,0, bytes.length))!=-1){
                    dos.write(bytes,0,length); //将从文件输入流中读取的byte长度的内容写入输出流
                    dos.flush();
                    progress+=length; //当前进度增加以供输出
                    System.out.println("| "+ (100*progress / file.length()) + "% |");
                }
                System.out.println();
                System.out.println("***文件传输成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fis!=null)
                fis.close();
            if(dos!=null)
                dos.close();
            client.close();
        }
    }

    /**
     * 入口函数
     */
    public static void main(String[] args){
        try{
            FileTransferClient client = new FileTransferClient();
            client.sendFile();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
