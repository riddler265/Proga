package network;

import commands.CommandProcessor;
import json.JsonManager;
import managers.CollectionManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.logging.Logger;

/**
 * Модуль приёма подключений.
 * Работает в однопоточном неблокирующем режиме через NIO Selector.
 *
 * Клиент использует неблокирующий SocketChannel.
 * Сервер читает/пишет через потоки ввода-вывода (ObjectInputStream / ObjectOutputStream),
 * но принимает соединения через ServerSocketChannel в неблокирующем режиме.
 */
public class ConnectionAcceptor {

    private static final Logger logger = Logger.getLogger(ConnectionAcceptor.class.getName());

    private final int port;
    private final CommandProcessor commandProcessor;

    // Буфер для чтения длины пакета (4 байта - int)
    private static final int LENGTH_HEADER_SIZE = 4;

    public ConnectionAcceptor(int port, CollectionManager collectionManager) throws IOException {
        this.port = port;
        this.commandProcessor = new CommandProcessor(collectionManager);
    }

    /**
     * Основной цикл сервера: однопоточный, неблокирующий (NIO Selector).
     */
    public void run() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        logger.info("Server listening on port " + port);

        while (true) {
            // Проверяем stdin для серверных команд (save, exit)
            handleServerConsole();

            selector.select(500); // таймаут 500мс, чтобы проверять консоль

            Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();
                keyIterator.remove();

                if (!key.isValid()) continue;

                if (key.isAcceptable()) {
                    acceptConnection(serverChannel, selector);
                } else if (key.isReadable()) {
                    handleClient(key);
                }
            }
        }
    }

    /**
     * Принимает новое подключение.
     */
    private void acceptConnection(ServerSocketChannel serverChannel, Selector selector) {
        try {
            SocketChannel clientChannel = serverChannel.accept();
            if (clientChannel == null) return;

            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ, new ClientState());

            logger.info("New connection accepted: " + clientChannel.getRemoteAddress());
        } catch (IOException e) {
            logger.warning("Error accepting connection: " + e.getMessage());
        }
    }

    /**
     * Обрабатывает данные от клиента.
     */
    private void handleClient(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ClientState state = (ClientState) key.attachment();

        try {
            // Читаем данные в буфер состояния клиента
            int bytesRead = channel.read(state.readBuffer);

            if (bytesRead == -1) {
                // Клиент отключился
                logger.info("Client disconnected: " + channel.getRemoteAddress());
                key.cancel();
                channel.close();
                return;
            }

            // Пробуем собрать полный пакет и обработать
            RequestReader reader = new RequestReader();
            String request = reader.tryRead(state);

            if (request != null) {
                logger.info("Request received: " + request);

                // Обрабатываем команду
                ResponseSender sender = new ResponseSender();
                Response response = commandProcessor.process(request);

                // Отправляем ответ
                logger.info("Sending response for command: " + JsonManager.getCommand(request));
                sender.send(channel, response);

                // Сбрасываем состояние для следующего запроса
                state.reset();
            }

        } catch (IOException e) {
            logger.warning("Client communication error: " + e.getMessage());
            key.cancel();
            try { channel.close(); } catch (IOException ignored) {}
        }
    }

    /**
     * Серверные команды из stdin (save, exit).
     */
    private void handleServerConsole() {
        try {
            if (System.in.available() > 0) {
                byte[] buf = new byte[System.in.available()];
                int n = System.in.read(buf);
                String input = new String(buf, 0, n).trim();

                if (input.equalsIgnoreCase("save")) {
                    commandProcessor.getCollectionManager().saveCollection();
                    System.out.println("[SERVER] Collection saved.");
                    logger.info("Server command: save executed");
                } else if (input.equalsIgnoreCase("exit")) {
                    logger.info("Server command: exit — shutting down");
                    System.exit(0);
                } else {
                    System.out.println("[SERVER] Unknown server command. Available: save, exit");
                }
            }
        } catch (IOException ignored) {}
    }
}
