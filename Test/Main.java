import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        List<String> products = Arrays.asList("Juice apple", "Poor PiNeapple", "CucuM ber", "sd");

        List<String> result = products.stream()
            .filter(s -> s.length() > 2)
            .map(s -> s.replace(" ", "_"))
            .map(String::toLowerCase)
            .sorted()
            .collect(Collectors.toList());

        System.out.println(result);

    }
}