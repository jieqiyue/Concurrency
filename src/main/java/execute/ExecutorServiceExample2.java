package execute;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname ExecutorServiceExample2
 * @Description 在ThreadPoolExecutor中有一个getQueue方法可以得到线程池里面的任务队列的queue。那么，我们可不可以直接将任务
 * 加入到这个queue中去呢。如果直接加入的话，这个任务不会去执行。因为线程池里面没有active的线程。虽然给定了核心线程数，但是没有active线程啊。
 * 所以得先用excutor加入一个线程。让线程池中有active的线程。才行。
 * @Date 2020/11/1 15:19
 * @Created by Jieqiyue
 */
public class ExecutorServiceExample2 {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor)Executors.newFixedThreadPool(3);

        executorService.execute(()->{
            System.out.println("i am before to excutor...");
        });

        BlockingQueue<Runnable> queue = executorService.getQueue();
        queue.add(()->{
            System.out.println("after");
        });

    }
}
