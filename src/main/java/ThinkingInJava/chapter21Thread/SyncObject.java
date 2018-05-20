package ThinkingInJava.chapter21Thread;

import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/5/8 23:01
 */
public class SyncObject {

    public static void main(String[] args) throws Exception {
        final DualSynch dualSynch = new DualSynch();
        new Thread() {
            @Override
            public void run() {
                dualSynch.f();
            }
        }.start();
        dualSynch.g();
    }
}

class DualSynch {
    private Object syncObject = new Object();
    public synchronized void f() {
        for (int i = 0; i < 50; i++) {
            System.out.println("f()");
            Thread.yield();
        }
    }

    public void g() {
        synchronized(syncObject) {
            for (int i = 0; i < 50; i++) {
                System.out.println("g()");
                Thread.yield();
            }
        }
    }
}
