package JVMBook.chapter4;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanchao
 * @date 2018/11/8 15:34
 * 使用 JConsole 查看内存、线程等情况
 */
public class JConsole {

    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
            System.out.println(i);
        }
        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(100);
    }
}
