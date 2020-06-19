package test.java.util.concurrent.completablefutuer;

import java.util.concurrent.TimeUnit;

/**
 * 商店折扣服务
 * @author yanchao
 * @date 2020-06-14 10:24
 */
public class Discount {

    /**
     * 折扣码
     */
    public enum Code {
        /**
         * 无折扣
         */
        NONE(0),
        SILVER(5),
        GOLD(10),
        PLATINUM(15),
        DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        System.out.println(Thread.currentThread().getName() + " : Discount applyDiscount execute~");
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscount());
    }

    private static double apply(double price, Code code) {
        // 模拟请求延迟
        delay();
        return Double.parseDouble(String.format("%.2f", price * (100 - code.percentage) / 100 ));
    }

    private static void delay() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
