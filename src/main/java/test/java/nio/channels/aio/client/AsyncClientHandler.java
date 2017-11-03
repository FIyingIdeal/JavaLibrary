package test.java.nio.channels.aio.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

/**
 * @author yanchao
 * @date 2017/10/31 17:24
 */
public class AsyncClientHandler implements CompletionHandler<Void, AsyncClientHandler>, Runnable {

    private AsynchronousSocketChannel clientChannel;
    private String host;
    private int port;
    private CountDownLatch latch;

    public AsyncClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            clientChannel = AsynchronousSocketChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        clientChannel.connect(new InetSocketAddress(host, port), this, this);
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            clientChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //连接服务器成功
    //意味着TCP三次握手完成
    @Override
    public void completed(Void result, AsyncClientHandler attachment) {
        System.out.println("客户端成功连接到服务器...");
    }

    //连接服务器失败
    @Override
    public void failed(Throwable exc, AsyncClientHandler attachment) {
        System.err.println("连接服务器失败...");
        exc.printStackTrace();
        try {
            clientChannel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //向服务器发送消息
    public void sendMsg(String msg){
        byte[] req = msg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        //异步写
        clientChannel.write(writeBuffer, writeBuffer,new WriteHandler(clientChannel, latch));
    }

}
