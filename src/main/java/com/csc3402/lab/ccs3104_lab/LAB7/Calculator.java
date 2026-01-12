package com.csc3402.lab.ccs3104_lab.LAB7;

/**
 * Calculator Class for JUnit Testing Demo
 * 
 * This is a simple calculator class that demonstrates basic arithmetic operations.
 * It is designed to be used with JUnit 5 for unit testing purposes.
 * 
 * Key Features:
 * - Addition of two integers
 * - Subtraction of two integers
 * - Division of two integers with exception handling
 * 
 * @author CSC3104 Lab
 */
public class Calculator {

    /**
     * Adds two integers and returns the result.
     * 
     * @param a the first integer
     * @param b the second integer
     * @return the sum of a and b
     * 
     * Example:
     *   add(5, 3) returns 8
     *   add(-2, 7) returns 5
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * Subtracts the second integer from the first and returns the result.
     * 
     * @param a the first integer (minuend)
     * @param b the second integer (subtrahend)
     * @return the difference of a and b (a - b)
     * 
     * Example:
     *   subtract(10, 4) returns 6
     *   subtract(3, 8) returns -5
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * Divides the first integer by the second and returns the result.
     * 
     * @param a the dividend
     * @param b the divisor
     * @return the quotient of a divided by b (integer division)
     * @throws IllegalArgumentException if b is zero (division by zero)
     * 
     * Example:
     *   divide(10, 2) returns 5
     *   divide(7, 3) returns 2 (integer division)
     *   divide(5, 0) throws IllegalArgumentException
     */
    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }

    /**
     * Multiplies two integers and returns the result.
     * (Bonus method for additional testing practice)
     * 
     * @param a the first integer
     * @param b the second integer
     * @return the product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * Returns the remainder of division (modulo operation).
     * (Bonus method for additional testing practice)
     * 
     * @param a the dividend
     * @param b the divisor
     * @return the remainder of a divided by b
     * @throws IllegalArgumentException if b is zero
     */
    public int modulo(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot perform modulo by zero!");
        }
        return a % b;
    }

    /**
     * Main method to demonstrate calculator usage.
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║              CALCULATOR CLASS DEMONSTRATION                  ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Demonstrate addition
        System.out.println("Addition Examples:");
        System.out.println("  5 + 3 = " + calc.add(5, 3));
        System.out.println("  -2 + 7 = " + calc.add(-2, 7));
        System.out.println("  0 + 0 = " + calc.add(0, 0));
        System.out.println();
        
        // Demonstrate subtraction
        System.out.println("Subtraction Examples:");
        System.out.println("  10 - 4 = " + calc.subtract(10, 4));
        System.out.println("  3 - 8 = " + calc.subtract(3, 8));
        System.out.println("  5 - 5 = " + calc.subtract(5, 5));
        System.out.println();
        
        // Demonstrate division
        System.out.println("Division Examples:");
        System.out.println("  10 / 2 = " + calc.divide(10, 2));
        System.out.println("  7 / 3 = " + calc.divide(7, 3) + " (integer division)");
        System.out.println("  15 / 5 = " + calc.divide(15, 5));
        System.out.println();
        
        // Demonstrate division by zero exception
        System.out.println("Division by Zero Test:");
        try {
            calc.divide(10, 0);
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        
        System.out.println();
        System.out.println("════════════════════════════════════════════════════════════════");
        System.out.println("  Run the JUnit tests to verify all functionality!");
        System.out.println("════════════════════════════════════════════════════════════════");
    }
}
