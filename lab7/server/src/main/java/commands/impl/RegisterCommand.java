package commands.impl;

import auth.UserManager;
import commands.Command;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import network.Response;

public class RegisterCommand implements Command {
    private final UserManager userManager;
    public RegisterCommand(UserManager userManager) { this.userManager = userManager; }

    @Override
    public Response execute(JsonObject jProduct, JsonObject jCoords, JsonObject jPerson,
                            JsonElement param, String login) {
        if (param == null || !param.isJsonObject())
            return new Response(false, "response.error.no_credentials");

        JsonObject creds   = param.getAsJsonObject();
        String newLogin    = getString(creds, "login");
        String password    = getString(creds, "password");

        if (newLogin == null || password == null)
            return new Response(false, "response.error.no_credentials");

        boolean ok = userManager.register(newLogin, password);
        if (!ok)
            return new Response(false, "response.register.login_taken", new String[]{ newLogin });

        return new Response(true, "response.register.success", new String[]{ newLogin });
    }

    private String getString(JsonObject obj, String key) {
        return obj.has(key) && !obj.get(key).isJsonNull() ? obj.get(key).getAsString() : null;
    }
}
