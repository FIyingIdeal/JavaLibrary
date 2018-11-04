package test.java.util.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yanchao
 * @date 2018/11/4 12:08
 * 模拟文件读取：多个线程读取不同文件，读取前先检查文件是否正确，如果有一个文件不正确则不进行任何文件的读取操作
 */
public class CheckThenRead {

    static class CheckFileTask implements Runnable {
        private String fileName;
        // 模拟文件状态
        private boolean fileStatus;
        private CyclicBarrier barrier;

        public CheckFileTask(String fileName, boolean fileStatus, CyclicBarrier barrier) {
            this.fileName = fileName;
            this.fileStatus = fileStatus;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.println("开始检查 " + fileName);
            if (!fileStatus) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(fileName + " 文件异常");
            }
            System.out.println(fileName + " 文件正常");
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println("读取文件 " + fileName);

        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        int fileCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(fileCount);
        for (int fileIndex = 1; fileIndex <= fileCount; fileIndex++) {
            boolean fileStatus = fileIndex % 2 == 0 ? false : true;
            executor.submit(new CheckFileTask(String.valueOf(fileIndex), fileStatus, barrier));
        }
        executor.shutdown();
    }
}
