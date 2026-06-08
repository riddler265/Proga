package subscription;

/**
 * Подписка пользователя.
 * Условие: product.<field> <operator> threshold
 * Пример: price < 100.0
 */
public class Subscription {
    private final int id;
    private final String userLogin;
    private final String field;      // "price" | "manufactureCost"
    private final String operator;   // "<" | ">" | "<=" | ">=" | "=="
    private final float threshold;

    public Subscription(int id, String userLogin, String field, String operator, float threshold) {
        this.id = id;
        this.userLogin = userLogin;
        this.field = field;
        this.operator = operator;
        this.threshold = threshold;
    }

    public int getId()          { return id; }
    public String getUserLogin(){ return userLogin; }
    public String getField()    { return field; }
    public String getOperator() { return operator; }
    public float getThreshold() { return threshold; }

    @Override
    public String toString() {
        return "Subscription#" + id + "[" + userLogin + ": " + field + " " + operator + " " + threshold + "]";
    }
}
