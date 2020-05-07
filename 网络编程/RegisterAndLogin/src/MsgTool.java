import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class MsgTool {

    public static byte[] strToByteArray(String str, int AllLength) {
        ByteBuffer result = ByteBuffer.wrap(new byte[AllLength]);  //先创建一个新的buffer数组，通过包裹byte而创建，其长度由arrayLength指明
        result.put(str.getBytes());  //先把已经有的内容覆盖在新byte数组的前部
        for (int i = str.length(); i < AllLength && result.remaining() > 0; i++) {  //从长度代表的这个下标开始，其余的补/0
            result.put((byte) '\0');
        }
        return result.array();
    }

    public static byte[] MergeHead(int AlltLength, int commandID) throws IOException {
        byte[] head = intToByteArray(AlltLength);
        byte[] hind = intToByteArray(commandID);
        return MergeByte(head, hind);
    }

    public static byte[] intToByteArray(final int data) {
        //模板函数
        return new byte[]{  //网络数据统统按大端序处理
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    public static int byteArrayToInt(byte[] data) {
        if (data == null || data.length != 4) return 0x0;  //如果是空，或直接不是四个字节的完整长度，直接返回0
        return (0xff & data[0]) << 24 |
                (0xff & data[1]) << 16 |
                (0xff & data[2]) << 8 |
                (0xff & data[3]);
    }

    public static String byteArrayToStr(byte[] arr) {
        //把定长的数据填满
        int len;
        for (len = 0; len < arr.length && arr[len] != '\0'; len++) {
        }
        String str = new String(arr, 0, len);
        return str;
    }

    public static byte[] MergeByte(byte[]... byteArrays) throws IOException {
        int AllLength = 0;
        for (byte[] byteArray : byteArrays) {
            AllLength += byteArray.length;
        }
        ByteBuffer result = ByteBuffer.wrap(new byte[AllLength]);
        for (byte[] byteArray : byteArrays) {
//            System.out.println(byteArray.length);
            result.put(byteArray);  //就把这些比特数组全部接在一起
        }
        return result.array();
    }

    public static byte[] FormCtoSMsg(int commandID, String userName, String password) throws Exception {
        byte[] header = MergeHead(4 + 4 + 20 + 30, commandID);  //先组成一个8字节的头部，包含总的固定长度以及指令编号
        if (userName.length() > 10) {
            throw new Exception("您的用户名需小于等于10个字符！");
        } else if(password.length()>15){
            throw new Exception("您的用户密码需要小于15个字符");
        } else{
            byte[] ByteUserName = strToByteArray(userName, 20);
            byte[] BytePassword = strToByteArray(password, 30);
            byte[] message = MergeByte(header, ByteUserName, BytePassword);  //把这个三部分组合在一起
            return message;
        }
    }

    public static byte[] FormStoCMsg(int commandID, String status, String txt) throws IOException {
        byte[] header = MergeHead(4 + 4 + 1 + 64, commandID);  //响应回来的信息，其长度也是固定的
        byte[] ByteStatus = strToByteArray(status, 1);
        byte[] ByteDescription = strToByteArray(txt, 64);
        byte[] message = MergeByte(header, ByteStatus, ByteDescription);
        return message;
    }

    public static void sendMsg(byte[] msg, Socket socket) throws IOException {
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.write(msg);  //直接让包通过这个数据通道传输过去
            dos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> GetUserMap(File file) throws IOException {
        HashMap<String, String> map = new HashMap<>();
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file)); // 这是一个万能包裹器
        BufferedReader reader = new BufferedReader(inputStreamReader);  //将输入流对象放进这个缓冲区对象中，方便我们每次从中读取一定的数据
        String line = "";
        while ((line = reader.readLine()) != null) {
            String lineUserName = line.split(",")[0];
            String linePassword = line.split(",")[1];
            map.put(lineUserName,linePassword);
        }
        return map;
    }
}
