package org.example;

public class TriangleArea {

    public double calculateArea(double a, double b, double c) {

        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException();
        }


        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new IllegalArgumentException();
        }

        double semiPerimeter = (a + b + c) / 2;

        double area = Math.sqrt(semiPerimeter *
                (semiPerimeter - a) *
                (semiPerimeter - b) *
                (semiPerimeter - c));

        return Math.round(area * 100) / 100.0;
    }

    public static void main(String[] args) {
        TriangleArea triangle = new TriangleArea();

        double a = 3, b = 4, c = 5;
        double area = triangle.calculateArea(a, b, c);

        System.out.println("Площадь треугольника со сторонами " + a + ", " + b + ", " + c + " = " + area);
    }
}