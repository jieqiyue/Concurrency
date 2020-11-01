package execute;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @Classname ExecutorServiseExample1
 * @Description TODO
 * @Date 2020/10/29 16:47
 * @Created by Jieqiyue
 */
public class ExecutorServiceExample1 {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testInvokeAll();
//        testInvokeAll();
        testSubmitRunnable();
    }


    /**
     *  invokeAny 是一个同步方法。
     *  在invokeAny之后的List中只要有一个线程返回了，那么剩下的线程就不会继续执行。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void testInvokeAny() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);


        List<Callable<Integer>> collect = IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(i + "is executor ... ");
                    return i;
                }
        ).collect(toList());

        executorService.invokeAny(collect);
        System.out.println("===========================");
    }

    /**
     * 加了个timeout，最多等你多少秒。
     * @throws ExecutionException
     * @throws InterruptedException
     * @throws TimeoutException
     */

    public static void testInvokeAnyTimeout() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> collect = IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                    System.out.println(i + "is executor ... ");
                    return i;
                }
        ).collect(toList());

        Integer Value = executorService.invokeAny(collect, 3, TimeUnit.SECONDS);
        System.out.println("===========================" + Value);
    }


    public static void testInvokeAll() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        List<Callable<Integer>> collect = IntStream.range(0, 5).boxed().map(i ->
                (Callable<Integer>) () -> {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(i + "is executor ... ");
                    return i;
                }
        ).collect(toList());


        List<Future<Integer>> futures = executorService.invokeAll(collect);
        futures.stream().map(future ->{
//            try {
//                return future.get();  ????
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            Integer integer = null;
            try {
                integer = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return integer;
        }).forEach(System.out::println);


        System.out.println("===========================" );
    }

    /**
     * 这里面submit会返回一个future，如果在runnable中没有返回东西，那么这个future会get到null。
     * get方法是同步的。
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static void testSubmitRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Future<?> submit = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 1;
        });

        System.out.println(((ThreadPoolExecutor)executorService).getActiveCount());
        Object o = submit.get();
        System.out.println("result is " + o);
    }


}
