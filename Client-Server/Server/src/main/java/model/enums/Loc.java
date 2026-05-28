package model.enums;

import exceptions.IncorrectInputException;
import managers.AnnounceManager;
import util.NumbParser;

import java.util.Locale;

public enum Loc {
    RUSSIAN(1, "ru", new Locale("ru")),
    ENGLISH(2, "en", Locale.ENGLISH);

    private final int id;
    private final String code;
    private final Locale locale;

    Loc(int id, String code, Locale locale) {
        this.id = id;
        this.code = code;
        this.locale = locale;
    }

    public static String getLocalesInfo() {
        return AnnounceManager.getInstance().cTCL("locales.info");
    }

    public Locale getLocale() {
        return locale;
    }

    public static Loc getLocale(String input) throws IncorrectInputException {
        if (input == null || input.isBlank()) {
            throw new IncorrectInputException(getLocalesInfo());
        }

        String trimmedInput = input.trim();

        try {
            int id = NumbParser.parseInt(input);
            for (Loc unit : values()) {
                if (unit.id == id) return unit;
            }
        } catch (NumberFormatException | ArithmeticException ignored) {}

        for (Loc locale : values()) {
            if (locale.code.equalsIgnoreCase(trimmedInput)) {
                return locale;
            }
        }
        throw new IncorrectInputException(getLocalesInfo());
    }
}