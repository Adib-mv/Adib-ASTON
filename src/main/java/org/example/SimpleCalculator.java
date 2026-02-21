package org.example;

public class SimpleCalculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }


    public int multiply(int a, int b) {
        return a * b;
    }


    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException();
        }
        return (double) a / b;
    }

    public static void main(String[] args) {
        SimpleCalculator calc = new SimpleCalculator();

        int a = 10, b = 5;

        System.out.println(a + " + " + b + " = " + calc.add(a, b));
        System.out.println(a + " - " + b + " = " + calc.subtract(a, b));
        System.out.println(a + " * " + b + " = " + calc.multiply(a, b));
        System.out.println(a + " / " + b + " = " + calc.divide(a, b));
    }
}