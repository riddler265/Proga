package subscription;

import model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Проверяет подписки против изменённого продукта.
 * Возвращает список сработавших подписок (с информацией о пользователе и условии).
 */
public class SubscriptionChecker {

    /** Результат срабатывания: какая подписка и какое фактическое значение. */
    public static class Match {
        public final Subscription subscription;
        public final float actualValue;

        public Match(Subscription subscription, float actualValue) {
            this.subscription = subscription;
            this.actualValue = actualValue;
        }
    }

    /**
     * Проверяет все подписки против продукта.
     * @return список сработавших подписок (каждому владельцу — о своём условии)
     */
    public static List<Match> check(List<Subscription> subscriptions, Product product) {
        List<Match> matches = new ArrayList<>();
        for (Subscription sub : subscriptions) {
            Float value = getValue(sub.getField(), product);
            if (value != null && compare(value, sub.getOperator(), sub.getThreshold())) {
                matches.add(new Match(sub, value));
            }
        }
        return matches;
    }

    private static Float getValue(String field, Product product) {
        return switch (field.toLowerCase()) {
            case "price"           -> product.getPrice();
            case "manufacturecost" -> product.getManufactureCost();
            case "coordx"          -> product.getCoordinates() != null
                                       ? (float) product.getCoordinates().getX() : null;
            case "coordy"          -> product.getCoordinates() != null
                                       ? (float) product.getCoordinates().getY() : null;
            case "height"          -> product.getOwner() != null
                                       ? product.getOwner().getHeight() : null;
            default                -> null;
        };
    }

    private static boolean compare(float value, String operator, float threshold) {
        return switch (operator) {
            case "<"  -> value < threshold;
            case ">"  -> value > threshold;
            case "<=" -> value <= threshold;
            case ">=" -> value >= threshold;
            case "==" -> Math.abs(value - threshold) < 0.0001f;
            default   -> false;
        };
    }
}
