package exceptions;

import model.Coordinates;
import model.Person;
import model.Product;

/**
 * <p>
 * Исключения для некорректных данных для классов:
 * {@link Product},
 * {@link Person},
 * {@link Coordinates}.
 */
public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String conditions) {
        super(format(conditions));
    }

    private static String format(String cond) {
        if (cond == null || cond.isBlank()) {
            return "Некорректный ввод. Попробуйте еще раз: ";
        }

        // Если запятой нет — это одиночный параметр
        if (!cond.contains(",")) {
            return "\nДопустимое значение параметра: " + cond.trim() + ".\nПопробуйте еще раз: ";
        }

        // Если запятые есть — строим список с переносами
        String list = java.util.Arrays.stream(cond.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(java.util.stream.Collectors.joining("\n\t", "\n\t", ""));

        return "\nДопустимые значения параметра: " + list + "\nПопробуйте еще раз: ";
    }
}
