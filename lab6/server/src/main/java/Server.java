import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9090);
        System.out.println("Ждём подключения...");

        Socket client = serverSocket.accept(); // блокирует до подключения
        while (true) {}
    }
}