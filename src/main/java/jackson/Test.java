package jackson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author yanchao
 * @date 2018/10/24 10:49
 */
public class Test {

    public static void method(List<Integer> list, String initVal) throws InterruptedException {
        synchronized(list) {
            if ("+".equals(initVal)) {
                System.out.println("add operation");
                TimeUnit.SECONDS.sleep(3);
                System.out.println(list.stream().reduce(0, Integer::sum));
                return;
            } else {
                System.out.println("max operation : " + list.stream().reduce(1, Integer::max));
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1,2,3,4,5));
        List<Integer> list2 = new ArrayList<>(Arrays.asList(1,2,3,4,6));

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new MyTask(list1, "+"));
        TimeUnit.SECONDS.sleep(2);
        executorService.submit(new MyTask(list1, "max"));
        executorService.shutdown();
    }

    static class MyTask implements Runnable {

        private List<Integer> list;
        private String operator;

        public MyTask(List<Integer> list, String operator) {
            this.list = list;
            this.operator = operator;
        }

        @Override
        public void run() {
            try {
                method(list, operator);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
