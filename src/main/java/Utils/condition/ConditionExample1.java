package Utils.condition;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Classname ContionExample1
 * @Description 使用condition可以来代替wait，notify对。condition的await和object草wait相似。都会放弃当前抢到的锁。
 * @Date 2020/10/22 18:51
 * @author Jieqiyue
 * @Created by Jieqiyue
 */
public class ConditionExample1 {

    private static int DATA;

    private static final Lock LOCK = new ReentrantLock();

    private static final Condition CONDITION = LOCK.newCondition();

    private static boolean noUse = true;
    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            while (true){
                consume();
            }
        }).start();
        new Thread(()->{
            while (true){
                produce();
            }
        }).start();
    }

    private static void produce(){
        try {
            LOCK.lock();
            while (noUse){
                CONDITION.await();
            }

            TimeUnit.SECONDS.sleep(1);
            System.out.println("P: " + DATA++ );
            noUse = true;
            CONDITION.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
    private static void consume(){
        try {
            LOCK.lock();
            while (!noUse){
                CONDITION.await();
            }

            TimeUnit.SECONDS.sleep(1);
            System.out.println("C: " + DATA );
            noUse = false;
            CONDITION.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            LOCK.unlock();
        }
    }
}
