package Utils.CountDownLatchs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Classname CountDownLatchAPI
 * @Description
 *      1. 在一个线程await之后。有两种情况可以退出。一种是等待latch减为0。另外一种就是interrupt这个被阻塞的线程。
 *
 * @Date 2020/10/15 16:15
 * @Created by Jieqiyue
 */
public class CountDownLatchAPI {

    final static CountDownLatch latch = new CountDownLatch(1);

    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(10_000);
                latch.countDown();
                System.out.println("release .... ");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        try {
            latch.await(1000, TimeUnit.MILLISECONDS);
            // 最多等1秒钟。一秒钟之后不管是不是0，都继续执行。
            System.out.println("doing ... ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
