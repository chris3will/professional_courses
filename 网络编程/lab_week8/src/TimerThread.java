import java.time.LocalTime;

public class TimerThread extends  Thread{
    @Override
    public void run(){
        while(true){
            System.out.println(LocalTime.now());
            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                break;
            }
        }
    }
}
