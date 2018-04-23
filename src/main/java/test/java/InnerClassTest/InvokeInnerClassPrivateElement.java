package test.java.InnerClassTest;

public class InvokeInnerClassPrivateElement {
    class Inner {
        private int i = 10;
        private int getI() {
            return this.i;
        }
    }

    public Inner getInner() {
        return this.new Inner();
    }

    public int getInnerField() {
        return this.getInner().getI();
    }

    public static void main(String[] args) {
        InvokeInnerClassPrivateElement test = new InvokeInnerClassPrivateElement();
        System.out.println(test.getInnerField());  // 10
    }
}
