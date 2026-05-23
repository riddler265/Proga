package localization;

import exceptions.IncorrectInputException;
import localization.Loc;
import json.ServerResponse;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class AnnounceManager {

    private static AnnounceManager instance;
    private ResourceBundle bundle;

    private AnnounceManager() {
        this.bundle = ResourceBundle.getBundle("lang.messages", Loc.RUSSIAN.getLocale());
    }

    public static AnnounceManager getInstance() {
        if (instance == null) {
            instance = new AnnounceManager();
        }
        return instance;
    }
    
    public String format(String key, String ... parameters) {
        return MessageFormat.format(bundle.getString(key), (Object[]) parameters);
    }

    public void setLocale(Loc locale) throws IncorrectInputException {
        if (locale == null) throw new IncorrectInputException(Loc.getLocalesInfo());
        else bundle = ResourceBundle.getBundle("lang.messages", locale.getLocale());
    }

    public String print(String key, String ... parameters) {
        System.out.print(format(key, parameters));
        return format(key, parameters);
    }

    public String println(String key, String ... parameters) {
        System.out.println(format(key, parameters) + "\n");
        return format(key, parameters);
    }
}
