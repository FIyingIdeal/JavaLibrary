package othertest.polymorphism;

/**
 * Created by Administrator on 2017/8/3.
 */
public class Child2 extends AbstractParent {

    @Override
    public void doSomething(String message) {
        System.out.println("hi, " + message);
    }
}
