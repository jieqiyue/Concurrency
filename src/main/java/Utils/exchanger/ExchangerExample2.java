package Utils.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Classname ExchangerExample2
 * @Description TODO
 * @Date 2020/10/17 15:02
 * @Created by Jieqiyue
 */
public class ExchangerExample2 {

    public static void main(String[] args) {
        Exchanger<Integer> exchanger = new Exchanger<>();

        new Thread(){
            @Override
            public void run() {
//                AtomicInteger integer = new AtomicInteger(1);
                AtomicReference<Integer> value = new AtomicReference<>(1);

                while (true){
                    try {
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("Thread t1 has value" + value.get());
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
//                AtomicInteger integer = new AtomicInteger(1);
                AtomicReference<Integer> value = new AtomicReference<>(2);

                while (true){
                    try {
                        value.set(exchanger.exchange(value.get()));
                        System.out.println("Thread t2 has value" + value.get());
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
