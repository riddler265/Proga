package stack;

import exceptions.RecursionException;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Stack {
    private final Set<File> stack = new HashSet<>();

    public void add(File file) throws IOException, RecursionException {
        if (!(stack.add(file.getCanonicalFile()))) throw new RecursionException(file);
    }

    public boolean remove(File file) {
        return stack.remove(file);
    }

    public void clear() {
        stack.clear();
    }
}
