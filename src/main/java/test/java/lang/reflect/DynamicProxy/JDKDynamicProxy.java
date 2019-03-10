package test.java.lang.reflect.DynamicProxy;

import org.junit.Test;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author yanchao
 * @date 2018/11/19 16:22
 * JDK 基于接口的动态代理
 */
public class JDKDynamicProxy {

    interface DynamicInterface {
        String sayHello(String name);
    }

    class DynamicInterfaceImp implements DynamicInterface {
        @Override
        public String sayHello(String name) {
            return "Hello, " + name;
        }
    }

    static class DynamicInterfaceProxy implements InvocationHandler {

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

    @Test
    public void main() {
        DynamicInterfaceImp imp = new DynamicInterfaceImp();

        /**
         * Proxy.newInstance(ClassLoader, Class[], InvocationHandler)构造代理对象的时候，
         * 其中会生成一个 extends Proxy implements XXXX(newInstance方法中第二个参数传递的接口类)
         * 的代理类。且这个代理类有一个以InvocationHandler为参数的构造方法，
         * 也就是将newInstance方法的第三个参数传递了进去。
         * 之后就ClassLoader来加载这个动态生成的类即可，加载后再方法区中就存在了一个Class对象，代表这个代理类。
         * 有了Class对象，可以通过Class.getConstructor(InvocationHandler)来构造代理对象了！
         *
         * 既然实现了指定的接口，那必须要实现接口中的方法，而方法中的逻辑则是直接将调用委托给了InvocationHandler#invoke()方法执行
         * 这样的话就执行了自定义的InvocationHandler#invoke逻辑！
         */
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
