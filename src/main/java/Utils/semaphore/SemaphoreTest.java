package Utils.semaphore;

import javax.swing.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Classname SemaphoreTest
 * @Description TODO
 *      类似于一种许可证的形式。初始给定一个值，最多只有这么多个线程能够获得许可证。当许可证已经发放完毕的时候。
 *      只有当已经获得许可证的线程先去释放掉许可证，剩下在等待的线程才能够得到许可证（如果没有线程释放，则会阻塞住等待）。
 * @Date 2020/10/18 14:01
 * @Created by Jieqiyue
 */
public class SemaphoreTest {

    private static Semaphore semaphore = new Semaphore(2);
    public static void main(String[] args) {
        for (int i = 0 ;i < 2;i++){
            new Thread(){
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "is start ...");
                    try {
                        semaphore.acquire(2);
                        System.out.println(Thread.currentThread().getName() + "get the lock .. ");
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " release the lock...");
                    // 只释放了一个，所以，另外一个线程需要两个，它永远也拿不到。
                    semaphore.release(1);
                }
            }.start();
        }

        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("queue length "+semaphore.getQueueLength());
            System.out.println("available "+semaphore.availablePermits());
            System.out.println("======================");
        }
    }

    static class SemaphoreLock{
        private Semaphore semaphore = new Semaphore(2);

        public void lock() throws InterruptedException {
            // 获取许可证
            semaphore.acquire();
        }

        public void unlock(){
            // 放弃许可证
            semaphore.release();
        }
    }
}
