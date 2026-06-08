package commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.Response;

/**
 * Интерфейс команды сервера.
 * login — логин авторизованного пользователя (null для публичных команд).
 */
public interface Command {
    Response execute(JsonObject jProduct, JsonObject jCoordinates,
                     JsonObject jPerson, JsonElement parameter, String login);
}
