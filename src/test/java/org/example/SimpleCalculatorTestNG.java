package org.example;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class SimpleCalculatorTestNG {

    private SimpleCalculator calculator;

    @BeforeMethod
    public void setUp() {
        calculator = new SimpleCalculator();
    }

    @Test
    public void testAdd() {
        assertEquals(calculator.add(2, 3), 5);
        assertEquals(calculator.add(-2, 1), -1);
    }

    @Test
    public void testSubtract() {
        assertEquals(calculator.subtract(5, 2), 3);
        assertEquals(calculator.subtract(2, 5), -3);
    }

    @Test
    public void testMultiply() {
        assertEquals(calculator.multiply(3, 5), 15);
        assertEquals(calculator.multiply(-3, 5), -15);
    }

    @Test
    public void testDivide() {
        assertEquals(calculator.divide(5, 2), 2.5, 0.001);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        calculator.divide(10, 0);
    }
}