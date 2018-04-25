package ThinkingInJava.Chapter10InnerClass;

/**
 * 创建一个private内部类，让它实现一个public接口。写一个方法，它返回一个指向此private内部类的实例引用，并将此引用向上转型为该接口类型。
 * 通过尝试向下转型，说明此（局部）内部类被完全隐藏了。
 */
public class Exercise11 {

    private class InnerClass implements MyInterface {
        public void print() {
            System.out.println("InnerClass");
        }
    }

    public MyInterface getInnerClassInstance() {
        return new InnerClass();
    }

    public MyInterface getLocalClassInstance() {
        class LocalClass implements MyInterface {
            @Override
            public void print() {
                System.out.println("LocalClass");
            }
        }
        return new LocalClass();
    }

    public static void main(String[] args) {
        Exercise11 exercise11 = new Exercise11();
        // 向下转型为内部类
        InnerClass innerClassInstance = (InnerClass) exercise11.getInnerClassInstance();
        innerClassInstance.print(); // InnerClass

        MyInterface localClassInstance = exercise11.getLocalClassInstance();
        // Error: 超出LocalClass的作用域，无法向下转型
        // LocalClass localClass = (LocalClass) interfaceInstance;
        // 虽然LocalClass的作用域在getLocalClassInstance()方法内，但并不意味着一旦该方法执行完毕，局部类LocalClass就不可用了
        localClassInstance.print(); // LocalClass
    }
}

interface MyInterface {
    void print();
}
