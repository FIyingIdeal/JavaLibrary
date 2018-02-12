package test.java.util.ObserverAndObservableTest;

import java.util.Observable;
import java.util.Observer;

/**
 * @author yanchao
 * @date 2018/1/30 16:28
 */
public class EvenNumberObserver implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        if (Integer.parseInt(arg.toString()) % 2 == 0) {
            System.out.println(this.getClass().getSimpleName() + " : " + arg.toString());
        }
    }
}
