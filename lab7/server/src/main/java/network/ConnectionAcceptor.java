package network;

import commands.CommandProcessor;
import json.JsonManager;
import managers.CollectionManager;
import subscription.NotificationBus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

/**
 * Многопоточный сервер.
 *
 * Архитектура по заданию:
 *   - Fixed thread pool (4 потока) — чтение запросов
 *   - Cached thread pool          — обработка команд
 *   - Fixed thread pool (4 потока) — отправка ответов
 *
 * Главный поток крутит Selector и раздаёт задачи в readPool.
 */
public class ConnectionAcceptor {

    private static final Logger logger = Logger.getLogger(ConnectionAcceptor.class.getName());

    private final int port;
    private final CommandProcessor commandProcessor;
    private final managers.CollectionManager collectionManager;

    // Fixed pool — для чтения входящих запросов
    private final ExecutorService readPool    = Executors.newFixedThreadPool(4);
    // Cached pool — для обработки команд
    private final ExecutorService processPool = Executors.newCachedThreadPool();
    // Fixed pool — для отправки ответов
    private final ExecutorService writePool   = Executors.newFixedThreadPool(4);

    public ConnectionAcceptor(int port, CollectionManager collectionManager,
                               auth.UserManager userManager) throws IOException {
        this.port = port;
        this.collectionManager = collectionManager;
        this.commandProcessor = new CommandProcessor(collectionManager, userManager);
    }

    public void run() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(new InetSocketAddress(port));

        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        logger.info("Server listening on port " + port);
        startConsoleThread();

        while (true) {
            selector.select(500);

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey key = it.next();
                it.remove();
                if (!key.isValid()) continue;

                if (key.isAcceptable()) {
                    acceptConnection(serverChannel, selector);
                } else if (key.isReadable()) {
                    // Снимаем ключ с чтения пока не обработаем — иначе selector будет
                    // срабатывать повторно на этот же канал
                    key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
                    readPool.submit(() -> handleRead(key, selector));
                }
            }
        }
    }

    private void acceptConnection(ServerSocketChannel serverChannel, Selector selector) {
        try {
            SocketChannel clientChannel = serverChannel.accept();
            if (clientChannel == null) return;
            clientChannel.configureBlocking(false);
            ClientState state = new ClientState();
            synchronized (selector) {
                clientChannel.register(selector, SelectionKey.OP_READ, state);
            }
            logger.info("New connection: " + clientChannel.getRemoteAddress());
        } catch (IOException e) {
            logger.warning("Accept error: " + e.getMessage());
        }
    }

    private void handleRead(SelectionKey key, Selector selector) {
        SocketChannel channel = (SocketChannel) key.channel();
        ClientState state = (ClientState) key.attachment();

        try {
            int bytesRead = channel.read(state.readBuffer);
            if (bytesRead == -1) {
                logger.info("Client disconnected");
                NotificationBus.getInstance().unregister(channel);
                key.cancel();
                channel.close();
                return;
            }

            RequestReader reader = new RequestReader();
            String request = reader.tryRead(state);

            if (request != null) {
                final String req = request;
                // Регистрируем канал в NotificationBus по логину
                String login = JsonManager.getLogin(req);
                if (login != null) NotificationBus.getInstance().register(login, channel);

                // Обработка в cachedPool
                processPool.submit(() -> {
                    Response response = commandProcessor.process(req);
                    // Отправка в writePool
                    writePool.submit(() -> {
                        try {
                            new ResponseSender().send(channel, response);
                            state.reset();
                            // Возвращаем OP_READ
                            synchronized (selector) {
                                if (key.isValid()) {
                                    key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                                    selector.wakeup();
                                }
                            }
                        } catch (IOException e) {
                            logger.warning("Write error: " + e.getMessage());
                            key.cancel();
                            try { channel.close(); } catch (IOException ignored) {}
                            NotificationBus.getInstance().unregister(channel);
                        }
                    });
                });
            } else {
                // Данные ещё не полные — восстанавливаем OP_READ
                synchronized (selector) {
                    if (key.isValid()) {
                        key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                        selector.wakeup();
                    }
                }
            }
        } catch (IOException e) {
            logger.warning("Read error: " + e.getMessage());
            key.cancel();
            try { channel.close(); } catch (IOException ignored) {}
            NotificationBus.getInstance().unregister(channel);
        }
    }

    /** Читает команды из консоли сервера в отдельном потоке — не блокирует Selector. */
    private void startConsoleThread() {
        Thread consoleThread = new Thread(() -> {
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(System.in));
            while (true) {
                try {
                    String input = reader.readLine();
                    if (input == null) break;
                    input = input.trim();
                    if (input.equalsIgnoreCase("exit")) {
                        logger.info("Server exit command");
                        System.exit(0);
                    } else if (input.equalsIgnoreCase("save")) {
                        // Выполняем в processPool чтобы не блокировать консольный поток
                        processPool.submit(() -> {
                            collectionManager.loadCollection();
                            int after = collectionManager.getCollection().size();
                            System.out.println("[SERVER] Collection synced with DB. Elements: " + after);
                            logger.info("Server command: save executed, elements=" + after);
                        });
                    } else if (input.equalsIgnoreCase("help")) {
                        System.out.println("[SERVER] Available commands:");
                        System.out.println("  save  — sync collection with DB");
                        System.out.println("  exit  — shutdown server");
                    } else if (!input.isEmpty()) {
                        System.out.println("[SERVER] Unknown command: '" + input + "'. Type 'help' for list.");
                    }
                } catch (java.io.IOException e) {
                    logger.warning("Console read error: " + e.getMessage());
                    break;
                }
            }
        }, "server-console");
        consoleThread.setDaemon(true);
        consoleThread.start();
    }

    private void handleServerConsole() {
        // Оставляем пустым — консоль теперь читается в startConsoleThread()
    }
}
