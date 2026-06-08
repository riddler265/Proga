package network;

import json.JsonManager;
import model.Product;

import java.util.List;

/**
 * Ответ сервера клиенту.
 *
 * Поля для локализации:
 *   messageKey  — ключ из messages.properties (например "response.add.success")
 *   messageArgs — аргументы для подстановки в MessageFormat (например ["42"])
 *
 * Если messageKey не задан, клиент выводит поле message напрямую (legacy/raw).
 */
public class Response {

    private boolean success;

    /** Локализуемый ключ (основной способ передачи сообщений). */
    private String messageKey;

    /** Аргументы для MessageFormat.format(bundle.getString(messageKey), messageArgs). */
    private String[] messageArgs;

    /** Устаревшее сырое сообщение — используется только если messageKey == null. */
    private String message;

    /** Список продуктов (для show, filter-команд). */
    private List<Product> products;

    public Response() {}

    /** Конструктор с ключом локализации и параметрами. */
    public Response(boolean success, String messageKey, String[] messageArgs) {
        this.success = success;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
    }

    /** Конструктор с ключом без параметров. */
    public Response(boolean success, String messageKey) {
        this.success = success;
        this.messageKey = messageKey;
        this.messageArgs = new String[0];
    }

    /** Конструктор для ответов со списком продуктов (show, filter). */
    public Response(boolean success, String messageKey, String[] messageArgs, List<Product> products) {
        this.success = success;
        this.messageKey = messageKey;
        this.messageArgs = messageArgs;
        this.products = products;
    }

    /** Конструктор для ответов со списком продуктов без доп. аргументов. */
    public Response(boolean success, String messageKey, List<Product> products) {
        this.success = success;
        this.messageKey = messageKey;
        this.messageArgs = new String[0];
        this.products = products;
    }

    public String toJson() {
        return JsonManager.GSON.toJson(this);
    }

    public boolean isSuccess()      { return success; }
    public String getMessageKey()   { return messageKey; }
    public String[] getMessageArgs(){ return messageArgs; }
    public String getMessage()      { return message; }
    public List<Product> getProducts() { return products; }
}
