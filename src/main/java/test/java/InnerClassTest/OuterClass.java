package test.java.InnerClassTest;

/**
 * Created by Administrator on 2016/7/20.
 */
public class OuterClass {

    private int i = 100;
    private int a = 20;
    private int y = 9;
    private static int b = 30;

    static {
        System.out.println("OuterClass static block");
    }

    {
        System.out.println("OuterClass dynamic block");
    }

    public class InnerClass {
        private int i = 200;
        // ERROR : 内部类不能有static变量
        // private static int ie = 0;

        // ERROR : 内部类不能有static代码块
        // static {}

        {
            System.out.println("InnerClass dynamic block");
        }

        private int getI() {
            return this.i;
        }

        public void print() {
            int i = 300;
            System.out.println(i);                  //300
            System.out.println(this.i);             //200
            System.out.println(OuterClass.this.i);  //100
            System.out.println(a);                  //20
            System.out.println(b);                  //30
        }
    }

    public InnerClass getInnerClassInstance() {
        return new InnerClass();
    }

    public int getInnerClassPrivateField() {
        return getInnerClassInstance().getI();
    }

    public void localInnerClass() {
        int y = 10;
        class LocalInnerClass {
            int i = 500;

            public void print() {
                System.out.println(i);                  //500
                System.out.println(this.i);             //500
                System.out.println(OuterClass.this.i);  //100
                System.out.println(y);                  //10
            }
        }
        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.print();
    }

    // 静态内部类只有在用到的时候才会加载
    static class StaticInnerClass {
        int param = 9;

        static {
            System.out.println("load StaticInnerClass");
            //System.out.println(a);          //error, 静态内部类中只能访问外部类的静态成员或方法
            System.out.println(b);          //30
        }

        // 只有在静态内部类中可以定义静态方法或静态成员变量
        public static void staticMethod() {
            System.out.println("in static inner class static method b = " + b);
            //System.out.println(a);
        }

        public void print() {
            System.out.println(param);
        }
    }

    public static void main(String[] args) {
        OuterClass outerClass = new OuterClass();
        // 非静态内部类的实例要绑定到外部类的实例上
        InnerClass innerClass = outerClass.new InnerClass();
        innerClass.print();
        // 与上边innerClass构造是等价的
        OuterClass.InnerClass innerClass1 = outerClass.getInnerClassInstance();
        // 外部类可以访问到内部类的private方法
        System.out.println("InnerClass private method : innerClass1.getI() = " + innerClass1.getI());
        System.out.println("InnerClass private method : outerClass.getInnerClassPrivateField() = "
                + outerClass.getInnerClassPrivateField());
        outerClass.localInnerClass();
        // 静态内部类的实例无需绑定到外部列的实例上
        StaticInnerClass staticInnerClass = new StaticInnerClass();

    }
}
