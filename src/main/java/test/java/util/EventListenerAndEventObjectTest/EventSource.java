package test.java.util.EventListenerAndEventObjectTest;

import java.util.Vector;

/**
 * @author yanchao
 * @date 2018/1/30 15:29
 *
 * 事件源：事件触发/发布器，当事件对象发生一个特定事件（如窗口关闭）的时候，会将该事件通知给事件监听器
 * 因此，在此需要维护一个事件监听器的列表（有可能一个事件会被多个事件监听器监听）
 */
public class EventSource {

    private Vector<WindowListener> listeners = new Vector<>();

    public void addWindowListener(WindowListener listener) {
        this.listeners.add(listener);
    }

    public void removeWindowListener(WindowListener listener) {
        this.listeners.remove(listener);
    }

    public void notifyListeners(WindowObject event) {
        for (WindowListener listener : listeners) {
            listener.handleEvent(event);
        }
    }
}
