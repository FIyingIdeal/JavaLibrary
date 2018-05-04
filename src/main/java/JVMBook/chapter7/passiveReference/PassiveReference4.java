package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/4/29 11:25
 */
public class PassiveReference4 {

    public static void main(String[] args) {
        Class clazz = Parent.class;
        System.out.println(Parent.STR1);
    }
}
