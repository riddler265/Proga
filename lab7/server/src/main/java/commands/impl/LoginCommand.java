package commands.impl;

import auth.UserManager;
import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.Response;

public class LoginCommand implements Command {
    private final UserManager userManager;
    public LoginCommand(UserManager userManager) { this.userManager = userManager; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson,
                            JsonElement param, String login) {
        if (param == null || !param.isJsonObject())
            return new Response(false, "response.error.no_credentials");

        JsonObject creds   = param.getAsJsonObject();
        String reqLogin    = getString(creds, "login");
        String password    = getString(creds, "password");

        if (reqLogin == null || password == null)
            return new Response(false, "response.error.no_credentials");

        if (!userManager.authenticate(reqLogin, password))
            return new Response(false, "response.error.wrong_credentials");

        return new Response(true, "response.login.success", new String[]{ reqLogin });
    }

    private String getString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : null;
    }
}
