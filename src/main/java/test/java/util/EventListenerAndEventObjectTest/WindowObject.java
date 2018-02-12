package test.java.util.EventListenerAndEventObjectTest;

import java.util.EventObject;

/**
 * @author yanchao
 * @date 2018/1/30 15:23
 *
 * 事件对象：事件发生器，即生成事件的组件，其应该维护事件的状态和各个状态的处理事件
 * 当事件监听器监听到事件以后，其处理依然会交给事件对象来进行处理，即事件监听器会维护一个事件对象
 */
public class WindowObject extends EventObject {

    public WindowObject(Object source) {
        super(source);
    }

    public void doEvent() {
        System.out.println("receive a event : " + this.getSource());
    }

    public void open() {
        System.out.println("window open action");
    }

    public void close() {
        System.out.println("window close action");
    }
}
