package exceptions;

import java.io.File;

public class RecursionException extends RuntimeException {
    public RecursionException(File file) {
        super("Обнаружена рекурсия. Исполнение файла " + file + " будет прервано.");
    }
}
