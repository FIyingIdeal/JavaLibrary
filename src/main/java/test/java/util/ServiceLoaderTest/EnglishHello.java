package test.java.util.ServiceLoaderTest;

/**
 * @author yanchao
 * @date 2018/1/29 16:34
 */
public class EnglishHello implements Hello {

    @Override
    public String sayHello(String message) {
        return "Hello, " + message;
    }
}
