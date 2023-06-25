import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    private static final Map<String, Integer> romanNumerals = new HashMap<>();

    static {
        romanNumerals.put("I", 1);
        romanNumerals.put("II", 2);
        romanNumerals.put("III", 3);
        romanNumerals.put("IV", 4);
        romanNumerals.put("V", 5);
        romanNumerals.put("VI", 6);
        romanNumerals.put("VII", 7);
        romanNumerals.put("VIII", 8);
        romanNumerals.put("IX", 9);
        romanNumerals.put("X", 10);
        romanNumerals.put("XX", 20);
        romanNumerals.put("XXX", 30);
        romanNumerals.put("XL", 40);
        romanNumerals.put("L", 50);
        romanNumerals.put("LX", 60);
        romanNumerals.put("LXX", 70);
        romanNumerals.put("LXXX", 80);
        romanNumerals.put("XC", 90);
        romanNumerals.put("C", 100);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result;
        try {
            result = calc(input);
        } catch (IllegalArgumentException e) {
            result = "Ошибка: " + e;
        }
        System.out.println(result);
    }

    public static String calc(String input) {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        boolean isRoman = isRomanNumeral(parts[0]) && isRomanNumeral(parts[2]);
        boolean isArabic = isArabicNumeral(parts[0]) && isArabicNumeral(parts[2]);

        if (!(isRoman || isArabic)) {
            throw new IllegalArgumentException("Используйте только арабские положительные или только римские числа до 10 (X)");
        }

        int num1, num2;
        if (isRoman) {
            num1 = romanToArabic(parts[0]);
            num2 = romanToArabic(parts[2]);
            return arabicToRoman(calculate(parts[1], num1, num2));
        } else {
            num1 = Integer.parseInt(parts[0]);
            num2 = Integer.parseInt(parts[2]);
            return String.valueOf(calculate(parts[1], num1, num2));
        }
    }

    private static int calculate(String operand, int num1, int num2) {
        int result;
        switch (operand) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "*" -> result = num1 * num2;
            case "/" -> {
                if (num2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль недопустимо");
                }
                result = num1 / num2;
            }
            default -> throw new IllegalArgumentException("Некорректная операция");
        }
        return result;
    }

    private static boolean isRomanNumeral(String input) {
        return romanNumerals.containsKey(input);
    }

    private static boolean isArabicNumeral(String input) {
        try {
            int num = Integer.parseInt(input);
            return num > 0 && num <= 10 && num % 1 == 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Некорректное число: " + number);
        }
        int length = String.valueOf(number).length();
        StringBuilder roman = new StringBuilder();
        if (length == 2) {
            String numbers = String.valueOf(number);
            int num1 = Integer.parseInt(String.valueOf(numbers.charAt(0))) * 10;
            int num2 = Integer.parseInt(String.valueOf(numbers.charAt(1)));
            roman.append(getRomanNumber(num1));
            roman.append(getRomanNumber(num2));
        } else {
            roman.append(getRomanNumber(number));
        }
        return roman.toString();
    }

    private static String getRomanNumber(int num) {
        for (Map.Entry<String, Integer> entry : romanNumerals.entrySet()) {
            if (entry.getValue() == num) {
                return entry.getKey();
            }
        }
        return "";
    }

    private static int romanToArabic(String input) {
        int num = romanNumerals.get(input);
        if (num > 0 && num <= 10 && num % 1 == 0) {
            return num;
        } else {
            throw new IllegalArgumentException("Некорректное число: " + input);
        }
    }
}