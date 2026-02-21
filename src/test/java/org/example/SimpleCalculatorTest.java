package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SimpleCalculatorTest {

    private SimpleCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new SimpleCalculator();
    }

    @Test
    @DisplayName("Тест сложения")
    void testAdd() {
        assertEquals(5, calculator.add(2, 3), "2 + 3 должно быть 5");
        assertEquals(-1, calculator.add(-2, 1), "-2 + 1 должно быть -1");
        assertEquals(0, calculator.add(0, 0), "0 + 0 должно быть 0");
    }

    @Test
    @DisplayName("Тест вычитания")
    void testSubtract() {
        assertEquals(3, calculator.subtract(5, 2), "5 - 2 должно быть 3");
        assertEquals(-3, calculator.subtract(2, 5), "2 - 5 должно быть -3");
        assertEquals(0, calculator.subtract(5, 5), "5 - 5 должно быть 0");
    }

    @Test
    @DisplayName("Тест умножения")
    void testMultiply() {
        assertEquals(15, calculator.multiply(3, 5), "3 * 5 должно быть 15");
        assertEquals(-15, calculator.multiply(-3, 5), "-3 * 5 должно быть -15");
        assertEquals(0, calculator.multiply(0, 5), "0 * 5 должно быть 0");
    }

    @Test
    @DisplayName("Тест деления")
    void testDivide() {
        assertEquals(2.5, calculator.divide(5, 2), 0.001, "5 / 2 должно быть 2.5");
        assertEquals(0, calculator.divide(0, 5), 0.001, "0 / 5 должно быть 0");
    }

    @Test
    @DisplayName("Тест деления на ноль")
    void testDivideByZero() {
        assertThrows(ArithmeticException.class,
                () -> calculator.divide(10, 0),
                "Деление на ноль должно вызывать исключение");
    }
}