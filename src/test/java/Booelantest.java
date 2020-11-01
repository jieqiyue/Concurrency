import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import javax.swing.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Handler;

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
    public void testCompareFaile() throws IOException {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        boolean b = atomicBoolean.compareAndSet(false, true);

    }

    @Test
    public void testio() throws IOException {
        byte []bytes = new byte[1024];
//        bytes[0] = 2;
//        bytes[1] = 0;
//        bytes[2] = 4;
//        bytes[3] = 6;
//        bytes[4] = 3;
//        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
//        System.out.println(bis.markSupported());
//        bis.mark(3);

//        DataInputStream dis = new DataInputStream(bis);
//        int i = dis.readInt();
//        System.out.println(i);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(222);
        IntBuffer intBuffer = IntBuffer.allocate(11);
    }

    @Test
    public void readWriteFile() throws IOException {
//        final int SIZE = 1024;
//
//        FileChannel inChannel = new FileInputStream("D:\\1.jpeg").getChannel();
//        FileChannel outChannel = new FileOutputStream("D:\\算法\\1copy.jpeg").getChannel();
//
//        ByteBuffer dsts = ByteBuffer.allocate(SIZE);
//        while (inChannel.read(dsts) != -1) {
//            System.out.println();
//            dsts.flip();
//            outChannel.write(dsts);
//            dsts.clear();
//        }
//        inChannel.close();
//        outChannel.close();



        String s = "ababcbacadefegdehijhklij";
        Map<Character,Integer> lastApper = new HashMap<>();
        List<Integer> ret = new ArrayList<>();
        for(int i = 0;i < s.length();i++){
            lastApper.put(s.charAt(i),i);
        }
        int max = 0,left = 0;
        for (int i = 0; i < s.length();i++){
            max = Math.max(max,lastApper.get(s.charAt(i)));
            if (max == i){
                ret.add(i - left + 1);
                left = i + 1;
            }
        }
    }



}
