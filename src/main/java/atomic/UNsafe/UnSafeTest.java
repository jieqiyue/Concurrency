package atomic.UNsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @Classname UnSafeTest
 * @Description TODO
 * @Date 2020/10/14 16:50
 * @Created by Jieqiyue
 */
public class UnSafeTest {

    private static Unsafe getUnsafe(){
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static long sizeOf(Object object){
        Unsafe unsafe = getUnsafe();
        Set<Field> fieldSet = new HashSet<>();
        Class<?> aClass = object.getClass();
        while(aClass != Object.class){
            Field[] declaredFields = aClass.getDeclaredFields();
            for(Field f : declaredFields){
                fieldSet.add(f);
            }
            aClass = aClass.getSuperclass();
        }

        long maxOffset = 0;
        for(Field f : fieldSet){
            long l = unsafe.objectFieldOffset(f);
            if(l > maxOffset){
                maxOffset = l;
            }
        }

        return  8 * ((maxOffset / 8)+ 1);
    }
    public static void main(String[] args) {
//        try {
//            Simple simple = Simple.class.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        try {
//            Class<?> aClass = Class.forName("atomic.UNsafe.UnSafeTest$Simple");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

         Unsafe unsafe = getUnsafe();
        try {
            Simple o =(Simple) unsafe.allocateInstance(Simple.class);
            System.out.println(o.getL());
            System.out.println(o.getClass().getClassLoader());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }



    }


    static class Simple{
        public long l = 0;

        public Simple(){
            this.l = 1;
            System.out.println("======");
        }

        public long getL() {
            return l;
        }
    }
}
