package Utils.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Classname ReentrantLockExample
 * @Description
 *      1. 介绍Lock显示锁。
 *      2. 比较Lock显示锁和synchronized的区别。
 *              lock接口中的lock方法不可以被中断。这个和synchronized类似。
 *              lock可以有可以被中断的方法。lockInterruptibly()。
 *
 * @Date 2020/10/20 19:23
 * @Created by Jieqiyue
 */
public class ReentrantLockExample {

    public static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            lockbysync();
        });
        thread1.start();
        Thread thread2 = new Thread(() -> { lockbysync();});
        TimeUnit.SECONDS.sleep(2);
        thread2.start();

        TimeUnit.SECONDS.sleep(2);
        thread2.interrupt();
    }

    public static void testUnInterruptibly(){
        try {
            //            lock.lock();  使用lock方法是不能被中断的。
            lock.lockInterruptibly();// 使用这个方法是会被中断的。
            System.out.println(Thread.currentThread().getName() + "get the lock ... ");
            while (true){

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     *      用synchronized关键字来使用锁，看能不能被打断。
     *      是不能被打断的。这个和lock的lock方法一样。
     */
    public static void lockbysync(){
        synchronized (ReentrantLockExample.class){
            System.out.println(Thread.currentThread().getName() + "get the syhclock ... ");
            while (true){

            }
        }
    }
}
