package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/4/29 11:26
 */
public class Child extends Parent {

    static {
        System.out.println("Child static block");
    }
}
