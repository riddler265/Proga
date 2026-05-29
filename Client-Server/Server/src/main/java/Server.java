import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Server {

    private static final int PORT = 9090;
    private static Selector selector;
    private static ServerSocketChannel serverChannel;
    private static boolean running = true;
    public static final BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();

    public static void main(String[] args) {
        try {
            // 1. Открываем селектор и серверный канал
            selector = Selector.open();
            serverChannel = ServerSocketChannel.open();

            // 2. Привязываем к порту и делаем НЕБЛОКИРУЮЩИМ
            serverChannel.bind(new InetSocketAddress(PORT));
            serverChannel.configureBlocking(false);

            // 3. Регистрируем серверный канал на приём подключений (OP_ACCEPT)
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("Простой Эхо-сервер запущен на порту " + PORT);

            // Главный однопоточный цикл
            while (running) {
                // Ждем сетевых событий (поток спит, пока ничего не происходит)
                selector.select();

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove(); // Удаляем ключ из списка обработанных

                    if (!key.isValid()) continue;

                    // Событие А: Кто-то подключается
                    if (key.isAcceptable()) {
                        handleAccept();
                    }
                    // Событие Б: Кто-то прислал данные
                    else if (key.isReadable()) {
                        handleRead(key);
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    private static void handleAccept() throws IOException {
        SocketChannel clientChannel = serverChannel.accept();
        if (clientChannel != null) {
            // Делаем канал клиента неблокирующим
            clientChannel.configureBlocking(false);

            // Создаем персональный текстовый буфер для этого клиента
            StringBuilder clientBuffer = new StringBuilder();

            // Регистрируем на чтение (OP_READ) и ПРИКРЕПЛЯЕМ (attach) к ключу наш буфер
            clientChannel.register(selector, SelectionKey.OP_READ, clientBuffer);

            System.out.println("Подключился новый клиент: " + clientChannel.getRemoteAddress());
        }
    }

    private static void handleRead(SelectionKey key) {
        SocketChannel clientChannel = (SocketChannel) key.channel();

        // Достаем буфер, который мы закрепили за этим конкретным клиентом в handleAccept
        StringBuilder clientBuffer = (StringBuilder) key.attachment();

        ByteBuffer readBuffer = ByteBuffer.allocate(1024);

        try {
            int bytesRead = clientChannel.read(readBuffer);

            // Если вернулось -1, значит клиент закрыл соединение
            if (bytesRead == -1) {
                System.out.println("Клиент отключился: " + clientChannel.getRemoteAddress());
                clientChannel.close();
                key.cancel();
                return;
            }

            if (bytesRead > 0) {
                readBuffer.flip();
                // Декодируем байты в строку и добавляем в накопленный буфер клиента
                String chunk = StandardCharsets.UTF_8.decode(readBuffer).toString();
                clientBuffer.append(chunk);

                // Проверяем, пришла ли целая строка (до символа \n)
                int newlineIndex;
                while ((newlineIndex = clientBuffer.indexOf("\n")) != -1) {
                    // Вырезаем строку (вместе с \n, чтобы клиенту было удобно читать через readLine)
                    String messageToEcho = clientBuffer.substring(0, newlineIndex + 1);
                    // Удаляем отправленную часть из буфера
                    clientBuffer.delete(0, newlineIndex + 1);

                    System.out.print("Повторяю клиенту: " + messageToEcho);

                    // Отправляем данные обратно в неблокирующем режиме
                    ByteBuffer writeBuffer = ByteBuffer.wrap(messageToEcho.getBytes(StandardCharsets.UTF_8));
                    while (writeBuffer.hasRemaining()) {
                        clientChannel.write(writeBuffer);
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Связь с клиентом оборвалась жестко.");
            try {
                clientChannel.close();
            } catch (IOException ex) { /* ignore */ }
            key.cancel();
        }
    }

    private static void stopServer() {
        try {
            if (serverChannel != null) serverChannel.close();
            if (selector != null) selector.close();
        } catch (IOException e) { /* ignore */ }
    }
}



/*import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Сервер запущен. Ждём подключения...");

        // Блокирует поток, пока клиент не подключится
        try (Socket clientSocket = serverSocket.accept();
             // Создаем поток для чтения данных от клиента
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             // Создаем поток для отправки ответа клиенту (если нужно)
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            System.out.println("Клиент успешно подключился: " + clientSocket.getRemoteSocketAddress());

            String inputLine;
            // Читаем строки от клиента, пока он не закроет соединение
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Получено от клиента: " + inputLine);

                // Имитируем ответ сервера, чтобы клиент не зависал (ведь ваш клиент ждет JSON)
                // Замените эту строку на отправку вашего настоящего JSON-ответа
                String jsonResponse = "{\"type\": \"LOGIN_SUCCESS\", \"parameters\": []}";
                out.println(jsonResponse);
            }

            System.out.println("Клиент отключился.");

        } catch (IOException e) {
            System.out.println("Ошибка при работе с клиентом: " + e.getMessage());
        }
    }
}*/