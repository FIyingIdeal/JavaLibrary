package test.java.util.EventListenerAndEventObjectTest;

import java.util.EventListener;

/**
 * @datetime 2018-1-30 15:59:58
 * @author yanchao
 *
 * 事件监听器：负责监听事件对象各个事件事件状态，当某一事件发生的时候，调用该状态对应的处理方法
 */

public interface WindowListener extends EventListener {

    void handleEvent(WindowObject event);

}
