package test.java.InnerClassTest;

/**
 * Created by Administrator on 2016/7/20.
 */
public class OutterClass {

    private int i = 100;

    public class InnerClass {
        private int i = 200;

        public void print() {
            int i = 300;
            System.out.println(i);
            System.out.println(this.i);
            System.out.println(OutterClass.this.i);
        }
    }

    public void localInnerClass() {
        int y = 10;
        class LocalInnerClass {
            int i = 500;

            public void print() {
                System.out.println(i);
                System.out.println(this.i);
                System.out.println(OutterClass.this.i);
                System.out.println(y);
            }
        }
        LocalInnerClass localInnerClass = new LocalInnerClass();
        localInnerClass.print();
    }

    static class StaticInnerClass {
        int param = 9;

        public void print() {
            System.out.println(param);
        }
    }

    public static void main(String[] args) {
        OutterClass outterClass = new OutterClass();
        outterClass.new InnerClass().print();
        outterClass.localInnerClass();
        StaticInnerClass staticInnerClass = new StaticInnerClass();
    }
}
