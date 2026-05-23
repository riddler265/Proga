package json;

import com.google.gson.Gson;

public class JsonManager {

    private static final Gson GSON = new Gson();

    /**
     * Парсит JSON-строку и превращает её в объект ответа.
     * Теперь метод возвращает json.ServerResponse, а не void.
     */
    public static ServerResponse parseResponse(String jsonString) {
        try {
            return GSON.fromJson(jsonString, ServerResponse.class);
        } catch (Exception e) {
            System.err.println("Ошибка при парсинге JSON: " + e.getMessage());
            return null; // Или выбросить кастомное исключение
        }
    }

    public static String parseRequest(ClientRequest clientRequest) {
        return GSON.toJson(clientRequest);
    }

}
