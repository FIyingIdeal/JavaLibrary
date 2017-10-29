package test.java.lang.reflect.DynamicProxy;

import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/7/27.
 */
public class Main {

    public static void main(String[] args) {
        DynamicInterfaceImp imp = new DynamicInterfaceImp();

        DynamicInterface di = (DynamicInterface) Proxy.newProxyInstance(
                DynamicInterface.class.getClassLoader(),
                new Class[] { DynamicInterface.class },
                new DynamicInterfaceProxy(imp)
        );

        String proxyResult = di.sayHello("zhangsan");
        for (int i = 0; i < 10; i++) {
            di.sayHello(String.valueOf(i));
        }
        System.out.println("proxy result : " + proxyResult);
    }
}
