package managers;

import exceptions.IncorrectInputException;
import model.enums.Loc;

import java.text.MessageFormat;
import java.util.ResourceBundle;

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

    public void setLocale(Loc locale) throws IncorrectInputException {
        if (locale == null) throw new IncorrectInputException(Loc.getLocalesInfo());
        else bundle = ResourceBundle.getBundle("lang.messages", locale.getLocale());
    }

    public String print(String key, String ... params) {
        System.out.print(MessageFormat.format(bundle.getString(key), (Object[]) params));
        return MessageFormat.format(bundle.getString(key), (Object[]) params);
    }

    public String println(String key, String ... params) {
        System.out.println(MessageFormat.format(bundle.getString(key), (Object[]) params) + "\n");
        return MessageFormat.format(bundle.getString(key), (Object[]) params);
    }

    /**
     * <strong>C</strong>ast <strong>T</strong>o <strong>C</strong>urrent <strong>L</strong>anguage - локализует, не выводя в консоль.
     * @param key название переменной.
     * @param params параметры для подстановки.
     * @return локализованное сообщение.
     */
    public String cTCL(String key, String ... params) {
        return  MessageFormat.format(bundle.getString(key), (Object[]) params);
    }

}
