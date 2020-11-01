package Utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Classname SemaphoreAPI
 * @Description
 *  1.hasQueuedThreads  这个是protect方法。可以通过继承的方式来使用它。
 *  2.tryAcquire    尝试着去获得，如果没有获得到许可证也不会阻塞。
 *  3.drainPermits   一次性把所有的许可证都拿走。
 * @Date 2020/10/18 14:17
 * @Created by Jieqiyue
 */
public class SemaphoreAPI {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(2);

        Thread t1 = new Thread(){
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    System.out.println("t1 get the lock ");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        Thread t2 = new Thread(){
            @Override
            public void run() {
//                  semaphore.acquire();// 会抛出中断异常，能够被中断。
                    semaphore.acquireUninterruptibly();
                    System.out.println("t2 get the lock ");
            }
        };
        t2.start();

        t2.interrupt();// 虽然这里中断了，但是t2不予理会。

    }

}
