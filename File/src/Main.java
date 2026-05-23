import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File("hello/test.txt");
        if (file.exists()) {
            System.out.println(file.getAbsoluteFile());
        } else {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}