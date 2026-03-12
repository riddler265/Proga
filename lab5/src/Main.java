import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Incorrect input");
            System.exit(1);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.nextLine());
    }
}