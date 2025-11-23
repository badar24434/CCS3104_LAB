package com.csc3402.lab.ccs3104_lab.LAB4;

/**
 * SIMPLEST POSSIBLE MULTITHREADING EXAMPLES
 * Pure Java - No JavaFX - Just Console Output
 * Perfect for teaching basic concepts
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

        // Example 3: Multiple threads working together
        System.out.println("EXAMPLE 3: MULTIPLE THREADS");
        System.out.println("-----------------------------------");
        example3_MultipleThreads();
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


    /**
     * EXAMPLE 3: MULTIPLE THREADS WITH DIFFERENT TASKS
     * Shows how threads can do different work simultaneously
     */
    public static void example3_MultipleThreads() {
        // Thread 1: Count up
        Thread counter = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("  [Counter] Counting: " + i);
                sleep(500);
            }
        });

        // Thread 2: Print letters
        Thread printer = new Thread(() -> {
            char[] letters = {'A', 'B', 'C', 'D', 'E'};
            for (char letter : letters) {
                System.out.println("    [Printer] Letter: " + letter);
                sleep(700);
            }
        });

        // Thread 3: Calculate
        Thread calculator = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                int result = i * i;
                System.out.println("      [Calculator] " + i + " squared = " + result);
                sleep(600);
            }
        });

        // Start all threads at once
        counter.start();
        printer.start();
        calculator.start();

        System.out.println("[Main] All threads started! Watch them work together!\n");

        // Wait for all to complete
        try {
            counter.join();
            printer.join();
            calculator.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n[Main] All threads finished!");

        // OBSERVE: All three tasks run at the same time
        // Output is interleaved - proving they run simultaneously
    }


    // ========== HELPER METHODS ==========

    /**
     * Simulates Task A - takes 3 seconds
     */
    private static void doTaskA() {
        String threadName = Thread.currentThread().getName();
        System.out.println("  [Task A] Running on thread: " + threadName);

        for (int i = 1; i <= 3; i++) {
            System.out.println("  [Task A] Working... " + i + "/3 seconds");
            sleep(1000); // Sleep 1 second
        }

        System.out.println("  [Task A] COMPLETED!");
    }

    /**
     * Simulates Task B - takes 3 seconds
     */
    private static void doTaskB() {
        String threadName = Thread.currentThread().getName();
        System.out.println("    [Task B] Running on thread: " + threadName);

        for (int i = 1; i <= 3; i++) {
            System.out.println("    [Task B] Processing... " + i + "/3 seconds");
            sleep(1000); // Sleep 1 second
        }

        System.out.println("    [Task B] COMPLETED!");
    }

    /**
     * Helper method to sleep without try-catch clutter
     */
    private static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


/*
 * ============================================================================
 * EXPECTED OUTPUT ANALYSIS FOR STUDENTS
 * ============================================================================
 *
 * EXAMPLE 1 OUTPUT (Without Threading):
 * --------------------------------------
 * Starting Task A...
 *   [Task A] Running on thread: main
 *   [Task A] Working... 1/3 seconds
 *   [Task A] Working... 2/3 seconds
 *   [Task A] Working... 3/3 seconds
 *   [Task A] COMPLETED!
 * Starting Task B...
 *   [Task B] Running on thread: main
 *   [Task B] Processing... 1/3 seconds
 *   [Task B] Processing... 2/3 seconds
 *   [Task B] Processing... 3/3 seconds
 *   [Task B] COMPLETED!
 * All tasks completed!
 *
 * NOTICE:
 * - Both run on "main" thread
 * - Task B starts AFTER Task A finishes
 * - Total time: 6 seconds
 *
 *
 * EXAMPLE 2 OUTPUT (With Threading):
 * -----------------------------------
 * Starting Task A in background...
 * Starting Task B in background...
 * Main thread is free! Both tasks running in background.
 *   [Task A] Running on thread: Thread-0
 *     [Task B] Running on thread: Thread-1
 *   [Task A] Working... 1/3 seconds
 *     [Task B] Processing... 1/3 seconds
 *   [Task A] Working... 2/3 seconds
 *     [Task B] Processing... 2/3 seconds
 *   [Task A] Working... 3/3 seconds
 *     [Task B] Processing... 3/3 seconds
 *   [Task A] COMPLETED!
 *     [Task B] COMPLETED!
 * All tasks completed!
 *
 * NOTICE:
 * - Task A runs on "Thread-0"
 * - Task B runs on "Thread-1"
 * - Both tasks print at the SAME TIME (interleaved)
 * - Total time: 3 seconds (half the time!)
 *
 *
 * EXAMPLE 3 OUTPUT (Multiple Threads):
 * -------------------------------------
 * [Main] All threads started! Watch them work together!
 *
 *   [Counter] Counting: 1
 *     [Printer] Letter: A
 *       [Calculator] 1 squared = 1
 *   [Counter] Counting: 2
 *       [Calculator] 2 squared = 4
 *   [Counter] Counting: 3
 *     [Printer] Letter: B
 *       [Calculator] 3 squared = 9
 *   [Counter] Counting: 4
 *   [Counter] Counting: 5
 *     [Printer] Letter: C
 *       [Calculator] 4 squared = 16
 *     [Printer] Letter: D
 *       [Calculator] 5 squared = 25
 *     [Printer] Letter: E
 *
 * [Main] All threads finished!
 *
 * NOTICE:
 * - All three threads print messages mixed together
 * - This proves they're running SIMULTANEOUSLY
 * - The order is unpredictable (depends on CPU scheduling)
 *
 *
 * ============================================================================
 * KEY CONCEPTS FOR STUDENTS
 * ============================================================================
 *
 * 1. CREATING A THREAD:
 *    Thread myThread = new Thread(() -> {
 *        // Code here runs in background
 *    });
 *
 * 2. STARTING A THREAD:
 *    myThread.start();  // ← Starts background execution
 *
 * 3. WAITING FOR A THREAD:
 *    myThread.join();   // ← Wait until thread finishes
 *
 * 4. THREAD NAMES:
 *    - "main" = The main thread (where program starts)
 *    - "Thread-0", "Thread-1" = Background threads
 *
 * 5. BENEFITS:
 *    - Tasks can run simultaneously
 *    - Saves time (parallel execution)
 *    - Main thread stays free
 *
 *
 * ============================================================================
 * TEACHING QUESTIONS
 * ============================================================================
 *
 * Q1: In Example 1, what thread does everything run on?
 * A1: The "main" thread
 *
 * Q2: In Example 2, how much time do we save?
 * A2: 3 seconds (6 seconds → 3 seconds)
 *
 * Q3: Why is the output in Example 3 mixed/interleaved?
 * A3: Because all threads are printing at the same time
 *
 * Q4: What does .start() do?
 * A4: Begins execution of the thread in the background
 *
 * Q5: What does .join() do?
 * A5: Waits for the thread to finish before continuing
 *
 * Q6: Can we predict the exact order of output in Example 3?
 * A6: No! It depends on how the CPU schedules the threads
 */