package subscription;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class NotificationBus {

    private static final NotificationBus INSTANCE = new NotificationBus();
    public static NotificationBus getInstance() { return INSTANCE; }

    private final Map<String, List<SocketChannel>> channels = new ConcurrentHashMap<>();

    /** Регистрирует канал — не добавляет если уже есть. */
    public void register(String login, SocketChannel channel) {
        List<SocketChannel> list = channels.computeIfAbsent(login, k -> new CopyOnWriteArrayList<>());
        if (!list.contains(channel)) {
            list.add(channel);
        }
    }

    public void unregister(SocketChannel channel) {
        channels.values().forEach(list -> list.remove(channel));
    }

    public void notify(String login, String message) {
        List<SocketChannel> list = channels.get(login);
        if (list == null || list.isEmpty()) return;

        String json = "{\"notification\":\"" + escape(message) + "\"}\n";

        list.removeIf(ch -> {
            try {
                network.ResponseSender.write(ch, json);
                return false;
            } catch (Exception e) {
                return true; // канал закрыт — удаляем
            }
        });
    }

    public void notifyAll(List<String> logins, String message) {
        logins.forEach(login -> notify(login, message));
    }

    private String escape(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
