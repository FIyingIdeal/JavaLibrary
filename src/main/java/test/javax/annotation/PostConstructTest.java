package test.javax.annotation;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by Administrator on 2017/1/11.
 *
 * @PostConstruct 与 @PreDestroy注解说明：用来修饰Servlet的生命周期的注解
 *
 * @PostConstruct 该注解修饰的方法在Servlet被加载的时候只运行一次（与init()方法类似），
 *                其运行的时机是：在构造方法之后，init方法之前运行
 * @PreDestroy    该注解修饰的方法在Servlet被卸载的时候只运行一次（与destroy()方法类似），
 *                其运行的时机是：在destroy方法之后，Servlet被完全卸载之前
 *
 * 这两个注解用来修饰非静态的void方法，且该方法不能抛出任何异常
 * 在此处不起作用，该注解是影响Servlet生命周期的注解,且要求Servlet 2.5+
 */
public class PostConstructTest /*extends HttpServlet*/ {

    public PostConstructTest() {
        System.out.println("Construct...");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct");
    }

    public void init() {
        System.out.println("init");
    }

    public void service() {

    }

    public void destroy() {
        System.out.println("destroy");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("@PreDestroy");
    }

    public static void main(String [] args) {
        PostConstructTest postConstruct = new PostConstructTest();
        postConstruct.service();
    }

}