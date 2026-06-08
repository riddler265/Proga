package util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumbParser {

    public static int parseInt(String input) throws ArithmeticException, NumberFormatException {
        BigDecimal bd = new BigDecimal(input.replace(',', '.'));
        return bd.intValueExact();
    }

    public static float parseFloat(String input) throws ArithmeticException, NumberFormatException {
        BigDecimal bd = new BigDecimal(input.replace(',', '.'));
        return bd.setScale(5, RoundingMode.HALF_UP).floatValue();
    }

    public static double parseDouble(String input) throws ArithmeticException, NumberFormatException {
        BigDecimal bd = new BigDecimal(input.replace(',', '.'));
        return bd.setScale(5, RoundingMode.HALF_UP).doubleValue();
    }
}
