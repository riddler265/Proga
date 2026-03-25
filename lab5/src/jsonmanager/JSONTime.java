package jsonmanager;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Класс, позволяющий библиотеке GSON работать с данными типа LocalDateTime.
 */
public class JSONTime implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    // Стандартный формат даты-времени
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Сериализует объект {@link LocalDateTime} в формат JSON.
     * <p>
     * Данный метод преобразует дату в строковое представление с использованием
     * заданного {@link #formatter}.
     *
     * @param src       объект даты, который необходимо сериализовать.
     * @param typeOfSrc тип исходного объекта.
     * @param context   контекст сериализации.
     * @return {@link JsonElement}, представляющий дату в виде строки.
     */
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        // Превращаем объект даты в строку
        return new JsonPrimitive(src.format(formatter));
    }

    /**
     * Десериализует JSON-элемент обратно в объект {@link LocalDateTime}.
     * <p>
     * Ожидает строку в формате, соответствующем {@link #formatter}.
     *
     * @param json    JSON-данные, которые нужно преобразовать.
     * @param typeOfT тип, в который происходит преобразование.
     * @param context контекст десериализации.
     * @return объект {@link LocalDateTime}, полученный из JSON.
     * @throws JsonParseException если строка в JSON имеет неверный формат
     * и не может быть распознана.
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // Читаем строку из JSON и превращаем обратно в объект даты
        return LocalDateTime.parse(json.getAsString(), formatter);
    }
}
