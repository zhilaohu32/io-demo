package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class NIOServer {

    private final static int LISTENER_PORT = 3232;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(LISTENER_PORT));

        // 设置非阻塞
        serverSocketChannel.configureBlocking(false);
    }

    /**
     * 处理器
     */
    public static void handler(){

    }
}
