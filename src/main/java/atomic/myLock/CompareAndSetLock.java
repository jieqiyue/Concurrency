package atomic.myLock;

import com.sun.net.httpserver.Authenticator;

import java.time.temporal.ValueRange;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Classname CompareAndSetLock
 * @Description tryLock 之后，如果失败立即抛出异常。在freeLock中也加入了一个currentGetLock来分辨是不是当前
 * 获得锁的那个线程来释放锁。如果不是，就直接return false。
 * @Date 2020/9/29 15:50
 * @Created by Jieqiyue
 */
public class CompareAndSetLock {

    private final AtomicInteger lock = new AtomicInteger(0);
    Thread currentGetLock ;
    public CompareAndSetLock(){

    }

    public boolean tryLock() throws GetLockException{
        boolean sucess = lock.compareAndSet(0, 1);
        if(!sucess){
            throw new GetLockException("get the lock failed. ");
        }else{
            currentGetLock = Thread.currentThread();
            return sucess;
        }

    }

    public boolean freeLock(){
        if(0 == lock.get()){
            return false;
        }

        if(currentGetLock == Thread.currentThread()){
           return lock.compareAndSet(1, 0);
        }
        return false;
    }
}
