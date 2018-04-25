package ThinkingInJava.Chapter10InnerClass;

interface Incrementable {
    void increment();
}

class MyIncrement {
    public void increment() {
        System.out.println("Other operation");
    }

    static void f(MyIncrement mi) {
        mi.increment();
    }
}

class Callee2 extends MyIncrement {
    private int i = 0;
    @Override
    public void increment() {
        super.increment();
        i++;
        System.out.println("i = " + i);
    }

    private class Closure implements Incrementable {
        @Override
        public void increment() {
            Callee2.this.increment();
        }
    }

    Incrementable getCallbackReference() {
        return new Closure();
    }
}

public class Callbacks {

    public static void main(String[] args) {
        Callee2 callee2 = new Callee2();
        callee2.increment();
        callee2.increment();
    }
}
