package test.java.lang.reflect.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/7/27.
 */
public class DynamicInterfaceProxy implements InvocationHandler {

    private static int num = 0;

    private Object proxied;

    public DynamicInterfaceProxy(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String result = null;
        //System.out.println(proxy);//proxy动态代理的对象,不是实际的被代理对象
        System.out.println("before proxy");
        result = (String) method.invoke(proxied, args);
        System.out.println("after proxy, proxy num is " + ++num);
        return result;
    }
}
