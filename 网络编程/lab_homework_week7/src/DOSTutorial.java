import java.io.*;

public class DOSTutorial {
    /**
     * 学习一下dataoutputstream的使用
     */
     public static void main(String[] argv) throws IOException {
         //通过调用这个类，相当于一个文件管道，可以结合文件输出流输出输出
         DataOutputStream dos = new DataOutputStream(new FileOutputStream("binary.data"));
         //观察源码可以发现，这一个也都是synchronized的
         dos.writeInt(45);
         dos.writeFloat(4545);
         dos.writeDouble(109.123);

         dos.close();

         DataInputStream dis = new DataInputStream(new FileInputStream("binary.data"));

         int in123 = dis.readInt();
         float float2 = dis.readFloat();
         double double3 = dis.readDouble();

         dis.close();

         System.out.println("int123 ="+in123);
         System.out.println("float2 = "+float2);
         System.out.println("double3 =" + double3);
     }

}
