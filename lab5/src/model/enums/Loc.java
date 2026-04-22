package model.enums;

import java.util.Locale;

public enum Loc {
    RUSSIAN(new Locale("ru")),
    ENGLISH(Locale.ENGLISH),
    GERMAN(Locale.GERMAN);

    private final Locale locale;

    Loc(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}