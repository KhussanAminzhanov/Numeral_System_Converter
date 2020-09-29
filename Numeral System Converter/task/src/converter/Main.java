package converter;

import java.util.Scanner;

public class Main {

    public static String getInteger(String sourceNumber, int sourceRadix, int targetRadix) {

        int number = sourceRadix == 1 ? sourceNumber.length() : Integer.parseInt(sourceNumber, sourceRadix);
        if (targetRadix == 1) return "1".repeat(number);
        return Integer.toString(number, targetRadix);

    }

    public static String getDouble(String sourceNumber, int sourceRadix, int targetRadix) {

        String integerPart = getInteger(sourceNumber.split("\\.")[0], sourceRadix, targetRadix);
        String fractionalPart = getFractional(sourceNumber, sourceRadix, targetRadix);
        return integerPart + fractionalPart;

    }

    public static String getFractional(String sourceNumber, int sourceRadix, int targetRadix) {

        String[] parts = sourceNumber.split("\\.");
        if (parts.length == 1) return "";

        String number = parts[1];
        double fraction = convertFractionToDecimal(number, sourceRadix);

        return convertFractionalToTargetRadix(fraction, targetRadix);

    }

    public static double convertFractionToDecimal(String number, int sourceRadix) {

        double fraction = 0;
        for (int i = 0; i < number.length(); i++) {
            double digit;
            digit = Integer.parseInt(String.valueOf(number.charAt(i)), sourceRadix);
            fraction += digit / Math.pow(sourceRadix, i + 1);
        }
        return fraction;

    }

    public static String convertFractionalToTargetRadix(double fraction, int targetRadix) {

        StringBuilder number = new StringBuilder(".");
        double product;
        int integer;
        for (int i = 0; i < 5; i++) {
            product = fraction * targetRadix;
            integer = (int) (product - product % 1);
            number.append(Integer.toString(integer, targetRadix));
            fraction = product - integer;
        }
        return number.toString();

    }

    public static boolean contains(String sourceRadix, String str) {

        String[] chars = str.split("");
        for (int i = 0; i < str.length(); i++) {
            if (sourceRadix.contains(chars[i])) {
                return true;
            }
        }
        return false;

    }

    public static int findIndex(String str, String ch) {

        String[] chars = str.split("");
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].equals(ch)) {
                return i;
            }
        }
        return -1;
    }

    public static int getRadix(String radix) {

        if (!contains(radix, "-.abcdefghijklmnopqrstuvwxyz")) {
            int n = Integer.parseInt(radix);
            return n > 0 && n < 37 ? n : -1;
        }
        return -1;

    }

    public static String getSourceNumber(String sourceNumber, int sourceRadix) {

        if (sourceRadix == 1) {
            return "1".repeat(sourceNumber.length()).equals(sourceNumber)
                    ? sourceNumber : "";
        }

        String digits = "0123456789abcdefghijklmnopqrstuvwxyz";
        String[] chars = sourceNumber.split("");
        for (String aChar : chars) {
            int index = findIndex(digits, aChar);
            if ((index == -1 || index >= sourceRadix) && !aChar.equals(".") && !aChar.equals("-")) {
                return "";
            }
        }
        return sourceNumber;

    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String sourceNumber;
        int sourceRadix, targetRadix;

        sourceRadix = getRadix(scanner.next());
        if (sourceRadix == -1) {
            System.out.println("error: invalid number for sourceRadix");
            return;
        }

        sourceNumber = getSourceNumber(scanner.next(), sourceRadix);
        if (sourceNumber.equals("")) {
            System.out.println("error: invalid number for sourceNumber");
            return;
        }

        targetRadix = getRadix(scanner.next());
        if (targetRadix == -1) {
            System.out.println("error: invalid number for targetRadix");
            return;
        }

        if (sourceNumber.contains(".")) {
            System.out.println(getDouble(sourceNumber, sourceRadix, targetRadix));
        } else {
            System.out.println(getInteger(sourceNumber, sourceRadix, targetRadix));
        }

    }
}