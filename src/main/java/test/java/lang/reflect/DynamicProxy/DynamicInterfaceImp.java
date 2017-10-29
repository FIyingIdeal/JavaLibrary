package test.java.lang.reflect.DynamicProxy;

/**
 * Created by Administrator on 2017/7/27.
 */
public class DynamicInterfaceImp implements DynamicInterface {

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
