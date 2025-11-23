package com.csc3402.lab.ccs3104_lab.LAB4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple Counter Example - Teaching Multithreading Basics
 * Shows the difference between blocking and non-blocking operations
 * Lab 4 - CCS3104
 */
public class SimpleCounterExample extends Application {

    private Label counterLabel;
    private Label statusLabel;
    private Button startButton;
    private Button stopButton;
    private Button testUIButton;

    private boolean isRunning = false;
    private int counter = 0;
    private Thread counterThread;

    @Override
    public void start(Stage primaryStage) {
        // Create UI components
        Label title = new Label("Simple Counter - Multithreading Demo");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        counterLabel = new Label("Counter: 0");
        counterLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: blue;");

        statusLabel = new Label("Status: Ready");
        statusLabel.setStyle("-fx-font-size: 14px;");

        // Create buttons
        startButton = new Button("Start Counting");
        stopButton = new Button("Stop Counting");
        testUIButton = new Button("Test UI (Click Me!)");

        stopButton.setDisable(true);

        // Button styles
        startButton.setStyle("-fx-font-size: 14px; -fx-min-width: 150px;");
        stopButton.setStyle("-fx-font-size: 14px; -fx-min-width: 150px;");
        testUIButton.setStyle("-fx-font-size: 14px; -fx-min-width: 150px;");

        // Button actions
        startButton.setOnAction(e -> startCounting());
        stopButton.setOnAction(e -> stopCounting());
        testUIButton.setOnAction(e -> testUI());

        // Layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(
                title,
                counterLabel,
                statusLabel,
                startButton,
                stopButton,
                testUIButton
        );

        // Scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setTitle("Multithreading Counter");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> stopCounting());
        primaryStage.show();
    }

    /**
     * START COUNTING - This demonstrates multithreading
     * The counter runs in a background thread
     */
    private void startCounting() {
        // STEP 1: Check if already running
        if (isRunning) {
            statusLabel.setText("Status: Already running!");
            return;
        }

        // STEP 2: Set flag and update UI
        isRunning = true;
        counter = 0;
        startButton.setDisable(true);
        stopButton.setDisable(false);
        statusLabel.setText("Status: Counting...");

        // STEP 3: Create a new background thread
        counterThread = new Thread(() -> {

            // This code runs in BACKGROUND THREAD
            System.out.println("Counter thread started: " + Thread.currentThread().getName());

            try {
                // Keep counting while isRunning is true
                while (isRunning) {

                    // Increment counter (background work)
                    counter++;

                    // STEP 4: Update UI using Platform.runLater()
                    // We MUST use Platform.runLater because we're in background thread
                    Platform.runLater(() -> {
                        // This code runs in MAIN THREAD (JavaFX Application Thread)
                        counterLabel.setText("Counter: " + counter);
                    });

                    // Sleep for 100ms to make counting visible
                    // This simulates doing some work
                    Thread.sleep(100);
                }

                // When stopped, update status
                Platform.runLater(() -> {
                    statusLabel.setText("Status: Stopped at " + counter);
                });

            } catch (InterruptedException e) {
                // If thread is interrupted, update UI
                Platform.runLater(() -> {
                    statusLabel.setText("Status: Interrupted!");
                });
            }

            System.out.println("Counter thread finished");
        });

        // STEP 5: Set as daemon thread
        counterThread.setDaemon(true);

        // STEP 6: Start the thread
        counterThread.start();
    }

    /**
     * STOP COUNTING
     * Demonstrates how to safely stop a thread
     */
    private void stopCounting() {
        if (!isRunning) {
            return;
        }

        // Set flag to false - the thread will see this and stop
        isRunning = false;

        // Update buttons
        startButton.setDisable(false);
        stopButton.setDisable(true);

        statusLabel.setText("Status: Stopping...");
    }

    /**
     * TEST UI RESPONSIVENESS
     * Click this while counting to prove UI is not frozen
     */
    private void testUI() {
        // This runs on the main thread
        System.out.println("Test button clicked on: " + Thread.currentThread().getName());

        // Show temporary message
        String oldStatus = statusLabel.getText();
        statusLabel.setText("Status: UI is responsive! 👍");

        // Reset after 1 second
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    if (isRunning) {
                        statusLabel.setText("Status: Counting...");
                    } else {
                        statusLabel.setText(oldStatus);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


/*
 * TEACHING NOTES FOR STUDENTS:
 * ============================
 *
 * EXPERIMENT 1: Without Multithreading (WRONG WAY)
 * -------------------------------------------------
 * If we did the counting in the main thread like this:
 *
 *     private void startCountingWrong() {
 *         for (int i = 0; i < 100; i++) {
 *             counter++;
 *             counterLabel.setText("Counter: " + counter);
 *             Thread.sleep(100); // This would FREEZE the UI!
 *         }
 *     }
 *
 * Result: The UI would freeze! You couldn't click buttons or close the window.
 *
 *
 * EXPERIMENT 2: With Multithreading (CORRECT WAY)
 * ------------------------------------------------
 * By using a background thread:
 * - The counter runs in the background
 * - The UI stays responsive
 * - You can click "Test UI" button while counting
 *
 *
 * KEY CONCEPTS DEMONSTRATED:
 * ==========================
 *
 * 1. TWO THREADS:
 *    - Main Thread (JavaFX Application Thread): Handles UI
 *    - Background Thread (counterThread): Does the counting
 *
 * 2. isRunning FLAG:
 *    - Controls when the thread should stop
 *    - Shared between threads (communication mechanism)
 *
 * 3. Platform.runLater():
 *    - Bridge from background thread to main thread
 *    - Required for updating UI components
 *
 * 4. Thread.sleep():
 *    - Pauses the current thread
 *    - In background thread: doesn't freeze UI
 *    - In main thread: would freeze UI (bad!)
 *
 * 5. Daemon Thread:
 *    - Automatically stops when app closes
 *    - Prevents app from hanging
 *
 *
 * WHAT TO OBSERVE:
 * ================
 * 1. Click "Start Counting" - counter starts incrementing
 * 2. While counting, click "Test UI" - still responsive!
 * 3. This proves the main thread is not blocked
 * 4. Click "Stop Counting" - the background thread stops safely
 *
 *
 * COMPARE WITH:
 * =============
 * If you remove multithreading and do everything in main thread:
 * - UI would freeze during counting
 * - "Test UI" button wouldn't respond
 * - Window might show "Not Responding"
 */