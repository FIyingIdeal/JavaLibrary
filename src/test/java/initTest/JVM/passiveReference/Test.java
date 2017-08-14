package test.java.initTest.JVM.passiveReference;

/**
 * Created by Administrator on 2017/7/17.
 */

/**
 * 类加载 -- 被动引用
 */
public class Test {

    public static void main(String[] args) {
        //子类调用父类的静态变量，子类不会被初始化。只用父类被初始化（加载）
        //对于静态字段，只有直接定义这个字段的类才会被初始化（加载）
        System.out.println(SubClass.num);

        //通过数组定义来引用类，不会触发类的初始化（加载）
        SubClass[] subClassArray = new SubClass[9];

        //访问类的常量（static final），不会初始化类
        System.out.println(ConstClass.num);         //静态代码块不会被执行

        System.out.println(SuperClass.class.isAssignableFrom(SubClass.class));   //true  父类在前，子类在后
        System.out.println(SubClass.class.isAssignableFrom(SuperClass.class));   //false
        System.out.println(SuperClass.class.isAssignableFrom(SuperClass.class)); //true
        System.out.println(Integer.class.isAssignableFrom(int.class));           //false
    }
}
