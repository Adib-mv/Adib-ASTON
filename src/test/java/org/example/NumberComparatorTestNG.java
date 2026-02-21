package org.example;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class NumberComparatorTestNG {

    private NumberComparator comparator;

    @BeforeMethod
    public void setUp() {
        comparator = new NumberComparator();
    }

    @Test
    public void testCompare() {
        assertEquals(comparator.compare(10, 5), "10 больше, чем 5");
        assertEquals(comparator.compare(3, 8), "3 меньше, чем 8");
        assertEquals(comparator.compare(7, 7), "7 равно 7");
    }

    @Test
    public void testGetMax() {
        assertEquals(comparator.getMax(15, 20), 20);
        assertEquals(comparator.getMax(15, 10), 15);
    }

    @Test
    public void testGetMin() {
        assertEquals(comparator.getMin(15, 20), 15);
        assertEquals(comparator.getMin(15, 10), 10);
    }
}