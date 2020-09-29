package atomic.BooleanUseage;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Classname AtomicBooleanFlag
 * @Description 用这个AtomicBoolean来代替原来的boolean基本类型作为flag。
 * @Date 2020/9/29 16:41
 * @Created by Jieqiyue
 */
public class AtomicBooleanFlag {
    private final static AtomicBoolean falg = new AtomicBoolean(true);
    public static void main(String[] args) throws InterruptedException {
            new Thread(){
                @Override
                public void run() {
                    while (falg.get()){
                        System.out.println(" i am working ...");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("i am finished ..");
                }
            }.start();
            Thread.sleep(5000);
        System.out.println("i am going to finish the job ... ");
        falg.set(false);
    }
}
