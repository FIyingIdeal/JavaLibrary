package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/4/29 11:25
 * 通过.class来创建Class对象的引用时，不会自动的初始化该Class对象，不会触发类的初始化
 */
public class PassiveReference4 {

    public static void main(String[] args) {
        Class clazz = Parent.class;
    }
}
