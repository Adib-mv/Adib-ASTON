package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TriangleAreaTest {

    private TriangleArea triangleArea;

    @BeforeEach
    void setUp() {
        triangleArea = new TriangleArea();
    }

    @Test
    @DisplayName("Тест прямоугольного треугольника 3-4-5")
    void testRightTriangle() {
        double area = triangleArea.calculateArea(3, 4, 5);
        assertEquals(6.0, area, 0.001, "Площадь должна быть 6.0");
    }

    @Test
    @DisplayName("Тест равностороннего треугольника")
    void testEquilateralTriangle() {
        double area = triangleArea.calculateArea(5, 5, 5);
        assertEquals(10.83, area, 0.01, "Площадь должна быть примерно 10.83");
    }

    @Test
    @DisplayName("Тест равнобедренного треугольника")
    void testIsoscelesTriangle() {
        double area = triangleArea.calculateArea(5, 5, 6);
        assertEquals(12.0, area, 0.001, "Площадь должна быть 12.0");
    }

    @Test
    @DisplayName("Тест с нулевой стороной")
    void testZeroSide() {
        assertThrows(IllegalArgumentException.class,
                () -> triangleArea.calculateArea(0, 4, 5),
                "Сторона не может быть нулевой");
    }

    @Test
    @DisplayName("Тест несуществующего треугольника")
    void testInvalidTriangle() {
        assertThrows(IllegalArgumentException.class,
                () -> triangleArea.calculateArea(1, 1, 3),
                "Треугольник 1-1-3 не существует");
    }
}