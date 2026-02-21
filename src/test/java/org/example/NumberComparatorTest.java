package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class NumberComparatorTest {

    private NumberComparator comparator;

    @BeforeEach
    void setUp() {
        comparator = new NumberComparator();
    }

    @Test
    @DisplayName("Тест сравнения когда первое больше")
    void testFirstGreater() {
        String result = comparator.compare(10, 5);
        assertEquals("10 больше, чем 5", result);
    }

    @Test
    @DisplayName("Тест сравнения когда первое меньше")
    void testFirstLess() {
        String result = comparator.compare(3, 8);
        assertEquals("3 меньше, чем 8", result);
    }

    @Test
    @DisplayName("Тест сравнения равных чисел")
    void testEqualNumbers() {
        String result = comparator.compare(7, 7);
        assertEquals("7 равно 7", result);
    }

    @Test
    @DisplayName("Тест поиска максимума")
    void testGetMax() {
        assertEquals(20, comparator.getMax(15, 20));
        assertEquals(15, comparator.getMax(15, 10));
        assertEquals(5, comparator.getMax(5, 5));
    }

    @Test
    @DisplayName("Тест поиска минимума")
    void testGetMin() {
        assertEquals(15, comparator.getMin(15, 20));
        assertEquals(10, comparator.getMin(15, 10));
        assertEquals(5, comparator.getMin(5, 5));
    }
}