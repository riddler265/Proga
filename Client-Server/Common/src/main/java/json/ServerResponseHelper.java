package json;

import java.nio.channels.SocketChannel;

public record ServerResponseHelper(SocketChannel socketChannel, ServerResponse serverResponse) {
}
