import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMsg_test {


    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    //一个用户注册功能的密码有如下要求：由数字和字母组成，并且要同时含有数字和字母，且长度要在8-16位之间。
    String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$";  //用来验证密码的字符串


    public boolean isRegister() {
        return isRegister;
    }

    public void setRegister(boolean register) {
        this.isRegister = register;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) throws IllegalArgumentException{
        if(isSpecialChar(username)){
            throw new IllegalArgumentException("Bad username: "+username);
        }
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(!password.matches(regex)){
            throw new IllegalArgumentException("Bad password: "+password);
        }
        this.password = password;
    }

    private boolean isRegister;

    public boolean isResponse() {
        return isResponse;
    }

    public void setResponse(boolean response) {
        this.isResponse = response;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        this.isRequest = request;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private boolean isLogin;
    private boolean isResponse;
    private boolean isRequest;

    private boolean status;
    private String description;
    private String username;
    private String password;

    public UserMsg_test(boolean isResponse, boolean isRequest, boolean isRegister, boolean isLogin, String username, String password)
    {
        //在这个通用类中还没有限制数据包的具体格式，初步是采用二进制的方式进行处理
        if(isRegister && isLogin){
            //消息只能同时处于一个状态
            throw new IllegalArgumentException("Must have a certain status");
        }
        if(!isRegister && !isLogin){
            throw new IllegalArgumentException("Must have a status");
        }
        if("".equals(username) || username == null){
            throw new IllegalArgumentException("must have a username");
        }
        if(!isSpecialChar(password)){
            throw new IllegalArgumentException("请输入8-16位仅包含数字与字母的密码");
        }
        this.isRegister = isRegister;
        this.isLogin = isLogin;
        this.username = username;
        this.password = password;
    }

    public String toString(){
        String res = (isRegister?"register":"login")+ " for username: "+username+","+"password: "+ password;
        return res;
    }

}
