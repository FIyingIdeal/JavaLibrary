package test.java.InnerClassTest;

/**
 * Created by Administrator on 2016/7/20.
 */
public class OutterClass {

    private int i = 100;
    private int a = 20;
    private static int b = 30;

    static {
        System.out.println("OutterClass static block");
    }

    {
        System.out.println("OutterClass dynamic block");
    }

    public class InnerClass {
        private int i = 200;

        public void print() {
            int i = 300;
            System.out.println(i);                  //300
            System.out.println(this.i);             //200
            System.out.println(OutterClass.this.i); //100
            System.out.println(a);                  //20
        }
    }

    public void localInnerClass() {
        int y = 10;
        class LocalInnerClass {
            int i = 500;

            public void print() {
                System.out.println(i);                  //500
                System.out.println(this.i);             //500
                System.out.println(OutterClass.this.i); //100
                System.out.println(y);                  //10
            }
        }
        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.print();
    }

    //静态内部类只有在用到的时候才会加载
    static class StaticInnerClass {
        int param = 9;

        static {
            System.out.println("load StaticInnerClass");
            //System.out.println(a);          //error, 静态内部类中只能访问外部类的静态成员或方法
            System.out.println(b);          //30
        }

        //只有在静态内部类中可以定义静态方法或静态成员变量
        public static void staticMethod() {
            System.out.println("in static inner class static method b = " + b);
            //System.out.println(a);
        }

        public void print() {
            System.out.println(param);
        }
    }

    public static void main(String[] args) {
        /*OutterClass outterClass = new OutterClass();
        //非静态内部类的实例要绑定到外部类的实例上
        InnerClass innerClass = outterClass.new InnerClass();
        innerClass.print();
        outterClass.localInnerClass();*/
        //静态内部类的实例无需绑定到外部列的实例上
        StaticInnerClass staticInnerClass = new StaticInnerClass();

    }
}
