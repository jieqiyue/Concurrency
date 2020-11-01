package Utils.exchanger;

import atomic.Array.AtomicInteger;

import java.util.concurrent.Exchanger;

/**
 * @Classname ExchanggerExample1
 * @Description exchanger必须要两个两个配对出现。如果多个线程在同一个exchange里面交换数据。那么不能保证是哪两个线程
 *              一定能够交换到。
 *
 *              另外一个需要注意的点就是，两个线程交换过去的东西是同一个（同一个对象）。所以说可能有线程安全问题。
 * @Date 2020/10/17 14:16
 * @Created by Jieqiyue
 */
public class ExchangerExample1 {
    public static void main(String[] args) {

        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(()->{
            try {
                String ret = exchanger.exchange("i am come from t1.");
                System.out.println(ret);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();
        new Thread(()->{
            try {
                String ret = exchanger.exchange("i am come from t2.");
                System.out.println(ret);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();


    }

}
