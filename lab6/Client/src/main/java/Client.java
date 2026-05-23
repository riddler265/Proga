import connection.Connection;
import exceptions.RecursionException;
import json.JsonManager;
import util.InputManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Scanner;

// EchoClient.java — отвечает за отправку и чтение
// EchoClient.java
public class Client {
    public static void main(String[] args) throws Exception {

        //Подготовка
        InputManager inputManager = new InputManager();
        Scanner scanner = new Scanner(System.in);

        try (SocketChannel channel = new Connection("localhost", 9090).getChannel()) {


            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ);

            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
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
                        inputManager.execute(scanner.nextLine(), scanner);
                    } catch (RecursionException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        inputManager.setIsSystemReader(true);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}