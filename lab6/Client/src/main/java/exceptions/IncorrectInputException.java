package exceptions;

import localization.AnnounceManager;

import java.util.MissingResourceException;

public class IncorrectInputException extends RuntimeException {
    public IncorrectInputException(String conditions) {
        super(conditions);
    }

    private static String format(String cond) {
        if (cond == null || cond.isBlank()) {
            return AnnounceManager.getInstance().format("incorrectInput.e.no.conditions");
        }

        // Если запятой нет — это одиночный параметр
        if (!cond.contains(",")) {
            try {
                return AnnounceManager.getInstance().format("incorrectInput.e.one.conditions", AnnounceManager.getInstance().format(cond.trim()));
            } catch (MissingResourceException e) {
                return AnnounceManager.getInstance().format("incorrectInput.e.one.conditions", cond.trim());
            }
        }

        String list;

        // Если запятые есть — строим список с переносами
        try {
            list = java.util.Arrays.stream(cond.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    // Пропускаем каждое слово через твой метод локализации
                    .map(s -> AnnounceManager.getInstance().format(s))
                    .collect(java.util.stream.Collectors.joining("\n\t", "\n\t", ""));
        } catch (MissingResourceException e) {
            list = java.util.Arrays.stream(cond.split(","))

                    .map(String::trim)

                    .filter(s -> !s.isEmpty())

                    .collect(java.util.stream.Collectors.joining("\n\t", "\n\t", ""));
        }

        return AnnounceManager.getInstance().format("incorrectInput.e.many.conditions", list);
    }
}
