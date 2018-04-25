package ThinkingInJava.Chapter10InnerClass;

import java.util.HashMap;
import java.util.Map;

/**
 * 局部内部类：在方法的作用于内创建的类
 */
public class LocalInnerClass {

    public Map getMyMap() {
        class MyMap extends HashMap {
            // other code
        }
        return new MyMap();
    }
    // Error: MyMap不在作用域内
    // MyMap myMap = new MyMap();
}
