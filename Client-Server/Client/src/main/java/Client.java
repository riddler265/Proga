import json.JsonManager;
import util.ConsoleManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Client {

    // Очередь для отправки (заполняется извне, например, вашим ConsoleManager)
    private static final BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;
    private static volatile boolean isConnected = false;

    public static void main(String[] args) {

        ConsoleManager consoleManager = new ConsoleManager(outQueue);
        Scanner scanner = new Scanner(System.in);

        // =================================================================
        // 1. ПОТОК КОНСОЛИ (Запускается один раз при старте программы)
        // =================================================================
        Thread consoleThread = new Thread(() -> {
            while (running) {
                try {
                    if (scanner.hasNextLine()) {
                        // Передаем строку в ConsoleManager. Он обработает её
                        // и сам положит нужный JSON-запрос в outQueue
                        consoleManager.execute(scanner.nextLine().trim(), scanner);
                    }
                } catch (Exception ex) {
                    // Ловим исключения, чтобы поток консоли не упал при ошибке ввода
                    System.out.println("Ошибка обработки команды: " + ex.getMessage());
                } finally {
                    consoleManager.setIsSystemReader(true);
                }
            }
        });
        consoleThread.setDaemon(true); // Завершится автоматически при выходе из приложения
        consoleThread.start();

        while (running) {
            System.out.println("Попытка подключения к серверу...");

            try (Socket socket = new Socket("localhost", 9090);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {

                System.out.println("Успешное подключение к серверу!");
                isConnected = true;

                // =================================================================
                // 2. ПОТОК СЕТЕВОГО ЧТЕНИЯ (Пересоздается при каждом новом подключении)
                // =================================================================
                Thread readThread = new Thread(() -> {
                    while (running && isConnected) {
                        try {
                            String response = in.readLine();

                            if (response == null) {
                                System.out.println("\nСервер закрыл соединение.");
                                isConnected = false;
                                break;
                            }

                            System.out.println("Ответ сервера: " + JsonManager.responseDeserialization(response).toString());

                        } catch (IOException e) {
                            if (running && isConnected) {
                                System.out.println("\n[СВЯЗЬ ОБОРВАНА] Потеряно соединение с сервером.");
                                isConnected = false;
                            }
                            break;
                        }
                    }
                });
                readThread.setDaemon(true);
                readThread.start();

                // =================================================================
                // 3. ЦИКЛ ОТПРАВКИ ДАННЫХ (Работает в основном потоке, пока есть связь)
                // =================================================================
                while (running && isConnected) {
                    try {
                        // Вместо take() используем poll() с ожиданием в 1 секунду.
                        // Если очередь пуста, поток просто подождет 1 секунду и проверит условие цикла (isConnected).
                        // Это позволит основному потоку МГНОВЕННО узнать о разрыве связи,
                        // даже если пользователь ничего не вводит в консоль!
                        String line = outQueue.poll(1, TimeUnit.SECONDS);

                        // Если за 1 секунду ничего не пришло, просто идем на новую проверку while(isConnected)
                        if (line == null) {
                            continue;
                        }

                        if ("quit".equals(line)) {
                            running = false;
                            break;
                        }

                        // Если связь пропала, пока мы обрабатывали/ждали, возвращаем команду назад
                        if (!isConnected) {
                            outQueue.add(line);
                            break;
                        }

                        out.println(line);

                        if (out.checkError()) {
                            System.out.println("[ОШИБКА] Не удалось отправить данные.");
                            isConnected = false;
                        }

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        running = false;
                        break;
                    }
                }

            } catch (ConnectException e) {
                System.out.println("Сервер недоступен. Следующая попытка через 5 секунд...");
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage() + ". Ожидание 5 секунд...");
            } finally {
                // Гарантируем, что флаг соединения сброшен при выходе из try-with-resources
                isConnected = false;
            }

            // Наш интервал в 5 секунд перед следующей итерацией (если приложение не закрывается)
            if (running) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                }
            }
        }

        System.out.println("Работа клиента завершена.");
    }
}














/*import connection.Connection;
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
                    } catch (Exception ex) {
                        System.out.println("hello");
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

                    // ЖЕСТКИЙ ЦИКЛ: пишем в канал до тех пор, пока в буфере остаются байты
                    while (buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}*/