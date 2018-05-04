package test.java.lang.ThreadTest;

/**
 * @author yanchao
 * @date 2018/5/1 12:51
 * UncaughtExceptionHandler用于捕获在线程run()方法中抛出的未被捕获的异常
 * 其设置方式有两种：
 *      1.通过Thread.setDefaultUncaughtExceptionHandler()来设置，这种方式只针对所有代码都使用相同的异常处理器，
 *        否则应该定义不同的异常处理为线程设置对应的异常处理器；
 *      2.通过{@link test.java.util.concurrent.threadfactory.MyThreadFactory}来设置；
 */
public class UncaughtExceptionHandlerTest {

    public static void main(String[] args) {

        try {
            new Thread(() -> {
                throw new RuntimeException("如果不设置UncaughtException的话，run()方法中抛出的未处理异常将无法在run()方法外被捕获到");
            }).start();
        } catch (RuntimeException e) {
            // 即使线程的run()方法中抛出了异常，但在线程启动的地方添加cache也无法捕获到该异常
            System.out.println("In catch Block : " + e.getMessage());
        } finally {
            System.out.println("In finally block!");
        }


        // 如果程序中使用同样的异常处理器的话，可以直接为Thread设置一个DefaultUncaughtExceptionHandler来对所有的线程未捕获异常进行处理
        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            System.out.println("In DefaultUncaughtExceptionHandler => "
                    + t.getName() + " : " + e.getClass() + " : " + e.getMessage());
        });

        new Thread(() -> {
            try {
                throw new NullPointerException("在run()方法中捕获异常后，UncaughtExceptionHandler()中无法再次捕获到该异常");
            } catch (NullPointerException e) {
                // 如果run()方法中自动捕获了这个异常，那么UncaughtExceptionHandler就无法捕获到该异常了（正如其名称一样，它只处理未被捕获的异常）
                System.out.println("In run() catch : " + e.getMessage());
            }
            throw new RuntimeException("在run()方法中抛出的未捕获RuntimeException");
        }).start();
    }
}
