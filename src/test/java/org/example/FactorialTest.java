package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class FactorialTest {

    private Factorial calculator;

    @BeforeEach
    void setUp() {
        calculator = new Factorial();
    }

    @Test
    @DisplayName("Тест факториала 0")
    void testFactorialOfZero() {
        System.out.println("Тестируем факториал 0");
        long result = calculator.factorial(0);
        assertEquals(1, result, "0! должно быть равно 1");
    }

    @Test
    @DisplayName("Тест факториала 1")
    void testFactorialOfOne() {
        System.out.println("Тестируем факториал 1");
        long result = calculator.factorial(1);
        assertEquals(1, result, "1! должно быть равно 1");
    }

    @Test
    @DisplayName("Тест факториала 5")
    void testFactorialOfFive() {
        System.out.println("Тестируем факториал 5");
        long result = calculator.factorial(5);
        assertEquals(120, result, "5! должно быть равно 120");
    }

    @Test
    @DisplayName("Тест факториала 10")
    void testFactorialOfTen() {
        System.out.println("Тестируем факториал 10");
        long result = calculator.factorial(10);
        assertEquals(3628800L, result, "10! должно быть равно 3628800");
    }

    @Test
    @DisplayName("Тест отрицательного числа (должно выбрасывать исключение)")
    void testFactorialOfNegative() {
        System.out.println("Тестируем факториал отрицательного числа");
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.factorial(-5);
        }, "Факториал отрицательного числа должен вызывать исключение");
    }

    @Test
    @DisplayName("Тест факториала 20 (максимальное значение для long)")
    void testFactorialOfTwenty() {
        System.out.println("Тестируем факториал 20");
        long result = calculator.factorial(20);
        assertEquals(2432902008176640000L, result, "20! должно быть равно 2432902008176640000");
    }

    @Test
    @DisplayName("Тест факториала 21 (переполнение long)")
    void testFactorialOfTwentyOne() {
        System.out.println("Тестируем факториал 21 (должен быть отрицательный из-за переполнения)");
        long result = calculator.factorial(21);
        assertTrue(result < 0, "21! должен вызвать переполнение и стать отрицательным");
    }
}