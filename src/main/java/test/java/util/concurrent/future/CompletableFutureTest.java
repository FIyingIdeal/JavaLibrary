package test.java.util.concurrent.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yanchao
 * @date 2019-08-20 15:40
 * {@link CompletableFuture} 是 java8 开始提供的一个类，其对 {@link Future} 进行了扩展。
 * 最重要的一个扩展是它可以将一个同步的方法转换为异步执行
 * 《Java8实战》中提到的一个关于 {@link CompletableFuture} 与 {@link java.util.stream.Stream} 的选择问题（P234）：
 *      当进行计算密集型（CPU密集型）的任务时，推荐选用 {@link java.util.stream.Stream}；
 *      当进行 I/O 密集型（包括网络连接等待）的任务时，推荐选用 {@link CompletableFuture}。
 *  另外：{@link  CompletableFuture} 较 {@link java.util.stream.Stream} 更灵活，可以根据需要设定线程数量（因为其本身就是基于线程池的）
 */
@Slf4j
public class CompletableFutureTest {

    /**
     * 模拟一个耗时操作的任务
     */
    private static class Task {

        private String name;

        public Task(String name) {
            this.name = "task" + name;
        }

        public String run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // do other things!
            }
            System.out.println("Thread name is " + Thread.currentThread().getName() + ", MyTask name is " + name);
            return name;
        }
    }

    private List<Task> getTaskList(int taskNum) {
        // 耗时操作任务列表，设置了 3 个
        List<Task> tasks = new ArrayList<>();
        for (int i = 0; i < taskNum; i++) {
            tasks.add(new Task(String.valueOf(i)));
        }
        return tasks;
    }

    @Test
    public void supplyAsync() {

        // 耗时操作任务列表，设置了 3 个
        List<Task> tasks = getTaskList(3);

        // 同步方式执行任务列表中的任务
        log.info("同步方式执行任务");
        long startTime = System.currentTimeMillis();
        for (Task task : tasks) {
            task.run();
        }
        log.info("同步方式执行任务耗时： {} ms", System.currentTimeMillis() - startTime);

        // stream() 也是同步方式执行的，且执行顺序是和列表中的元素顺序一致。
        // 如果想要并行执行的话，需要使用 parallelStream()，并行后没法保证顺序
        startTime = System.currentTimeMillis();
        tasks.forEach(Task::run);
        log.info("Stream 同步方式执行任务耗时： {} ms", System.currentTimeMillis() - startTime);

        // parallelStream() 并行执行任务，没法保证任务执行顺序
        startTime = System.currentTimeMillis();
        tasks.parallelStream().forEach(Task::run);
        log.info("parallelStream 并行执行任务耗时： {} ms", System.currentTimeMillis() - startTime);

        // 通过 CompletableFuture.supplyAsync 异步执行任务
        startTime = System.currentTimeMillis();
        List<CompletableFuture<String>> futures = tasks.stream()
                .map(task -> CompletableFuture.supplyAsync(task::run)).collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).forEach(System.out::println);
        log.info("CompletableFuture 异步执行任务耗时： {} ms", System.currentTimeMillis() - startTime);
    }

    @Test
    public void thenApply() {
        // 耗时操作 任务列表，设置了 10 个任务
        long startTime = System.currentTimeMillis();
        List<Task> tasks = getTaskList(10);
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<CompletableFuture<String>> taskList = tasks.stream()
                // 使用 CompletableFuture.supplyAsync() 将任务转换为异步执行。还可以执行使用的线程池
                .map(task -> CompletableFuture.supplyAsync(task::run, threadPool))
        // ---->thenApply 在这里～ 通过 thenApply 对 CompletableFuture<T> 中包含的值进行转换处理（Function(? super T, U)）
                .map(future -> future.thenApply(String::toUpperCase)).collect(Collectors.toList());
        taskList.stream().map(CompletableFuture::join).forEach(System.out::println);
        log.info("CompletableFuture<T>.thenApply(Function(? super T, R)) const time : {} ms",
                System.currentTimeMillis() - startTime);
    }

    /**
     * {@link CompletableFuture#thenCompose(Function)} 用于将两个异步操作串联起来
     * 当第一个操作完成时，将其结果作为参数传递给第二个参数。
     */
    @Test
    public void thenCompose() {
        // 耗时操作 任务列表，设置了 10 个任务
        long startTime = System.currentTimeMillis();
        List<Task> tasks = getTaskList(10);
        ExecutorService threadPool = Executors.newFixedThreadPool(5);
        List<CompletableFuture<String>> futures = tasks.stream()
                .map(task -> CompletableFuture.supplyAsync(task::run))
                .map(future -> future.thenApply(String::toUpperCase))
                .map(future -> future.thenCompose(
                        taskName -> CompletableFuture.supplyAsync(
                                () -> TaskDetails.getTaskDetails(taskName)
                        ))).collect(Collectors.toList());
        futures.stream().map(CompletableFuture::join).forEach(System.out::println);
        log.info("thenCompose const time: {} ms", System.currentTimeMillis() - startTime);
    }

    private static class TaskDetails {

        public static String getTaskDetails(String taskName) {
            // 模拟获取任务详情的耗时
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
                // do other things!
            }
            return "Thread name is " + Thread.currentThread().getName() + "   " + taskName + " details...";
        }
    }

    @Test
    public void thenComposeAsync() {}

    @Test
    public void thenCombine() {}

    @Test
    public void thenAccept() {}

}
