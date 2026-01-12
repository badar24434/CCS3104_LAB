package com.csc3402.lab.ccs3104_lab.LAB7;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 Test Class for Calculator
 * 
 * This test class demonstrates:
 * - @BeforeEach: Setup method that runs before each test
 * - @Test: Marks a method as a test case
 * - @DisplayName: Provides a readable name for the test
 * - assertEquals: Verifies expected vs actual values
 * - assertThrows: Verifies that an exception is thrown
 * 
 * @author CSC3104 Lab
 */
@DisplayName("Calculator Unit Tests")
class CalculatorTest {

    // The Calculator instance to be tested
    private Calculator calculator;

    /**
     * This method runs BEFORE EACH test method.
     * It creates a fresh Calculator instance for each test,
     * ensuring tests are independent of each other.
     */
    @BeforeEach
    void setUp() {
        // Create a new Calculator instance before each test
        calculator = new Calculator();
        System.out.println("Setting up new Calculator instance for test...");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ADDITION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Addition: Two positive numbers should return correct sum")
    void testAdditionWithPositiveNumbers() {
        // Arrange
        int a = 5;
        int b = 3;
        int expectedResult = 8;
        
        // Act
        int actualResult = calculator.add(a, b);
        
        // Assert
        assertEquals(expectedResult, actualResult, 
            "5 + 3 should equal 8");
    }

    @Test
    @DisplayName("Addition: Positive and negative number should return correct sum")
    void testAdditionWithMixedNumbers() {
        // Test with negative number
        assertEquals(5, calculator.add(-2, 7), 
            "-2 + 7 should equal 5");
        
        // Test with both negative numbers
        assertEquals(-9, calculator.add(-4, -5), 
            "-4 + (-5) should equal -9");
    }

    @Test
    @DisplayName("Addition: Adding zero should return the same number")
    void testAdditionWithZero() {
        assertEquals(10, calculator.add(10, 0), 
            "10 + 0 should equal 10");
        assertEquals(0, calculator.add(0, 0), 
            "0 + 0 should equal 0");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // SUBTRACTION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Subtraction: Larger minus smaller should return positive result")
    void testSubtractionWithPositiveResult() {
        // Arrange
        int a = 10;
        int b = 4;
        int expectedResult = 6;
        
        // Act
        int actualResult = calculator.subtract(a, b);
        
        // Assert
        assertEquals(expectedResult, actualResult, 
            "10 - 4 should equal 6");
    }

    @Test
    @DisplayName("Subtraction: Smaller minus larger should return negative result")
    void testSubtractionWithNegativeResult() {
        assertEquals(-5, calculator.subtract(3, 8), 
            "3 - 8 should equal -5");
    }

    @Test
    @DisplayName("Subtraction: Same numbers should return zero")
    void testSubtractionWithSameNumbers() {
        assertEquals(0, calculator.subtract(5, 5), 
            "5 - 5 should equal 0");
        assertEquals(0, calculator.subtract(-3, -3), 
            "-3 - (-3) should equal 0");
    }

    @Test
    @DisplayName("Subtraction: Subtracting zero should return the same number")
    void testSubtractionWithZero() {
        assertEquals(15, calculator.subtract(15, 0), 
            "15 - 0 should equal 15");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // DIVISION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Division: Even division should return exact quotient")
    void testDivisionWithEvenResult() {
        // Arrange
        int a = 10;
        int b = 2;
        int expectedResult = 5;
        
        // Act
        int actualResult = calculator.divide(a, b);
        
        // Assert
        assertEquals(expectedResult, actualResult, 
            "10 / 2 should equal 5");
    }

    @Test
    @DisplayName("Division: Integer division should truncate decimal")
    void testDivisionWithIntegerTruncation() {
        // 7 / 3 = 2.33... but integer division returns 2
        assertEquals(2, calculator.divide(7, 3), 
            "7 / 3 should equal 2 (integer division truncates)");
        
        // 15 / 4 = 3.75 but integer division returns 3
        assertEquals(3, calculator.divide(15, 4), 
            "15 / 4 should equal 3 (integer division truncates)");
    }

    @Test
    @DisplayName("Division: Zero divided by any number should return zero")
    void testDivisionOfZero() {
        assertEquals(0, calculator.divide(0, 5), 
            "0 / 5 should equal 0");
        assertEquals(0, calculator.divide(0, 100), 
            "0 / 100 should equal 0");
    }

    @Test
    @DisplayName("Division: Dividing by one should return the same number")
    void testDivisionByOne() {
        assertEquals(42, calculator.divide(42, 1),
            "42 / 1 should equal 42");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // EXCEPTION TESTS
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Division by Zero: Should throw IllegalArgumentException")
    void testDivisionByZeroThrowsException() {
        // Using assertThrows to verify exception is thrown
        // This is the key test for exception handling!
        
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.divide(10, 0),  // Lambda expression - the code that should throw
            "Division by zero should throw IllegalArgumentException"
        );
        
        // Optionally verify the exception message
        assertEquals("Cannot divide by zero!", exception.getMessage(),
            "Exception message should be 'Cannot divide by zero!'");
    }

    @Test
    @DisplayName("Division by Zero: Should throw exception regardless of dividend")
    void testDivisionByZeroWithDifferentDividends() {
        // Test with positive dividend
        assertThrows(IllegalArgumentException.class,
            () -> calculator.divide(100, 0),
            "100 / 0 should throw exception");
        
        // Test with negative dividend
        assertThrows(IllegalArgumentException.class,
            () -> calculator.divide(-50, 0),
            "-50 / 0 should throw exception");
        
        // Test with zero dividend
        assertThrows(IllegalArgumentException.class,
            () -> calculator.divide(0, 0),
            "0 / 0 should throw exception");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BONUS: MULTIPLICATION TESTS (Additional Practice)
    // ═══════════════════════════════════════════════════════════════════════════

    @Test
    @DisplayName("Multiplication: Two positive numbers should return correct product")
    void testMultiplicationWithPositiveNumbers() {
        assertEquals(15, calculator.multiply(3, 5), 
            "3 * 5 should equal 15");
    }

    @Test
    @DisplayName("Multiplication: Any number times zero should return zero")
    void testMultiplicationWithZero() {
        assertEquals(0, calculator.multiply(100, 0), 
            "100 * 0 should equal 0");
        assertEquals(0, calculator.multiply(0, 50), 
            "0 * 50 should equal 0");
    }

    @Test
    @DisplayName("Multiplication: Negative numbers should follow sign rules")
    void testMultiplicationWithNegativeNumbers() {
        // Positive * Negative = Negative
        assertEquals(-12, calculator.multiply(3, -4), 
            "3 * (-4) should equal -12");
        
        // Negative * Negative = Positive
        assertEquals(20, calculator.multiply(-4, -5), 
            "(-4) * (-5) should equal 20");
    }
}
