import exceptions.RecursionException;
import json.JsonManager;
import localization.AnnounceManager;
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
                } catch (RecursionException e) {
                    // Ловим исключения, чтобы поток консоли не упал при ошибке ввода
                    System.out.println(e.getMessage());
                } finally {
                    consoleManager.setIsSystemReader(true);
                }
            }
        });
        consoleThread.setDaemon(true); // Завершится автоматически при выходе из приложения
        consoleThread.start();

        while (running) {
            AnnounceManager.getInstance().println("try.to.connect");

            try (Socket socket = new Socket("localhost", 9090);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {

                AnnounceManager.getInstance().println("connection.success");
                isConnected = true;

                // =================================================================
                // 2. ПОТОК СЕТЕВОГО ЧТЕНИЯ (Пересоздается при каждом новом подключении)
                // =================================================================
                /*Thread readThread = new Thread(() -> {
                    while (running && isConnected) {
                        try {
                            String response = in.readLine();

                            if (response == null) {
                                AnnounceManager.getInstance().println("connection.is.closed");
                                isConnected = false;
                                break;
                            }

                            AnnounceManager.getInstance().println("server.response", JsonManager.responseDeserialization(response).toString());

                        } catch (IOException e) {
                            if (running && isConnected) {
                                AnnounceManager.getInstance().println("connection.is.lost");
                                isConnected = false;
                            }
                            break;
                        }
                    }
                });
                readThread.setDaemon(true);
                readThread.start();*/

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

                        // Если связь пропала, пока мы обрабатывали/ждали, возвращаем команду назад
                        if (!isConnected) {
                            outQueue.add(line);
                            break;
                        }

                        out.println(line);

                        if (out.checkError()) {
                            AnnounceManager.getInstance().println("cant.send.data");
                            isConnected = false;
                        }

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        running = false;
                        break;
                    }
                }

            } catch (ConnectException e) {
                AnnounceManager.getInstance().println("server.unavailable");
            } catch (IOException e) {
                AnnounceManager.getInstance().println("io.exception");
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
    }
}