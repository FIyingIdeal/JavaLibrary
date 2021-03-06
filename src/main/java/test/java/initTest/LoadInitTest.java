package test.java.initTest;

/**
 * Created by Administrator on 2016/7/18.
 * 实例化InitTest的实例，测试初始化顺序
 */
public class LoadInitTest {

    //private InitTest unInstanceInitTest;
    // 将InitTest直接定义为LoadInitTest的一个非static属性并实例化:在只进行实例化而未被使用的情况下，不会调用InitTest的动态代码块
    // 如果是被使用的话，它会先于LoadInitTest的实例先实例化，因为它是实例的属性，而非类的属性！每初始化一个LoadInitTest实例就会实例化一次InitTest
    // 2018-3-28 23:20:52 对于实例变量initTest，在new LoadInitTest()的时候，会先调用InitTest的构造方法，然后调用LoadInitTest的动态代码块，最后调用LoadInitTest的构造方法
    private InitTest initTest = new InitTest("Field");

    public LoadInitTest() {
        System.out.println("LoadInitTest Constructor");
    }

    public InitTest getInitTest() {
        return this.initTest;
    }

    //static的属性，属于类的属性，所以在加载类的时候就会先得到执行，而static属性/代码块的执行顺序取决于其定义的先后
    private static InitTest staticInitTest = new InitTest("static/class Field");

    private static int a = 99;

    static {
        //定义在静态代码块之前的类变量的赋值先于静态代码块的执行
        //因为 <clinit> 方法使用编译器自动收集类中的所有类变量的赋值动作和静态语句块中的语句合并而成，编译器收集的顺序是由语句在源文件中出现的顺序决定的
        System.out.println("a=" + a);
        System.out.println("LoadInitTest static block");
    }

    {
        System.out.println("LoadInitTest dynamic block");
    }

    public static void staticMethod() {
        System.out.println("LoadInitTest static Method");
    }

    public static void main(String[] args) {
        System.out.println("In LoadInitTest main");
        InitTest test = new InitTest("LOCAL_VARIABLE");     //会加载InitTest类，所以InitTest类的动态代码块也会执行
        LoadInitTest loadInitTest = new LoadInitTest();
        LoadInitTest loadInitTest1 = new LoadInitTest();
        loadInitTest.getInitTest().method();
    }
}
