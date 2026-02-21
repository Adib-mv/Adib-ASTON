package org.example;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class FactorialTestNG {

    private Factorial factorial;

    @BeforeMethod
    public void setUp() {
        factorial = new Factorial();
    }

    @Test
    public void testFactorialOfZero() {
        long result = factorial.calculateFactorial(0);
        assertEquals(result, 1, "0! должен быть равен 1");
    }

    @Test
    public void testFactorialOfOne() {
        long result = factorial.calculateFactorial(1);
        assertEquals(result, 1, "1! должен быть равен 1");
    }

    @Test
    public void testFactorialOfFive() {
        long result = factorial.calculateFactorial(5);
        assertEquals(result, 120, "5! должен быть равен 120");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialOfNegative() {
        factorial.calculateFactorial(-5);
    }

    @DataProvider(name = "factorialData")
    public Object[][] createFactorialData() {
        return new Object[][] {
                {0, 1},
                {1, 1},
                {2, 2},
                {3, 6},
                {4, 24},
                {5, 120}
        };
    }

    @Test(dataProvider = "factorialData")
    public void testFactorialWithDataProvider(int n, long expected) {
        long result = factorial.calculateFactorial(n);
        assertEquals(result, expected);
    }
}