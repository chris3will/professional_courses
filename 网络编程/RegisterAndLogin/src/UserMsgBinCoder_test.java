import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class UserMsgBinCoder_test implements UserMsgCoder_test {

    //为了处理传输的消息，需要明确不同消息的具体包长度
    //1. 消息头部格式
    public static final int TOTAL_LENGTH=4; //消息总长度4个字节
    public static final int COMMANDID_LENGTH = 4;  //1,2,3,4四种编号，分别对应着不同的消息类型

    //2. 请求消息体格式
    public static final int USERNAME_LENGTH = 20;
    public static final int PASSWORD_LENGTH = 30;

    //3. 响应消息体格式
    public static final int STATUS_LENGTH = 1; //反应注册结果，0代表失败，1代表成功
    public static final int DESCRIPTION_LENGTH = 64;

    public static byte[] toLH(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public byte[] toWire(UserMsg_test msg) throws IOException{
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();  //先创建一个二进制流
        DataOutputStream out = new DataOutputStream(byteStream);

        //先不做很多magic的验证，直接写入一个头部信息
        //先写入4字节的消息包长度信息
        boolean isRegister = msg.isRegister();
        boolean isLogin = msg.isLogin();


        //如果是request，则其字节数为4+4 + 50
        //如果是respose, 其字节数为4+4 + 65

        if(isRegister){
            //如果这个是
        }

        byte[] datalen = new byte[4];
        out.write(datalen);

        return null;
    }

    @Override
    public UserMsg_test fromWire(byte[] input) throws IOException {
        return null;
    }


}
