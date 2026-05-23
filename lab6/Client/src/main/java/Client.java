import connection.Connection;
import exceptions.RecursionException;
import json.JsonManager;
import util.ConsoleManager;

import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.concurrent.*;

public class Client {

    private static final BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;

    public static void main(String[] args) {

        ConsoleManager consoleManager = new ConsoleManager(outQueue);
        Scanner scanner = new Scanner(System.in);

        try (SocketChannel channel = new Connection("localhost", 9090).getChannel()) {

            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            // поток для консоли
            Thread consoleThread = new Thread(() -> {
                while (running) {
                    try {
                        consoleManager.execute(scanner.nextLine().trim(), scanner);
                    } catch (RecursionException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        consoleManager.setIsSystemReader(true);
                    }
                }
            });
            consoleThread.setDaemon(true);
            consoleThread.start();

            // основной поток — сеть
            while (running) {
                selector.selectNow();

                for (SelectionKey key : selector.selectedKeys()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int bytes = channel.read(readBuffer);

                    if (bytes == -1) {
                        System.out.println("Сервер отключился.");
                        running = false;
                        break;
                    }

                    if (bytes > 0) {
                        readBuffer.flip();
                        String response = new String(readBuffer.array(), 0, readBuffer.limit());
                        System.out.println(JsonManager.parseResponse(response).toString());
                    }
                }
                selector.selectedKeys().clear();

                // отправляем накопленное из очереди
                String line;
                while ((line = outQueue.poll()) != null) {
                    if ("quit".equals(line)) {
                        running = false;
                        break;
                    }
                    ByteBuffer buffer = ByteBuffer.wrap((line + "\n").getBytes());
                    channel.write(buffer);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}



/*import connection.Connection;
import exceptions.RecursionException;
import json.JsonManager;
import util.consoleManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Scanner;
import java.util.concurrent.*;

// EchoClient.java — отвечает за отправку и чтение
// EchoClient.java
public class Client {

    private static final BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;

    public static void main(String[] args) throws Exception {

        //Подготовка
        consoleManager consoleManager = new consoleManager();
        Scanner scanner = consoleManager.getScanner();

        try (SocketChannel channel = new Connection("localhost", 9090).getChannel()) {


            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while (running) {
                // проверяем есть ли ответ от сервера (не блокируем — таймаут 0)
                selector.selectNow();
                for (SelectionKey key : selector.selectedKeys()) {
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    channel.read(readBuffer);
                    readBuffer.flip();
                    System.out.println(new String(readBuffer.array(), 0, readBuffer.limit()));
                    System.out.println(JsonManager.parseResponse(new String(readBuffer.array(), 0, readBuffer.limit())).toString());
                }
                selector.selectedKeys().clear();

                // проверяем есть ли ввод с консоли
                if (console.ready()) {
                    try {
                        consoleManager.execute(scanner.nextLine(), scanner);
                    } catch (RecursionException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        consoleManager.setIsSystemReader(true);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}*/