package ru.job4j.srp;

public class MoneyFormatter {
    public static final double USD = 60;

    public static final double EURO = 70;

    public static String inDollars(double roubles) {
        return String.format("%.2f", roubles / USD);
    }

    public static String inEuro(double roubles) {
        return String.format("%.2f", roubles / EURO);
    }
}