package ThinkingInJava.Chapter10InnerClass.AnonymousInnerClass;

import ThinkingInJava.Chapter10InnerClass.Destination;

/**
 * P199 说必须将参数声明成final的，但Java8实际测试不用也没有报错...
 */
public class Parcel10 {

    public Destination destination(final String dest, final float price) {
        return new Destination() {
            private int cost;
            {
                cost = Math.round(price);
                if (cost > 100) {
                    System.out.println("Over budget");
                }
            }
            private String label = dest;
            @Override
            public String readLabel() {
                return label;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 p = new Parcel10();
        Destination d = p.destination("ttt", 101.34f);
    }
}
