package network;

import json.JsonManager;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Модуль отправки ответов клиенту.
 * Сериализует Response в JSON и отправляет через SocketChannel.
 * Протокол: JSON-строка с '\n' в конце.
 */
public class ResponseSender {

    private static final Logger logger = Logger.getLogger(ResponseSender.class.getName());

    public void send(SocketChannel channel, Response response) throws IOException {
        String json = response.toJson() + "\n";
        byte[] data = json.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        logger.fine("Response sent: " + data.length + " bytes, success=" + response.isSuccess());
    }
}