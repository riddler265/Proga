package exceptions;

import localization.AnnounceManager;

import java.io.File;

public class RecursionException extends RuntimeException {
    public RecursionException(File file) {
        super(AnnounceManager.getInstance().format("recursion.e", file.getAbsolutePath()));
    }
}
