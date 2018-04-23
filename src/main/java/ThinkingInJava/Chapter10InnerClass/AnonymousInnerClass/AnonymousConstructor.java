package ThinkingInJava.Chapter10InnerClass.AnonymousInnerClass;

public class AnonymousConstructor {

    public static Base getBase(int i) {
        return new Base(i) {
            {
                System.out.println("inside instance initializer");
            }
            @Override
            public void f() {
                System.out.println("in anonymous f()");
            }
        };
    }

    public static void main(String[] args) {
        Base base = AnonymousConstructor.getBase(9);
        base.f();
    }

}

abstract class Base {
    public Base(int i) {
        System.out.println("Base Constructor, i = " + i);
    }

    public abstract void f();
}
