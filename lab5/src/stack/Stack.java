package stack;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Stack {
    private Set<File> stack = new HashSet<>();

    public boolean add(File file) throws IOException {
        return stack.add(file.getCanonicalFile());
    }

    public void clear() {
        stack.clear();
    }
}
