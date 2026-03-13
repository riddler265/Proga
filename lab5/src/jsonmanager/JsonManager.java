package jsonmanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class JsonManager {

    //fields
    private File file;
    private Scanner scanner;

    //constructor
    public JsonManager(String file) {
        this.file = new File(file);
        try {
            this.scanner = new Scanner(this.file);
        } catch (FileNotFoundException e) {
            System.out.println("\nFile " + file + " was not found.");
            System.exit(1);
        }
    }
}
