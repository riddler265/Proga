package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Модуль отправки ответов клиенту.
 * Работает в non-blocking NIO режиме — НЕ переключает канал в blocking.
 * Использует spin-wait с yield для случаев когда write() возвращает 0.
 */
public class ResponseSender {

    private static final Logger logger = Logger.getLogger(ResponseSender.class.getName());

    public void send(SocketChannel channel, Response response) throws IOException {
        String json = response.toJson() + "\n";
        write(channel, json);
        logger.fine("Response sent: " + json.length() + " bytes, success=" + response.isSuccess());
    }

    /**
     * Записывает строку в non-blocking SocketChannel.
     * Не переключает режим канала — Selector остаётся рабочим.
     */
    public static void write(SocketChannel channel, String text) throws IOException {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.wrap(data);
        int attempts = 0;
        while (buffer.hasRemaining()) {
            int written = channel.write(buffer);
            if (written == 0) {
                // Буфер сокета временно полон — даём другим потокам поработать
                attempts++;
                if (attempts > 1000) {
                    throw new IOException("Failed to write after 1000 attempts — channel buffer full");
                }
                Thread.yield();
            } else {
                attempts = 0;
            }
        }
    }
}
