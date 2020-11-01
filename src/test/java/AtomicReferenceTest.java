import javax.sound.midi.Soundbank;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Classname AtomicReference
 * @Description AtomicStampedReference 的使用就是把cas算法加了一个版本号。来解决aba问题。
 * @Date 2020/10/7 16:05
 * @Created by Jieqiyue
 */
public class AtomicReferenceTest {

    /*
    *       这个里面的stamp就是版本号。
    * */
    public static void atomicStampedReference(){
        AtomicStampedReference stampedReference = new AtomicStampedReference(100,0);

        new Thread(()->{
            System.out.println("begin ... ");
            boolean b = stampedReference.compareAndSet(100, 101, 0, 1);
            System.out.println("100 to 101 is " + b);
            if (b == true){
                boolean b1 = stampedReference.compareAndSet(101, 100, 1, 2);
                System.out.println("101 to 100 is " + b1);
            }else{
                System.out.println("fail 100 to 101 ... ");
            }
            System.out.println("final stamp is " + stampedReference.getStamp());
        }).start();
    }
    public static void main(String[] args) {
        //3、如果当前值 == 预期值，则以原子方式将该值设置为给定的更新值。
        SimpleObject test = new SimpleObject("test3" , 30);
        AtomicReference<SimpleObject> atomicReference2 = new AtomicReference<>(test);
        Boolean bool = atomicReference2.compareAndSet(test, new SimpleObject("test4", 40));
        System.out.println("simpleObject  Value: " + bool + atomicReference2.get() );
        System.out.println(test);

        // 测试atomicStampedReference
        atomicStampedReference();
    }

    static class SimpleObject {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "SimpleObject{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public SimpleObject(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }


}
