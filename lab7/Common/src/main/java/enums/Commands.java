package enums;

public enum Commands {
    HELP("help"),
    INFO("info"),
    SHOW("show"),
    ADD("add"),
    UPDATE_ID("update_id"),
    REMOVE_BY_ID("remove_by_id"),
    CLEAR("clear"),
    SAVE("save"),
    EXECUTE_SCRIPT("execute_script"),
    EXIT("exit"),
    ADD_IF_MIN("add_if_min"),
    REMOVE_GREATER("remove_greater"),
    HISTORY("history"),
    REMOVE_ALL_BY_PRICE("remove_all_by_price"),
    FILTER_LESS_THAN_MANUFACTURE_COST("filter_less_than_manufacture_cost"),
    FILTER_GREATER_THAN_MANUFACTURE_COST("filter_greater_than_manufacture_cost"),
    // Авторизация
    REGISTER("register"),
    LOGIN("login"),
    // Подписки
    SUBSCRIBE("subscribe"),
    UNSUBSCRIBE("unsubscribe"),
    LIST_SUBSCRIPTIONS("list_subscriptions");

    private final String name;

    Commands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
