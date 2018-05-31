package JVMBook.chapter7.passiveReference;

/**
 * @author yanchao
 * @date 2018/5/31 11:00
 * 通过子类引用父类的静态字段（使用-XX:+TraceClassLoading参数观察，Hot Spot中，会触发父类的加载和验证）
 */
public class PassiveReference1 {

    public static void main(String[] args) {
        System.out.println(Child.STR);
    }
}
