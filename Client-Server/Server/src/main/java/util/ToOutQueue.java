package util;

import json.JsonManager;
import json.ServerResponse;

import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ToOutQueue {

    private static final BlockingQueue<String> outQueue = new LinkedBlockingQueue<>();

    public static void addToOutQueue(SocketChannel socketChannel, String key, String... parameters) {
        outQueue.add(JsonManager.responseSerialization(new ServerResponse(socketChannel, key, parameters)));
    }


}
