package Utils.Lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

/**
 * @Classname isExclusion
 * @Description lock 显示锁是排它锁。只要有一个线程抢到了这个锁，这个线程不去主动释放这个锁的话，
 *  另外的线程是不能继续调用lock抢到锁的。
 * @Date 2020/10/22 18:53
 * @Created by Jieqiyue
 */
public class isExclusion {

    private static final Lock lock = new ReentrantLock();
    public static void main(String[] args) {

        IntStream.range(0,2).forEach((i)->{
            new Thread(()->{
                lock.lock();
                try {
                    while (true){
                        System.out.println(Thread.currentThread().getName() + "is working ...");
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }finally {
                    lock.unlock();
                }

            },String.valueOf(i)).start();
        });


    }
}
