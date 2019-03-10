package test.java.initTest;

/**
 * @author yanchao
 * @date 2018/11/9 11:58
 * 利用 类初始化 方式实现单例模式
 */
public class Singleton {

    private Singleton() {}

    public static Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Singleton INSTANCE = new Singleton();
    }
}
