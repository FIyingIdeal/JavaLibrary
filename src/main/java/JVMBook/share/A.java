package JVMBook.share;

/**
 * @author yanchao
 * @date 2018/5/20 23:11
 */
public class A {

    private B b;

    {
        System.out.println("in dynamic block, this = " + this);
        System.out.println("in dynamic block, this.b = " + this.b);
    }

    public A() {
        this.b = new B(this);
        System.out.println("in constructor, this = " + this);
        System.out.println("in constructor, this.b = " + this.b);
    }

    public String getName() {
        return A.class.getName();
    }

    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.b.getName());
    }
}




class B {

    private A a;

    public B(A a) {
        this.a = a;
    }

    public String getName() {
        return B.class.getName();
    }

}
