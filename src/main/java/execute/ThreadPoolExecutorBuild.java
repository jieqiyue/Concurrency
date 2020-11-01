package execute;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * @Classname ThreadPoolExecutorBuild
 * @Description TODO
 * @Date 2020/10/27 15:45
 * @Created by Jieqiyue
 */

public class ThreadPoolExecutorBuild {
    /**
     *                               int corePoolSize,
     *                               int maximumPoolSize,
     *                               long keepAliveTime,
     *                               TimeUnit unit,
     *                               BlockingQueue<Runnable> workQueue,
     *                               ThreadFactory threadFactory,
     *                               RejectedExecutionHandler handler
     */
    static class createThread implements ThreadFactory{
        @Override
        public Thread newThread(Runnable r) {
            Thread t1  = new Thread(){
                    @Override
                    public void run() {
                        r.run();
                    }
                };
                return t1;
        }

    }
    public static ExecutorService executorService = new ThreadPoolExecutor(2,10,30,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(3),new createThread(),
            // 自定义的构造线程的方法,也可以像上面一样传入一个自定义构造器。在这个自定义构造器里面
            // 自己可以写一些方法处理异常发生的情况。类似于继承ThreadPoolExecutor重写里面的beforeexecutor方法一样。
            // 因为runnable不能处理异常，也没有返回值。
//            (task)->{
//                Thread t1  = new Thread(){
//                    @Override
//                    public void run() {
//                        task.run();
//                    }
//                };
//                return t1;
//            },
            new ThreadPoolExecutor.AbortPolicy());


    public static void main(String[] args) throws InterruptedException {
        buildThreadPool();

        TimeUnit.SECONDS.sleep(2);

        //executorService.shutdown();// shtudown 方法会平滑的关闭线程池。即使有未执行的线程。也会等它执行完毕再去关闭。
        List<Runnable> runnables = executorService.shutdownNow();

        System.out.println("使用shutdownNow方法。此时返回的List里面的任务有"+ runnables.size() + "个 ...");
        executorService.awaitTermination(5,TimeUnit.SECONDS);
        System.out.println("all works is done .. ");


    }


    public static void buildThreadPool(){
//  传入5个线程每一个线程在执行完毕之后都会输出一句话。
        IntStream.range(1,6).boxed().forEach((i)->{
            executorService.submit(()->{
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i + "is done ... ");
            });
        });

    }
}
