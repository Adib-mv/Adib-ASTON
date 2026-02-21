package org.example;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class TriangleAreaTestNG {

    private TriangleArea triangleArea;

    @BeforeMethod
    public void setUp() {
        triangleArea = new TriangleArea();
    }

    @Test
    public void testRightTriangle() {
        double area = triangleArea.calculateArea(3, 4, 5);
        assertEquals(area, 6.0, 0.001, "Площадь должна быть 6.0");
    }

    @Test
    public void testEquilateralTriangle() {
        double area = triangleArea.calculateArea(5, 5, 5);
        assertEquals(area, 10.83, 0.01, "Площадь должна быть примерно 10.83");
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testZeroSide() {
        triangleArea.calculateArea(0, 4, 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testInvalidTriangle() {
        triangleArea.calculateArea(1, 1, 3);
    }
}