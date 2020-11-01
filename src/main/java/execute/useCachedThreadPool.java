package execute;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Classname useCachedThreadPool
 * @Description 只有在过了给定的秒数之后，并且当前线程数量大于核心线程数量的时候，才回去回收线程。只有这两个条件都满足才会去回收。
 * @Date 2020/10/28 15:55
 * @Created by Jieqiyue
 */
public class useCachedThreadPool {
    public static void main(String[] args) {
//        useCachedThreadPool();
//        useFixedThreadPool();
        useSingleThreadPool();
    }

//    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
//                                      60L, TimeUnit.SECONDS,
//                                      new SynchronousQueue<Runnable>());

    /**
     * 该线程池的使用情景就是短任务。因为最大任务数量不限制。所以会不断创建线程。如果在60秒内提交了过多的任务。
     * 并且这些任务在60秒内没有做完，就有可能oom。
     */
    private static void useCachedThreadPool(){
        ExecutorService executorService = Executors.newCachedThreadPool();

        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
    }

    /**
     *  这个就是指定了多少个线程就一定是多少个线程。因为核心线程数和最大线程数都是一样的。
     */
    private static void useFixedThreadPool(){
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
    }

    /**
     * SingleThread和单个线程的区别。
     *      1. Thread执行完一个任务之后，生命周期结束。
     *      2. Thread不能把任务存放到queue中。
     *   new FinalizableDelegatedExecutorService
     *             (new ThreadPoolExecutor(1, 1,
     *                                     0L, TimeUnit.MILLISECONDS,
     *                                     new LinkedBlockingQueue<Runnable>()));
     *     }
     *
     *     装饰器设计模式。没有暴露ThreadPoolExecutor中的方法。
     */
    private static void useSingleThreadPool(){

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 不能进行下面的写法。
//        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
    }
}
