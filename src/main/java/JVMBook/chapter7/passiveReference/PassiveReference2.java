package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/5/31 11:00
 * 通过数组定义来引用
 */
public class PassiveReference2 {

    public static void main(String[] args) {
        Child[] childs = new Child[10];
        Parent[] parents = new Parent[10];
        // 如果只初始化父类的话，不会触发子类的初始化
        // parents[0] = new Parent();
        // 如果只初始化子类的话，在子类初始化之前会触发父类的初始化
        // childs[0] = new Child();
    }
}
