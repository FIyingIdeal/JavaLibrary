package test.java.util.concurrent.completablefutuer;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author yanchao
 * @date 2020-06-13 18:19
 */
public class BestPriceFinder {

    private List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"), new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"));


    public List<String> findPrices(String product) {
        return this.shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());
    }

    @Test
    public void findPricesTest() {
        long start = System.nanoTime();
        System.out.println(findPrices("xiaomi"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // Done in 4174 msecs
    }

    public List<String> findPricesParallel(String product) {
        return this.shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(Collectors.toList());

    }

    @Test
    public void findPricesParallelTest() {
        long start = System.nanoTime();
        System.out.println(findPricesParallel("xiaomi"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // Done in 1174 msecs
    }

    /**
     * {@link CompletableFuture#join()} 是阻塞性方法，其调用位置直接影响了方法的执行效率
     * @see #findPricesAsync(String)
     */
    public List<String> findPricesAsyncWithSameStream(String product) {
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                        // 注意： join() 是阻塞性的方法，这样的话其实各个 shop 之间查询价格还是同步顺序执行的
                        .join()
                ).collect(Collectors.toList());
    }

    @Test
    public void findPricesAsyncWithSameStreamTest() {
        long start = System.nanoTime();
        System.out.println(findPricesAsyncWithSameStream("xiaomi"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // Done in 4173 msecs
    }

    /**
     * 较 {@link #findPricesAsyncWithSameStream(String)} 使用了两个 stream，但要比使用一个 stream 快很多
     *   第一个 stream 仅仅将同步方法转换为异步方法，不会等待方法执行完成（与使用一个 stream 的区别）；
     *   第二个则会等待方法结果返回，且不同任务在不同的线程中执行；
     * 这样的话两个 stream 就是并行的了，所以要快一些
     *
     * @see #findPricesAsyncWithSameStream(String)
     * @see #findPricesAsyncWithThreadPool(String)
     */
    public List<String> findPricesAsync(String product) {
        List<CompletableFuture<String>> priceFuture = this.shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product))
                )).collect(Collectors.toList());

        return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @Test
    public void findPricesAsyncTest() {
        long start = System.nanoTime();
        System.out.println(findPricesAsync("xiaomi"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // Done in 2159 msecs (3个线程执行4个任务，所以会慢一点)
    }



    /**
     * 较 {@link #findPricesAsync(String)} 增加了一个自定义线程池，速度也要快很多
     * @see #findPricesAsync(String)
     * @see #findPricesAsyncWithSameStream(String)
     */
    public List<String> findPricesAsyncWithThreadPool(String product) {
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 10),
                r -> {
                    Thread thread = new Thread(r, "shop-thread");
                    thread.setDaemon(true);
                    return thread;
                });
        List<CompletableFuture<String>> priceFuture = this.shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)),
                        executorService
                )).collect(Collectors.toList());

        return priceFuture.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @Test
    public void findPricesAsyncWithThreadPollTest() {
        long start = System.nanoTime();
        System.out.println(findPricesAsyncWithThreadPool("xiaomi"));
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
        // Done in 1183 msecs
    }


    @Test
    public void findPricesSyncWithDiscount() {
        long start = System.nanoTime();
        System.out.println(
                shops.stream()
                        .map(shop -> shop.getPriceWithDiscount("xiaomi"))
                        .map(Quote::parse).map(Discount::applyDiscount).collect(Collectors.toList())
        );
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    /**
     * 使用 {@link CompletableFuture#thenCompose(Function)} 将两个有关联的同步任务整合为一组任务，多组任务并行执行
     */
    @Test
    public void findPricesAsyncWithDiscount() {
        ExecutorService executorService = Executors.newFixedThreadPool(Math.min(shops.size(), 10),
                r -> {
                    Thread thread = new Thread(r, "shop-thread");
                    thread.setDaemon(true);
                    return thread;
                });
        long start = System.nanoTime();
        String product = "huawei";
        List<CompletableFuture<String>> pricesFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPriceWithDiscount(product)))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(
                                () -> Discount.applyDiscount(quote), executorService)
                )).collect(Collectors.toList());
        List<String> result = pricesFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println(result);
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("Done in " + duration + " msecs");
    }

    /**
     * 使用 {@link CompletableFuture#thenCombine(CompletionStage, BiFunction)} 方法将两个"过程"无关联的任务但结果又关联的
     *  CompletableFuture 整合起来
     */
    @Test
    public void findPricesAsyncWithRate() {
        String product = "huawei";
        ExchangeService exchangeService = new ExchangeService();
        List<CompletableFuture<Double>> result = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product)))
                .map(future -> future.thenCombine(
                        CompletableFuture.supplyAsync(
                                () -> exchangeService.getRate(ExchangeService.Money.RMB, ExchangeService.Money.USD)),
                                (r1, r2) -> r1 * r2)
                ).collect(Collectors.toList());
        List<Double> prices = result.stream().map(CompletableFuture::join).collect(Collectors.toList());
        System.out.println("prices are : " + prices);
    }
}
