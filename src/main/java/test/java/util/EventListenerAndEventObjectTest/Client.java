package test.java.util.EventListenerAndEventObjectTest;

/**
 * @author yanchao
 * @date 2018/1/30 15:34
 * @reference https://my.oschina.net/u/923324/blog/792857
 *
 * JDK自带的事件监听机制，其有三个关键组件：事件对象，事件源，事件监听器
 *      · 事件对象：事件发生器，如一个按钮，窗口等，其内部应该封装有时间状态或对应事件的处理机制
 *      · 事件源：事件触发/发布器，当事件对象发生一个特定事件（如窗口关闭）的时候，会将该事件通知给事件监听器
 *      · 事件监听器：负责处理监听的事件，当被监听的事件发生时，进行相应的处理
 */
public class Client {

    public static void main(String[] args) {
        EventSource eventSource = new EventSource();
        eventSource.addWindowListener((event) -> {
            event.doEvent();
            if (event.getSource().equals(WindowEventType.OPEN)) {
                event.open();
            } else {
                event.close();
            }
        });

        eventSource.notifyListeners(new WindowObject(WindowEventType.OPEN));

        eventSource.notifyListeners(new WindowObject(WindowEventType.CLOSE));
    }
}
