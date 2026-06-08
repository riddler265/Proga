package network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Модуль чтения запроса.
 * Накапливает байты до символа '\n' и возвращает готовую JSON-строку.
 * Соответствует клиенту, который отправляет строку через PrintWriter.println().
 */
public class RequestReader {

    private static final Logger logger = Logger.getLogger(RequestReader.class.getName());

    /**
     * Пытается собрать полную JSON-строку из буфера клиента.
     * Возвращает строку если найден '\n', иначе null (ждём ещё данных).
     */
    public String tryRead(ClientState state) throws IOException {
        ByteBuffer buf = state.readBuffer;
        buf.flip();

        // Ищем символ '\n' среди накопленных байт
        int newlineIndex = -1;
        for (int i = 0; i < buf.limit(); i++) {
            if (buf.get(i) == '\n') {
                newlineIndex = i;
                break;
            }
        }

        // '\n' ещё не пришёл — ждём следующего вызова
        if (newlineIndex == -1) {
            buf.compact();
            return null;
        }

        // Читаем всё до '\n'
        byte[] data = new byte[newlineIndex];
        buf.get(data);

        // Пропускаем сам '\n'
        buf.get();

        // Остаток сохраняем для следующего запроса
        buf.compact();

        String json = new String(data, StandardCharsets.UTF_8).trim();
        logger.fine("Received JSON: " + json);
        return json;
    }
}