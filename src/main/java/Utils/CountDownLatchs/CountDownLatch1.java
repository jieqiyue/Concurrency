package Utils.CountDownLatchs;

import java.util.concurrent.CountDownLatch;

/**
 * @Classname CountDownlatch
 * @Description 这里有一个要注意点，就是可以多个线程用同一个latch来wait。
 * @Date 2020/10/14 19:49
 * @Created by Jieqiyue
 */
public class CountDownLatch1 {


    public static void main(String[] args) throws InterruptedException {
        // 用法 ： 比如说某一个任务分为三个阶段来做的。然后第一三阶段是串行化处理的。但是第二阶段能用并行化处理。但是在第三阶段开始的时候必须要等待第二阶段完全结束。
        // 这个时候就可以用一个latch来计数。在第二阶段每一次成功的时候，就把这个latch--，直到减到零为止。这个时候，原本是wait的线程就可以继续去执行第三阶段了。

        final CountDownLatch latch = new CountDownLatch(1);

        new Thread(()->{
            System.out.println("Do some init work .... ");
            try {
                System.out.println("waiting for another thread's data ... ");
                Thread.sleep(1000);
                latch.await(); // 阻塞在这里，等待另外一个线程把latch的值减为0。
                System.out.println("all work done ... ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            try {
                System.out.println("producing data ... ");
                Thread.sleep(2000);
                latch.countDown();
                System.out.println("data produced ... ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 什么都不做。启动线程之后就await住。
        new Thread(()->{
            try {
                latch.await();
                System.out.println("release .... ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.currentThread().join();
    }
}
