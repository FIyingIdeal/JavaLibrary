package test.java.util.concurrent.completablefutuer;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author yanchao
 * @date 2020-06-13 17:22
 */
public class Shop {

    private Random random = new Random();
    private String name;

    public Shop() {
        this.name = "default-shop";
    }

    public Shop(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getPrice(String product) {
        return calculatePrice(product);
    }

    /**
     * {@link #getPrice(String)} 的异步版本
     *      1. 启用新的线程调用同步方法；
     *      2. 线程中调用了 {@link CompletableFuture#complete(Object)} 方法将结果保存起来；
     * @see {@link #getPriceAsyncSimple(String)}    是对该方法的简化
     */
    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                // 错误处理，将异常传递给 futurePrice
                // 若不处理则线程逻辑执行抛出被杀死后将无法被传递出去， get() 方法将会永远阻塞
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    /**
     * 该方法使用 {@link CompletableFuture#supplyAsync(Supplier)} 对 {@link #getPriceAsync(String)} 进行简化，
     *   通过简单的一条语句实现了其所有的逻辑：构造对象，异步运行，错误处理等等
     *
     * {@link CompletableFuture#supplyAsync(Supplier)} 会将任务交由 ForkJoinPool 池中的某个执行线程来执行；
     * 而其重载方法 {@link CompletableFuture#supplyAsync(Supplier, Executor)} 还可以传入一个线程池来执行任务
     */
    public Future<Double> getPriceAsyncSimple(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    /**
     * 获取商品的折扣价
     */
    public String getPriceWithDiscount(String product) {
        double price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", this.name, price, code);
    }

    private double calculatePrice(String product) {
        // 模拟调用延迟
        delay();
        System.out.println("===== Thread name is " + Thread.currentThread().getName());
        return random.nextDouble() * product.charAt(0) + product.charAt(1);
    }

    private static void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doSomethingElse() {
        System.out.println("Do something else~");
    }

    public static void main(String[] args) {
        Shop shop = new Shop();
        long startTime = System.nanoTime();
        // Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
        Future<Double> futurePrice = shop.getPriceAsyncSimple("my favorite product");
        long invocationTime = (System.nanoTime() - startTime) / 1_000_000;
        System.out.println("Invocation returned after " + invocationTime + " msecs");

        shop.doSomethingElse();

        try {
            double price = futurePrice.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        long retrievalTime = ((System.nanoTime() - startTime) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
    }
}
