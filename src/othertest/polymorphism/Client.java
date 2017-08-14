package othertest.polymorphism;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 */
public class Client {

    public static void main(String[] args) {
        ParentInterface parentInterface = AbstractParent.getInstance(2);
        parentInterface.doSomething("Child2");
        System.out.println(parentInterface.getClass().getName());

        List list = new ArrayList<>();
        System.out.println(list.isEmpty());
    }
}
