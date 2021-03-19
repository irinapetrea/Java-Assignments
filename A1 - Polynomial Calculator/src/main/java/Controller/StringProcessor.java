package Controller;

import Model.Monomial;
import Model.Polynomial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringProcessor {
    public static Polynomial toPolynomial(String input) {
        input = input.replaceAll("\\s", "");
        Polynomial result = new Polynomial();
        Monomial currentMonomial;
        String mon = "";
        Pattern pattern = Pattern.compile(
                "(?:([+-]?\\d+x\\^\\d+)|([+-]?\\d+x)|([+-]?\\d+)|([-+]?x\\^\\d+)|([+-]?x))");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            int sign = 1;
            if (matcher.group(1) != null) { //mx^n
                mon = matcher.group(1);
                sign = getSign(mon);
                mon = getUnsigned(mon);
            }
            if (matcher.group(2) != null) { //mx
                mon = matcher.group(2);
                sign = getSign(mon);
                mon = getUnsigned(mon);
                mon = mon + "^1";
            }
            if (matcher.group(3) != null) { //m
                mon = matcher.group(3);
                sign = getSign(mon);
                mon = getUnsigned(mon);
                mon =mon + "x^0";
            }
            if (matcher.group(4) != null) { //x^n
                mon = matcher.group(4);
                sign = getSign(mon);
                mon = getUnsigned(mon);
                mon = "1" + mon;
            }
            if (matcher.group(5) != null) { //x
                mon = matcher.group(5);
                sign = getSign(mon);
                mon = "1x^1";
            }
            result.addMonomial(toMonomial(mon, sign));
        }
        return result;
    }

    public static int getSign(String monomial) {
        int sign = 1;
        if (monomial.matches("-.*")) {
            sign = -1;
        }
        return sign;
    }

    public static String getUnsigned(String monomial) {
        if (monomial.matches("[-+].*")) {
            return monomial.split("[-+]")[1];
        }
        return monomial;
    }

    public static Monomial toMonomial(String mon, int sign) { //extract m and n from mx^n
        Pattern pattern = Pattern.compile("(?:(\\d+)x\\^(\\d+))");
        Matcher matcher = pattern.matcher(mon);
        int coefficient = 0, power = 0;
        while (matcher.find()) {
            coefficient = sign * Integer.parseInt(matcher.group(1));
            power = Integer.parseInt(matcher.group(2));
        }
        return new Monomial(coefficient, power);
    }
}
