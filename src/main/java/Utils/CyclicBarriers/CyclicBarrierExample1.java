package Utils.CyclicBarriers;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @Classname CyclicBarriesExample1
 * @Description 只有所有人都做完了这个事情，才会继续往下走。和latch有点类似。
 *      还有一个点。就是构造这个barrier的时候，传入第二个参数，这个参数作为所有工作都做完之后的回调函数。
 * @Date 2020/10/15 19:09
 * @Created by Jieqiyue
 */
public class CyclicBarrierExample1 {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2,()->{
            System.out.println("all works is done ... ");
        });

        Thread thread1 = new Thread(() -> {
            System.out.println("t1 begin ...");
            try {
                Thread.sleep(100);
                cyclicBarrier.await();
                System.out.println("t1 done ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });

        Thread thread2 = new Thread(() -> {
            System.out.println("t2 begin ...");
            try {
                Thread.sleep(4_000);
                cyclicBarrier.await();
                System.out.println("t2 done ...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        });
        thread1.start();
        thread2.start();
        int numberWaiting = cyclicBarrier.getNumberWaiting();
        boolean broken = cyclicBarrier.isBroken();
        final int parties = cyclicBarrier.getParties();

        Thread.sleep(9000);
        cyclicBarrier.reset();



    }
}
