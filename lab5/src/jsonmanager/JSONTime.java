package jsonmanager;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JSONTime implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    // Стандартный формат даты-времени
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        // Превращаем объект даты в строку
        return new JsonPrimitive(src.format(formatter));
    }

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Читаем строку из JSON и превращаем обратно в объект даты
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
