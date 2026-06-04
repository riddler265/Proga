package network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Logger;

/**
 * Модуль отправки ответов клиенту.
 * Сериализует Response и отправляет через SocketChannel с заголовком длины.
 *
 * Протокол: [int length][bytes of serialized Response]
 */
public class ResponseSender {

    private static final Logger logger = Logger.getLogger(ResponseSender.class.getName());

    public void send(SocketChannel channel, Response response) throws IOException {
        // Сериализуем Response в байты
        byte[] data;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(response);
            oos.flush();
            data = baos.toByteArray();
        }

        // Формируем пакет: [4 байта длины] + [данные]
        ByteBuffer packet = ByteBuffer.allocate(4 + data.length);
        packet.putInt(data.length);
        packet.put(data);
        packet.flip();

        // Отправляем полностью (цикл, т.к. канал неблокирующий)
        while (packet.hasRemaining()) {
            channel.write(packet);
        }

        logger.fine("Response sent: " + data.length + " bytes, success=" + response.isSuccess());
    }
}
