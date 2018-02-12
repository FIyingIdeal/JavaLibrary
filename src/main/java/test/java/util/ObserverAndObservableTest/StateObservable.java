package test.java.util.ObserverAndObservableTest;

import java.util.Observable;

/**
 * @author yanchao
 * @date 2018/1/30 16:24
 */
public class StateObservable extends Observable {

    private int state;

    public void setState(int state) {
        this.state = state;
        this.change(state);
    }

    public int getState() {
        return this.state;
    }

    public void change(int state) {
        super.setChanged();
        super.notifyObservers(state);
    }
}
