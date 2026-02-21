package org.example;

public class NumberComparator {

    public String compare(int a, int b) {
        if (a > b) {
            return a + " больше, чем " + b;
        } else if (a < b) {
            return a + " меньше, чем " + b;
        } else {
            return a + " равно " + b;
        }
    }

    public int getMax(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    public int getMin(int a, int b) {
        if (a < b) {
            return a;
        } else {
            return b;
        }
    }

    public static void main(String[] args) {
        NumberComparator comparator = new NumberComparator();

        int a = 7, b = 12;

        System.out.println(comparator.compare(a, b));
        System.out.println("Большее число: " + comparator.getMax(a, b));
        System.out.println("Меньшее число: " + comparator.getMin(a, b));
    }
}