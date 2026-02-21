package org.example;

public class Factorial {

    public long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Факториал отрицательного числа не определен");
        }

        if (n == 0) {
            return 1;
        }

        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }


    public static void main(String[] args) {
        Factorial fact = new Factorial();

        System.out.println("5! = " + fact.factorial(5));
        System.out.println("10! = " + fact.factorial(10));
        System.out.println("0! = " + fact.factorial(0));
    }
}