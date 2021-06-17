package nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class NIOServer {

    private final static int LISTENER_PORT = 3232;

    private Selector selector;

    public static void main(String[] args) throws IOException {
    }

    /**
     * 初始化
     * @throws IOException
     */
    private void init() throws IOException {
        this.selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(LISTENER_PORT));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    /**
     * 监听方法
     * @throws IOException
     */
    private void listener() throws IOException {
        while (true){
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    // acceptable事件时;
                    acceptHandler(key);
                }
                iterator.remove();
            }
        }
    }

    /**
     * 连接事件处理器
     */
    public void acceptHandler(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(this.selector,SelectionKey.OP_READ);

        Socket socket = socketChannel.socket();
        InetAddress inetAddress = socket.getInetAddress();
        String clientIp = inetAddress.getHostAddress();
        String clientHostName = inetAddress.getHostName();
        log.info("The new client connection IP is {} and hostName is {}",clientIp,clientHostName);
    }
}
