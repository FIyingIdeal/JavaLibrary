package test.java.lang;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author yanchao
 * @date 2018/4/13 14:32
 */
public class ClassTest {

    @Test
    public void getName() {
        Class<TestInterface> clazz = TestInterface.class;
        System.out.println(clazz.getName());      // test.java.lang.TestInterface
    }

    @Test
    public void toStringTest() {
        Class<TestInterface> clazz = TestInterface.class;
        System.out.println(clazz.toString());   // interface test.java.lang.TestInterface
    }

    @Test
    public void isAssignableFrom() {
        System.out.println(ClassTest.class.isAssignableFrom(Object.class));     // false
        System.out.println(Object.class.isAssignableFrom(ClassTest.class));     // true
        System.out.println(Object.class.isAssignableFrom(TestInterface.class)); // true
    }

    @Test
    public void getParameterTypes() throws Exception {
        Class<?>[] parameterTypes = String.class.getMethod("substring", int.class, int.class).getParameterTypes();
        Arrays.stream(parameterTypes).forEach(parameterType -> System.out.println(parameterType));
    }

    @Test
    public void getParameterCount() throws Exception {
        int parameterCount = String.class.getMethod("substring", int.class, int.class).getParameterCount();
        System.out.println(parameterCount);
    }

    /**
     * 获取泛型类型：
     *      位于声明一侧(泛型类型（泛型类与泛型接口）声明、带有泛型参数的方法和域的声明)的，源码里写了什么到运行时就能看到什么；
     *      位于使用一侧(局部变量的声明)的，源码里写什么到运行时都没了。
     *
     * @see <a href="http://jisonami.iteye.com/blog/2282650">Java反射获取实际泛型类型参数</a>
     * @see <a href="http://rednaxelafx.iteye.com/blog/586212">答复: Java获得泛型类型</a>
     * @see <a href="http://josh-persistence.iteye.com/blog/2165613">Java获取泛型参数的类型的方法 - 实例讲解</a>
     */
    @Test
    public void getTypeParameters() {
        Class<?>[] parameterClasses = null;
        Type[] genericParameterTypes = null;
        Parameter[] parameters = null;
        try {
            Method method = ClassTest.class.getMethod("genericType", List.class);
            parameterClasses = method.getParameterTypes();
            genericParameterTypes = method.getGenericParameterTypes();
            parameters = method.getParameters();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Class<?> parameterType = parameterClasses[0];
        Arrays.stream(parameterType.getTypeParameters()).forEach(typeParameter -> {
            System.out.println(typeParameter instanceof ParameterizedType);     // false
            System.out.println(typeParameter);                                  // E
        });

        // 通过 getGenericParameterTypes() 获取到方法参数中的泛型类型
        Arrays.stream(genericParameterTypes).forEach(genericParameterType -> {
            System.out.println(genericParameterType instanceof ParameterizedType);     // true
            System.out.println(genericParameterType);                                  // java.util.List<java.lang.String>
            Type type = ((ParameterizedType) genericParameterType).getActualTypeArguments()[0];  // class java.lang.String
            try {
                System.out.println("1111111" + CharSequence.class.isAssignableFrom(getClass().getClassLoader().loadClass(type.getTypeName())));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Parameter parameter = parameters[0];
        System.out.println(parameter.getParameterizedType() instanceof ParameterizedType);  // true

        class MyList extends ArrayList<String> { }

        Type type = MyList.class.getGenericSuperclass();
        System.out.println("MyList.class.getGenericSuperclass() is " + type);
        System.out.println("MyList.class.getGenericSuperclass() instance of ParameterizedType is "
                + (type instanceof ParameterizedType));
        ParameterizedType parameterizedType = (ParameterizedType) type;
        System.out.print("ParameterizedType#getActualTypeArguments() is " );
        Arrays.stream(parameterizedType.getActualTypeArguments()).forEach(a -> System.out.println(a));
        System.out.println("ParameterizedType#getOwnerType() is " + parameterizedType.getOwnerType());
        System.out.println("ParameterizedType#getRawType() is " + parameterizedType.getRawType());

        class MyMap extends HashMap<String, String> { }

        Type mapType = MyMap.class.getGenericSuperclass();
        System.out.println("MyMap.class.getGenericSuperclass() is " + mapType);
        System.out.println("MyMap.class.getGenericSuperclass() instance of ParameterizedType is "
                + (mapType instanceof ParameterizedType));
        ParameterizedType mapParameterizedType = (ParameterizedType) mapType;
        System.out.print("ParameterizedType#getActualTypeArguments() is " );
        Arrays.stream(mapParameterizedType.getActualTypeArguments()).forEach(a -> System.out.println(a));
        System.out.println("ParameterizedType#getOwnerType() is " + mapParameterizedType.getOwnerType());
        System.out.println("ParameterizedType#getRawType() is " + mapParameterizedType.getRawType());

    }

    public void genericType(List<String> strings) {

    }

}

interface TestInterface {

    String getName();
}
