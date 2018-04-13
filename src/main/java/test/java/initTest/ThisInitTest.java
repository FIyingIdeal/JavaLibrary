package test.java.initTest;

/**
 * @author yanchao
 * @date 2018/4/12 11:36
 * @function java 成员变量中可以直接使用this的原因
 * 参考： https://www.zhihu.com/question/53564749
 *       《深入理解Java虚拟机》第二章 2.3.1 对象的创建
 */
public class ThisInitTest {

    // Test test = new Test(this);

    {
        System.out.println("this = " + this);
        System.out.println("this.i = " + this.i);
        System.out.println("this.test = " + this.test);
    }

    // test 定义的位置与this.test输出相关，说明<init>收集的顺序与代码顺序有关
    Test test = new Test(this);
    private int i = 2;

    public ThisInitTest() {
        System.out.println("ThisInitTest构造方法");
    }

    public static void main(String[] args) {
        // new指令参数 -> 常量池符号引用 -> [类加载，解析，初始化] -> 新生对象分内存（堆） -> 内存初始化零值（除对象头）-> 对象头设置 （到这一步，从VM来看，对象已经产生）
        //      -> <init>方法执行   （从程序角度来说，执行<init>才是对象创建的开始）
        ThisInitTest initTest = new ThisInitTest();
        System.out.println(initTest);
    }
}

class Test {

    public Test(ThisInitTest thisInitTest) {
        System.out.println("Test :" + thisInitTest);
    }
}
