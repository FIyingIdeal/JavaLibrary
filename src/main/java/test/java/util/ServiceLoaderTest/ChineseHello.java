package test.java.util.ServiceLoaderTest;

/**
 * @author yanchao
 * @date 2018/1/29 16:33
 */
public class ChineseHello implements Hello {

    @Override
    public String sayHello(String message) {
        return "你好，" + message;
    }
}
