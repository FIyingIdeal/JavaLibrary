package ThinkingInJava.Chapter10InnerClass.AnonymousInnerClass;

/**
 * 匿名内部类
 */
public class AnonymousInnerClass {

    public Wrapping wrapping(int i) {
        return new Wrapping(i) {
            @Override
            public int value() {
                return super.value() * 47 + 1;
            }
        };
    }

    public static void main(String[] args) {
        AnonymousInnerClass anonymousInstance = new AnonymousInnerClass();
        System.out.println(anonymousInstance.wrapping(10).value());
    }
}

class Wrapping {
    private int i;
    public Wrapping(String s) {}
    public Wrapping(int i) {
        this.i = i;
    }
    public int value() {
        return i;
    }
}
