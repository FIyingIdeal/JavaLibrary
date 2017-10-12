package JVMBook.chapter3;

/**
 * 1.对象可以在被GC时自我拯救；
 * 2.这种自救的机会只有一次，因为一个对象的finalize()方法最多只会被系统自动调用一次
 *   finalize()方法只有在GC回收该对象的内存的时候才会被调用
 * @author yanchao
 * @date 2017/9/26 17:58
 */
public class FinalizeEscapeeGC {

    private static FinalizeEscapeeGC SAVE_HOCK;

    private static void isAlive() {
        if (SAVE_HOCK != null) {
            System.out.println("I'm still alive");
        } else {
            System.out.println("I've dead");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method invoke");
        SAVE_HOCK = this;
    }

    public static void main(String[] args) throws Exception {
        SAVE_HOCK = new FinalizeEscapeeGC();

        //对象的第一次成功自救
        SAVE_HOCK = null;
        System.gc();
        //因为finalize()方法优先级很低，所以暂停0.5s等待它
        Thread.sleep(500);
        isAlive();

        //对象第二次自救失败，因为finalize()方法最多只会被系统自动调用一次
        SAVE_HOCK = null;
        System.gc();
        Thread.sleep(500);
        isAlive();
    }
}

/**
 * 输出结果：
 *      finalize method invoke
 *      I'm still alive
 *      I've dead
 */
