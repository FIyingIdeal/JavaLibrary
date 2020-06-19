package test.java.util.concurrent.completablefutuer;

/**
 * 解析 {@link Shop#getPriceWithDiscount(String)} 返回值
 * @author yanchao
 * @date 2020-06-14 10:33
 */
public class Quote {

    private final String shopName;
    private final double price;
    private final Discount.Code discount;

    public Quote(String shopName, double price, Discount.Code discount) {
        this.shopName = shopName;
        this.price = price;
        this.discount = discount;
    }

    public static Quote parse(String discountPriceStr) {
        String[] split = discountPriceStr.split(":");
        String shopName = split[0];
        double price = Double.parseDouble(split[1]);
        Discount.Code discount = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discount);
    }

    public String getShopName() {
        return shopName;
    }

    public double getPrice() {
        return price;
    }

    public Discount.Code getDiscount() {
        return discount;
    }
}
