package test.javax.annotation;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/1/11.
 *
 * PostConstruct注解在此处不起作用，该注解是影响Servlet生命周期的注解
 */
public class PostConstructTest {

    public PostConstructTest() {
        System.out.println("Construct...");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct");
    }

    public void print() {
        System.out.println("print()");
    }

    public static void main(String [] args) {
        PostConstructTest postConstruct = new PostConstructTest();
        postConstruct.print();

        SubClass subClass = new SubClass();
        subClass.print();
    }
}

class SubClass {

    public SubClass() {
        System.out.println("SubClass Construct...");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("subclass postConstruct...");
    }

    public void print() {
        System.out.println("subclass print()");
    }
}
