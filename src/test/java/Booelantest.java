import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Classname Booelan
 * @Description TODO
 * @Date 2020/9/29 16:28
 * @Created by Jieqiyue
 */
public class Booelantest {

    @Test
    public void testBoolean(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean b = atomicBoolean.compareAndSet(true, false);
        assert b == true;
    }

    @Test
    // 这个期望值填入的是false，但是是true，所以这个测试不成功。
    public void testCompareFaile(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean b = atomicBoolean.compareAndSet(false, true);
        assert b == true;
    }

}
