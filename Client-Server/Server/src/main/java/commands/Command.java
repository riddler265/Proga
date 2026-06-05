package commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.Response;


/**
 * Интерфейс команды.
 * Каждая команда получает распаршенные части запроса и возвращает Response.
 */
public interface Command {

    /**
     * @param jProduct     объект product из JSON (может быть null)
     * @param jCoordinates объект coordinates из product (может быть null)
     * @param jPerson      объект owner из product (может быть null)
     * @param parameter    числовой или строковый параметр (может быть null)
     */
    Response execute(JsonObject jProduct, JsonObject jCoordinates,
                     JsonObject jPerson, JsonElement parameter);
}
