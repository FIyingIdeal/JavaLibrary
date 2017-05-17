package test.java.lang.reflect;

import org.junit.Test;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/3/3.
 */
public class UserReflectTest {

    public void println(String outString) {
        System.out.println(outString);
    }

    public void print(String outString) {
        System.out.print(outString + ' ');
    }

    //获取包名和类名
    @Test
    public void getPackageAndClassName() {
        User user = new User();
        println(user.getClass().getPackage().getName()); //java.lang.reflect
        println(user.getClass().getName());              //test.java.lang.reflect.User
        println(user.getClass().getSimpleName());        //User
    }

    //实例化Class类对象
    @Test
    public void getClassObject() {
        Class<?> clazz1 = null;
        Class<?> clazz2 = null;
        Class<?> clazz3 = null;

        try {
            clazz1 = Class.forName("User");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clazz2 = new User().getClass();
        clazz3 = User.class;
        println(clazz1.getName());  //User
        println(clazz2.getName());
        println(clazz3.getName());
    }

    //获取一个对象的父类与实现的接口
    @Test
    public void getFuthersAndInterfaces() {
        Class<?> superClazz = User.class.getSuperclass();
        println("User的父类为：" + superClazz);

        Class<?>[] allInterfaces = User.class.getInterfaces();
        print("User类实现的所有接口：");
        for (Class<?> interf : allInterfaces) {
            print(interf.getName());
        }
    }

    //实例化一个类的对象
    @Test
    public void instanceObject() {
        Class<?> clazz = User.class;
        //方法一：newInstance()方法，通过默认构造方法进行实例化,所以必须要有默认构造方法
        User user = null;
        try {
            user = (User)clazz.newInstance();
            println(user.toString());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //方法二：通过构造方法进行实例化
        Constructor<?>[] cons = clazz.getConstructors();
        for (Constructor<?> con : cons) {
            print(clazz.getSimpleName() + "(");
            Class<?>[] paramClasses = con.getParameterTypes();
            List<Class<?>> paramList = Arrays.asList(paramClasses);
            println(String.join(",", paramList.stream().map(Class::getSimpleName)
                    .collect(Collectors.toList())) + ")");
        }

        Constructor<?> cons0 = null;
        User user1 = null;
        try {
            cons0 = clazz.getConstructor(String.class, int.class, char.class);
            user1 = (User)cons0.newInstance("name", 23, 'm');
            println(user1.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //获取属性及属性的相关信息
    @Test
    public void getFields() {
        Class<?> clazz = User.class;
        //获取所有申明（private  protected  public）的属性
        //可以获取到本身类或实现接口中的Fields，但获取不到父类中的Fields(包括父类的public方法 也获取不到)
        Field[] fields = clazz.getDeclaredFields();
        //获取public属性,是一个类的话返回本身和父类中的public属性
        Field[] fields1 = clazz.getFields();
        for (Field field : fields) {
            //获取权限修饰符
            int modifier = field.getModifiers();
            String modifierName = Modifier.toString(modifier);
            println(field.getName() + "属性的权限修饰符：" + modifier + "  " + modifierName);

            //获取属性类型
            Class<?> type = field.getType();
            println(field.getName() + "属性的类型：" + type.getSimpleName());
        }

        println("------------------实现的接口/继承的类中的属性");
        fields = clazz.getFields();
        for (Field field : fields) {
            //获取权限修饰符
            int modifier = field.getModifiers();
            String modifierName = Modifier.toString(modifier);
            println(field.getName() + "属性的权限修饰符：" + modifier + "  " + modifierName);

            //获取属性类型
            Class<?> type = field.getType();
            println(field.getName() + "属性的类型：" + type.getSimpleName());
        }
    }

    //获取method及method的相关信息
    @Test
    public void getMethods() {
        Class<?> clazz = User.class;
        //获取所有（自身+接口+父类）的public方法
        //Method[] methods = clazz.getMethods();
        //获取自身+接口中的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            print(Modifier.toString(method.getModifiers()) + " "
                    + method.getReturnType().getSimpleName() + " "
                    + method.getName() + "(");
            Class<?>[] params = method.getParameterTypes();
            if (params.length > 0) {
                List<Class<?>> paramList = Arrays.asList(params);
                print(String.join(",", paramList.stream().map(Class::getSimpleName)
                        .collect(Collectors.toList())));
            }

            Class<?>[] exceptions = method.getExceptionTypes();
            if(exceptions.length > 0) {
                List<Class<?>> exceptionList = Arrays.asList(exceptions);
                print("throws " + String.join(",", exceptionList.stream().map(Class::getSimpleName)
                        .collect(Collectors.toList())));
            }
            println(")");
        }
    }

    //调用方法
    @Test
    public void invokeMethod() {
        Class clazz = User.class;
        try {
            User user = (User) clazz.newInstance();
            Method method = clazz.getMethod("setUserName", String.class);
            method.invoke(user, "yanchao");
            method = clazz.getMethod("getUserName");
            println(method.invoke(user).toString());

            println("---------获取私有方法");
            Method privateMethod = clazz.getDeclaredMethod("privatemethod");
            //调用私有方法的话必须要设置setAccessible(true)
            privateMethod.setAccessible(true);
            privateMethod.invoke(user);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //设置属性的值
    @Test
    public void setAndGetFieldValue() {
        Class clazz = User.class;
        Field field = null;
        try {
            User user = (User)clazz.newInstance();
            field = clazz.getDeclaredField("userName");
            field.setAccessible(true);
            //field的set与get方法不依赖于该field的setter/getter方法
            field.set(user, "yan");
            println(field.get(user).toString());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //给泛型为Integer的ArrayList中存放一个String类型的对象
    @Test
    public void setStringToInteger() {
        List<Integer> list = new ArrayList<>();
        try {
            Method method = list.getClass().getMethod("add", Object.class);
            method.invoke(list, "String");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println(list.get(0));
    }

    //修改数组中的元素， 此处使用的Array是java.lang.reflect包中的
    @Test
    public void updateArrayInfo() {
        int[] temp = {1,2,3,4,5};
        Class<?> clazz = temp.getClass().getComponentType();
        System.out.println("数组类型：" + clazz.getName());          //int
        System.out.println("数组长度：" + Array.getLength(temp));    //5
        System.out.println("数组第一个元素：" + Array.get(temp, 0)); //1
        Array.set(temp, 0, 100);
        System.out.println("修改后数组第一个元素：" + Array.get(temp, 0));//100
    }

    //修改数组长度
    @Test
    public void updateArrayLength() {
        int[] temp = {1,2,3,4,5,6,7,8,9};
        Class<?> clazz = temp.getClass().getComponentType();

        //通过反射创建一个数组
        int[] newArray = (int[])Array.newInstance(clazz, 15);
        int oldLength = temp.length;
        //实现数组复制
        System.arraycopy(temp, 0, newArray, 0, oldLength);

        //数组打印
        for (int i = 0; i < Array.getLength(newArray); i++) {
            print(Array.get(newArray, i).toString());
        }
    }
}
