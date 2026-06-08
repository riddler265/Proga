package util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Хранит данные текущей сессии пользователя.
 * injectAuth безопасно добавляет поле auth через Gson (не строковой манипуляцией).
 */
public class Session {

    private static final Session INSTANCE = new Session();
    public static Session getInstance() { return INSTANCE; }

    private String login;
    private String password;
    private boolean authenticated = false;

    public void set(String login, String password) {
        this.login    = login;
        this.password = password;
        this.authenticated = true;
    }

    public void clear() {
        this.login    = null;
        this.password = null;
        this.authenticated = false;
    }

    public boolean isAuthenticated() { return authenticated; }
    public String getLogin()         { return login; }
    public String getPassword()      { return password; }

    /**
     * Добавляет поле "auth" в JSON-строку запроса через Gson — надёжно при любой вложенности.
     * Если пользователь не аутентифицирован — возвращает строку без изменений.
     */
    public String injectAuth(String json) {
        if (!authenticated || json == null) return json;
        try {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            JsonObject auth = new JsonObject();
            auth.addProperty("login",    login);
            auth.addProperty("password", password);
            obj.add("auth", auth);
            return obj.toString();
        } catch (Exception e) {
            // Если JSON невалидный — возвращаем как есть
            return json;
        }
    }
}
