import communication.Request;
import exceptions.RecursionException;
import localization.AnnounceManager;
import localization.ResponsePrinter;
import util.ConsoleManager;
import util.json.JsonManager;

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
    private static final BlockingQueue<Request> outQueue = new LinkedBlockingQueue<>();
    private static volatile boolean running = true;
    private static volatile boolean isConnected = false;

    public static void main(String[] args) {

        ConsoleManager consoleManager = new ConsoleManager(outQueue);
        Scanner scanner = new Scanner(System.in);

        Thread consoleThread = new Thread(() -> {
            while (running) {
                try {
                    if (scanner.hasNextLine()) {
                        consoleManager.execute(scanner.nextLine().trim(), scanner);
                    }
                } catch (RecursionException e) {
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

                Thread readThread = new Thread(() -> {
                    while (running && isConnected) {
                        try {
                            String response = in.readLine();

                            if (response == null) {
                                AnnounceManager.getInstance().println("connection.is.closed");
                                isConnected = false;
                                break;
                            }

                            ResponsePrinter.print(response);

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
                readThread.start();

                while (running && isConnected) {
                    try {
                        Request request = outQueue.poll(1, TimeUnit.SECONDS);

                        if (request == null) {
                            continue;
                        }

                        if (!isConnected) {
                            outQueue.add(request);
                            break;
                        }

                        out.println(JsonManager.serializeRequest(request));

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
                System.out.println("ConnectException: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
                e.printStackTrace();
            } finally {
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