package test.java.lang.reflect.MethodTest;

import org.junit.Test;
import test.java.lang.Annotation.MethodParamaterAnnotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/6/1.
 * Method 测试
 */
public class MethodTest {

    class MyMethod {

        public void testMethod(@MethodParamaterAnnotation String param1, @MethodParamaterAnnotation String param2) {
            //do nothing
        }
    }

    /**
     * {@link Method#getParameterAnnotations()}方法用来获取该method的所有参数的注解，返回是一个二维数组，每一个参数的注解是一个维度
     */
    @Test
    public void getParameterAnnotations() {
        try {
            Method method = MyMethod.class.getMethod("testMethod", String.class, String.class);
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int i = 0 ; i < annotations.length; i++) {
                for (int j = 0; j < annotations[i].length; j++) {
                    System.out.print(annotations[i][j].annotationType().getSimpleName() + " ");
                }
                System.out.println();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}

