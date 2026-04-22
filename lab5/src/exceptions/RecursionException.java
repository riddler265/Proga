package exceptions;

import managers.AnnounceManager;

import java.io.File;

public class RecursionException extends RuntimeException {
    public RecursionException(File file) {
        super(AnnounceManager.getInstance().cTCL("recursion.e", file.getAbsolutePath()));
    }
}
