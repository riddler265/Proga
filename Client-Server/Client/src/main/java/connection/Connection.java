package connection;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

// Connection.java — отвечает только за установку соединения
public class Connection {
    private SocketChannel channel;

    public Connection(String host, int port) throws Exception {
        channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress(host, port));
        //while (!channel.finishConnect()) {}
    }

    public SocketChannel getChannel() {
        return channel;
    }
}
