package test.java.util.ObserverAndObservableTest;

/**
 * @author yanchao
 * @date 2018/1/30 16:34
 */
public class Client {
    public static void main(String[] args) {
        StateObservable observable = new StateObservable();
        observable.addObserver(new OddNumberObserver());
        observable.addObserver(new EvenNumberObserver());
        observable.setState(1);
        observable.setState(2);
        observable.setState(3);
        observable.setState(4);
    }
}
