package test.java.nio.channels;

import org.junit.Test;

import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yanchao
 * @date 2017/10/27 14:36
 */
public class SelectorAndSelectionKeyTest {

    //创建Selector实例
    //@Test
    public void open() {
        try {
            Selector selector = Selector.open();
        } catch (Exception e) {

        }
    }

    /**
     * 与Selector一起使用时，Channel必须处于非阻塞模式下，这意味着Selector不能与FileChannel一起使用，
     * 因为FileChannel不能切换到非阻塞模式。而套接字Channel可以(套接字Channel包括SocketChannel、ServerSocketChannel)
     */
    //@Test运行无意义，旨在说明Selector的用法
    public void channelRegistToSelector() {
        Selector selector = null;
        try {
            selector = Selector.open();
            //创建SocketChannel
            SocketChannel socketChannel = SocketChannel.open();
            //将Channel切换到非阻塞模式
            socketChannel.configureBlocking(false);
            /**
             * register()方法的第二个参数是“interest集合”，表示Selector监听Channel时对什么事件感兴趣。可监听的事件包括：
             *  1. Connect;     SelectionKey.OP_CONNECT     连接就绪
             *  2. Accept;      SelectionKey.OP_ACCEPT      接收就绪
             *  3. Read;        SelectionKey.OP_READ        读就绪
             *  4. Write;       SelectionKey.OP_WRITE       写就绪
             *  如果对多个事件感兴趣，可以使用“或”操作将常量连接起来
             */
            int interests = SelectionKey.OP_ACCEPT | SelectionKey.OP_CONNECT;
            SelectionKey selectionKey = socketChannel.register(selector, interests);

            //通过按位与 & 操作可以判断interest集合是否对某一个事件感兴趣
            boolean isInterestedInAccept = (interests & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;


            /**
             * 获取ready集合，该集合表示了Channel已经准备就绪的操作的集合，可以使用两种方式来判断某一事件是否准备就绪：
             * 1. 使用按位与来判断哪个事件已经就绪了；
             * 2. 调用SelectionKey#isXXXable()方法判断，如 isAcceptable()、isConnectable()、isReadable()、isWritable()
             */
            int readySet = selectionKey.readyOps();
            //1. 通过按位与的方式判断ACCEPT事件是否已经准备就绪
            boolean isAcceptReady = (readySet & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT;
            //2. 通过isXXXable()判断某一事件是否准备就绪
            boolean isConnectReady = selectionKey.isConnectable();


            /**
             * 通过SelectionKey获取Channel和Selector
             * 但获取到的Channel需要自己转型为要处理的类型，ServerSocketChannel或SocketChannel。
             */
            Channel channel = selectionKey.channel();
            selector = selectionKey.selector();


            /**
             * 添加附加对象，可以用来标识给定的某个Channel，有两种方式：
             * 1. 调用SelectionKey#attach(Object)方法
             * 2. 在register的时候添加，如 channel.register(selector, SelectionKey.OP_ACCEPT, theObject)
             */
            selectionKey.attach(new Object());


            /**
             * 通过Selector#select()获取已经就绪的Channel个数:
             * 1. int select()                  阻塞，直到至少有一个通道在你注册的事件上就绪了.返回值表示自上次调用select()后有多少Channel已经就绪，即使上次调用select()后存在Channel且没有进行任何处理
             * 2. int select(long timeout)      阻塞，同select()类似，只是多了一个超时时间
             * 3. int selectNow()               非阻塞，不管什么通道就绪都立即返回，若没有Channel变成可选择的，返回0
             */
            int readySelectorCount = selector.select();


            if (readySelectorCount > 0) {
                /**
                 * 当通过调用select()方法发现有Channel在给定的事件上准备就绪了，那就可以进行相应的操作
                 * 首先，通过selectedKeys()方法获取准备就绪的Channel对应的SelectionKeys
                 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey1 = iterator.next();
                    if (selectionKey1.isAcceptable()) {
                        //Do something
                    } else if (selectionKey1.isConnectable()) {
                        //Do something
                    } else if (selectionKey1.isReadable()) {
                        //Do something
                    } else if (selectionKey1.isWritable()) {
                        //Do something
                    }
                    //这里需要自己移除掉，Selector不会自己进行移除动作，当下一次该通道变成就绪时，Selector还会将其添加进来
                    iterator.remove();
                }
            }


            /**
             * Selector#wakeUp()方法
             * 当某个调用了select()方法的线程在阻塞情况下也可以立即返回，方法就是在其他线程中调用被阻塞的Selector对象的wakeUp()方法
             */

        } catch (Exception e) {

        } finally {
            if (selector != null) {
                try {
                    //Selector使用完毕后调用其close()方法以关闭该Selector，且使注册到该Selector上所有的SelectionKey实例无效
                    //但Channel本身并不会关闭
                    selector.close();
                } catch (Exception e) {}
            }
        }
    }

}
