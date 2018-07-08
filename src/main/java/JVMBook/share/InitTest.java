package JVMBook.share;

/**
 * @author yanchao
 * @date 2018/5/31 11:39
 */
public class InitTest {

    /**
     * 构造方法会在对象实例变量初始化和动态代码块之后执行
     */
    public InitTest() {
        System.out.println("InitTest constructor");
    }

    /**
     * 动态代码块和对象实例变量初始化执行是按照定义顺序执行的
     */
    // private C c = new C();

    {
        System.out.println("InitTest dynamic block");
    }

    private C c1 = new C();

    public static void main(String[] args) {
        InitTest test = new InitTest();
    }
}
