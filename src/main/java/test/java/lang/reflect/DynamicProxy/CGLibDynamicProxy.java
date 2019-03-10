package test.java.lang.reflect.DynamicProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.junit.Test;
import utils.PrintUtil;

import java.lang.reflect.Method;

/**
 * @author yanchao
 * @date 2018/11/19 16:29
 * CGLib 动态代理，基于类继承。因此无法对 final 类进行代理，也无法对 private、final 方法进行代理
 * 对于从 Object 中继承的方法也会代理，但getClass()、wait()等方法是 final 的，所以不会代理
 */
public class CGLibDynamicProxy {

    /**
     * 这个内部类必须声明为static，否则动态代理报错！！！
     * 因为CGLib动态代理不能对普通内部类进行代理，普通内部类依赖于外部类对象，无法通过反射直接访问到其无参构造方法
     */
    static class Hello {
        public void sayHello(String message) {
            PrintUtil.println(message);
        }
    }

    /**
     * MethodInterceptor 类似于 JDK 动态代理的 InvocationHandler
     */
    static class MyMethodInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] objects,
                                MethodProxy methodProxy) throws Throwable {
            PrintUtil.println("MyMethodInterceptor # intercept");
            return methodProxy.invokeSuper(o, objects);
        }
    }

    @Test
    public void cglibDynamicTest() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Hello.class);
        enhancer.setCallback(new MyMethodInterceptor());

        Hello hello = (Hello) enhancer.create();
        hello.sayHello("CGLib Dynamic");
    }

    // CGLib动态动态代理生成的代理类大体长相如下：
    /*public class Hello$$EnhancerByCGLIB$$e3734e52
            extends Hello
            implements Factory {

        private MethodInterceptor CGLIB$CALLBACK_0;

        public final void sayHello(String paramString) {
            MethodInterceptor tmp17_14 = CGLIB$CALLBACK_0;
            if (tmp17_14 != null) {

                return (String) tmp17_14.intercept(
                        this,
                        CGLIB$sayHello$0$Method,
                        new Object[] {paramString},
                        CGLIB$sayHello$0$Proxy);
            }
            return super.sayHello(paramString);
        }
        // ...
    }*/
}
