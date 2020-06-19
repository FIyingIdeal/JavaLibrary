package test.java.util.concurrent.completablefutuer;

/**
 * @author yanchao
 * @date 2020-06-17 23:02
 */
public class ExchangeService {

    public enum Money {
        /**
         * 人民币
         */
        RMB(100D),
        EUR(10D),
        USD(14.5D);

        private Double rate;

        Money(Double rate) {
            this.rate = rate;
        }

        public Double getRate() {
            return rate;
        }

    }

    public double getRate(Money m1, Money m2) {
        return m1.getRate() / m2.getRate();
    }
}
