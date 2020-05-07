import java.io.IOException;

public interface UserMsgCoder_test {
    byte[] toWire(UserMsg_test msg) throws IOException;
    UserMsg_test fromWire(byte[] input) throws IOException;
}
