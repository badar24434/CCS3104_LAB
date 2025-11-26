package com.csc3402.lab.ccs3104_lab.LAB4;

/**
 * MULTITHREADING EXAMPLES
 */
public class SimpleThreadingExamples {

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("MULTITHREADING EXAMPLES - PURE JAVA");
        System.out.println("========================================\n");

        // Example 1: WITHOUT multithreading
        System.out.println("EXAMPLE 1: WITHOUT MULTITHREADING");
        System.out.println("-----------------------------------");
        example1_WithoutThreading();

        System.out.println("\n\n");

        // Example 2: WITH multithreading
        System.out.println("EXAMPLE 2: WITH MULTITHREADING");
        System.out.println("-----------------------------------");
        example2_WithThreading();

        System.out.println("\n\n");

    }



    /**
     * EXAMPLE 1: WITHOUT MULTITHREADING
     * Everything runs sequentially on the main thread
     */
    public static void example1_WithoutThreading() {
        System.out.println("Starting Task A...");
        doTaskA(); // This blocks - must finish before moving on

        System.out.println("Starting Task B...");
        doTaskB(); // This blocks - must finish before moving on

        System.out.println("All tasks completed!");

        // OBSERVE: Tasks run one after another
        // Total time: 3 seconds + 3 seconds = 6 seconds
    }


    /**
     * EXAMPLE 2: WITH MULTITHREADING
     * Tasks run simultaneously on separate threads
     */
    public static void example2_WithThreading() {
        System.out.println("Starting Task A in background...");

        // Create thread for Task A
        Thread threadA = new Thread(() -> {
            doTaskA(); // Runs in background
        });

        System.out.println("Starting Task B in background...");

        // Create thread for Task B
        Thread threadB = new Thread(() -> {
            doTaskB(); // Runs in background
        });

        // Start both threads
        threadA.start(); // Task A starts running
        threadB.start(); // Task B starts running (at the same time!)

        System.out.println("Main thread is free! Both tasks running in background.");

        // Wait for both threads to complete
        try {
            threadA.join(); // Wait for Task A to finish
            threadB.join(); // Wait for Task B to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All tasks completed!");

        // OBSERVE: Tasks run at the same time
        // Total time: max(3 seconds, 3 seconds) = 3 seconds
    }

}


