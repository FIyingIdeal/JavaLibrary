package othertest.polymorphism;

/**
 * Created by Administrator on 2017/8/3.
 */
public abstract class AbstractParent implements ParentInterface {

    public static ParentInterface getInstance(int i) {
        if (i == 0) {
            return new Child1();
        } else if (i == 1) {
            return new Child2();
        } else
            return new DefaultChild();
    }
}
