package JVMBook.chapter7.clinit;

/**
 * @author yanchao
 * @date 2017/7/17.
 * @see <a href="http://www.cnblogs.com/javaee6/p/3714716.html"/>
 */
public class StaticFieldInit {

    private static StaticFieldInit staticFieldInit = new StaticFieldInit();
    public static int count1;
    public static int count2 = 0;
    //如果将staticFieldInit定义放在count1和count2定义之后，那么执行结果是count1和count2都为1
    //private static StaticFieldInit staticFieldInit = new StaticFieldInit();

    private StaticFieldInit() {
        count1++;
        count2++;
    }

    public static StaticFieldInit getInstance() {
        return staticFieldInit;
    }


    /**
     * 1:StaticFieldInit staticFieldInit1 = StaticFieldInit.getInstance();调用了类的StaticFieldInit调用了类的静态方法，触发类的初始化
     * 2:类加载的时候在准备过程中为类的静态变量分配内存并初始化默认值 StaticFieldInit=null count1=0,count2=0
     * 3:类初始化时，为类的静态变量赋值和执行静态代码块。StaticFieldInit赋值为new StaticFieldInit()调用类的构造方法
     * 4:调用类的构造方法后count=1;count2=1
     * 5:继续为count1与count2赋值,此时count1没有赋值操作,所有count1为1,但是count2执行赋值操作就变为0
     * @param args
     */
    public static void main(String[] args) {
        StaticFieldInit staticFieldInit1 = StaticFieldInit.getInstance();
        System.out.println(staticFieldInit1.count1);    //1
        System.out.println(staticFieldInit1.count2);    //0
    }
}
