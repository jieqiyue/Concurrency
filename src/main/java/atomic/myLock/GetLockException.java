package atomic.myLock;

/**
 * @Classname GetLockException
 * @Description TODO
 * @Date 2020/9/29 15:53
 * @Created by Jieqiyue
 */
public class GetLockException extends Exception{

    public GetLockException(){
        super();
    }

    public GetLockException(String message){
        super(message);
    }
}
