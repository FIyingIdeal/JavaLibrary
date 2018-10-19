package NewFeatures.trywithresource;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanchao
 * @version 1.0 2017/2/17.
 *
 *  try-with-resoure是java7开始支持的新的异常处理机制
 *  它能够很容易的关闭在try-catch语句块中使用的资源
 */
public class TryWithResourceTest {

    /**
     * 使用传统的try-catch方式来处理异常，必须在finally中手动释放流，
     * 并且如果第一个try语句块抛出异常，finally语句块中的try也抛出异常，
     * 那么finally语句块中抛出的异常会根据调用栈向外传播，即使第一个try
     * 语句块中抛出的异常与异常传播更相关
     */
    @Test
    public void tryCatch() {
        InputStream is = null;
        try {
            is = new FileInputStream("src/NewFeatures/streamtest/Tool.java");
            //int data = fis.read();
            for (int data; (data = is.read()) != -1; ) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * java7开始支持的方式是直接在try后的括号中添加FileInputStream的声明，
     * 当try语句块运行结束时，FileInputStream会被自动关闭，无需使用finally。
     * 这是因为FileInputStream实现了java.lang.AutoCloseable接口，所有实现了
     * 这个接口的类都可以在try-with-resource结构中使用
     */
    @Test
    public void tryWithResource() {
        try (FileInputStream fis = new FileInputStream("src/NewFeatures/streamtest/Tool.java")) {
            for (int data; (data = fis.read()) != -1; ) {
                System.out.print((char) data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}