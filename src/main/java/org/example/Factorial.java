package org.example;

public class Factorial {

    public long calculateFactorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал отрицательного числа не существует!");
        }

        if (n == 0) {
            return 1;
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result = result * i;
        }

        return result;
    }

    public static void main(String[] args) {
        Factorial fact = new Factorial();

        int number = 11;
        long result = fact.calculateFactorial(number);

        System.out.println(number + "! = " + result);
    }
}