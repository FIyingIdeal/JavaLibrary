package test.java.lang.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author yanchao
 * @date 2018/3/22 10:32
 * @function Parameter测试，Parameter是Java8中新增的类
 */
public class ParameterTest {

    public void parameterTestMethod(String name) {

    }

    @Test
    public void test() {
        Class<User> clazz = User.class;
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Parameter[] parameters = constructor.getParameters();
            for (Parameter parameter : parameters) {
                System.out.print(parameter.getName() + "  ");
            }
            System.out.println();
        }
    }

    /**
     * 判断是否保留参数名。默认是不保留的，如果编译时使用了-parameters，则会保留
     * IDEA中可以在Settings > Build, Execution, Deployment > Compiler > Java Compiler > Additional command line parameters中设置该参数
     */
    @Test
    public void isNamePresent() {
        try {
            Method method = ParameterTest.class.getMethod("parameterTestMethod", String.class);
            Parameter[] parameters = method.getParameters();
            for (Parameter parameter : parameters) {
                System.out.println(parameter.isNamePresent());
                System.out.println(parameter.getName());
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
