package network;

import java.nio.ByteBuffer;

/**
 * Состояние одного клиентского соединения.
 * Хранит накопленные байты до получения полного пакета.
 *
 * Протокол пакета:
 *   [4 байта: длина данных (int)] [N байт: сериализованный объект Request]
 */
public class ClientState {

    // Буфер для чтения — достаточно большой для одного запроса
    public ByteBuffer readBuffer = ByteBuffer.allocate(65536);

    // Было ли уже прочитано поле длины
    public boolean lengthRead = false;
    public int expectedLength = 0;

    public void reset() {
        readBuffer.clear();
        lengthRead = false;
        expectedLength = 0;
    }
}
