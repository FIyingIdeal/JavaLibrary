package othertest.polymorphism;

/**
 * Created by Administrator on 2017/8/3.
 */
public class DefaultChild extends AbstractParent {

    @Override
    public void doSomething(String message) {
        System.out.println("hi, " + message);
    }
}
