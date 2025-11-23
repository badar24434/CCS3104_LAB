package com.csc3402.lab.ccs3104_lab.LAB4;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Simple Eight Queens Problem Solver
 * Demonstrates multithreading with backtracking algorithm
 * Lab 4 - CCS3104
 */
public class EightQueensSimple extends Application {
    private static final int SIZE = 8;
    private int[] queens = new int[SIZE];
    private Label[][] board = new Label[SIZE][SIZE];
    private Label counterLabel;
    private int solutionCounter = 0;
    private boolean isSearching = false;
    
    @Override
    public void start(Stage primaryStage) {
        // Initialize queens array
        for (int i = 0; i < SIZE; i++) {
            queens[i] = -1;
        }
        
        // Create main layout
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));
        
        // Title
        Label title = new Label("Eight Queens Problem - Simple Version");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        // Counter label
        counterLabel = new Label("Solutions found: 0");
        counterLabel.setStyle("-fx-font-size: 14px;");
        
        // Top section
        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(title, counterLabel);
        
        // Create chess board
        GridPane chessBoard = createChessBoard();
        
        // Create buttons
        Button resetButton = new Button("Reset");
        Button nextButton = new Button("Next Solution");
        
        resetButton.setOnAction(e -> resetBoard());
        nextButton.setOnAction(e -> findNextSolutionThreaded());
        
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(resetButton, nextButton);
        
        // Layout
        root.setTop(topBox);
        root.setCenter(chessBoard);
        root.setBottom(buttonBox);
        
        // Scene
        Scene scene = new Scene(root, 500, 550);
        primaryStage.setTitle("Eight Queens - Simple");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // Find first solution using a separate thread
        findNextSolutionThreaded();
    }
    
    /**
     * Create the chess board grid
     */
    private GridPane createChessBoard() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(2);
        grid.setVgap(2);
        
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Label cell = new Label();
                cell.setPrefSize(50, 50);
                cell.setAlignment(Pos.CENTER);
                
                // Checkerboard pattern
                if ((i + j) % 2 == 0) {
                    cell.setStyle("-fx-background-color: white; -fx-border-color: gray;");
                } else {
                    cell.setStyle("-fx-background-color: lightgray; -fx-border-color: gray;");
                }
                
                board[i][j] = cell;
                grid.add(cell, j, i);
            }
        }
        
        return grid;
    }
    
    /**
     * Find next solution using a separate thread
     * demonstrates multithreading in JavaFX
     */
    private void findNextSolutionThreaded() {
        if (isSearching) {
            return; // Prevent multiple simultaneous searches
        }
        
        isSearching = true;
        
        // Create a new thread for the search operation
        // This prevents the UI from freezing during the search
        Thread searchThread = new Thread(() -> {
            try {
                // Perform the search in this background thread
                boolean found = findNextSolution();
                
                // Update UI on JavaFX Application Thread
                // This is REQUIRED - UI updates must happen on the JavaFX thread
                Platform.runLater(() -> {
                    if (found) {
                        solutionCounter++;
                        counterLabel.setText("Solutions found: " + solutionCounter);
                        updateBoard();
                    } else {
                        counterLabel.setText("No more solutions! (Total: " + solutionCounter + ")");
                    }
                    isSearching = false;
                });
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    counterLabel.setText("Error: " + e.getMessage());
                    isSearching = false;
                });
            }
        });
        
        // Set as daemon thread (will stop when application closes)
        searchThread.setDaemon(true);
        
        // Start the thread
        searchThread.start();
    }
    
    /**
     * Find the next solution using backtracking
     * This runs in a background thread
     */
    private boolean findNextSolution() {
        // Check if we have a current solution
        boolean hasSolution = queens[SIZE - 1] >= 0;
        
        int k;
        if (hasSolution) {
            // Backtrack from last position to find next solution
            queens[SIZE - 1] = -1;
            k = SIZE - 2;
            
            // Find next position in previous row
            while (k >= 0) {
                int nextPos = findPosition(k);
                if (nextPos >= 0) {
                    queens[k] = nextPos;
                    k++;
                    break;
                } else {
                    queens[k] = -1;
                    k--;
                }
            }
        } else {
            // Start from beginning
            k = 0;
        }
        
        // Complete the solution
        while (k >= 0 && k < SIZE) {
            int pos = findPosition(k);
            if (pos >= 0) {
                queens[k] = pos;
                k++;
            } else {
                queens[k] = -1;
                k--;
            }
        }
        
        return k == SIZE;
    }
    
    /**
     * Find a valid position for a queen in row k
     */
    private int findPosition(int k) {
        int start = queens[k] + 1;
        
        for (int j = start; j < SIZE; j++) {
            if (isValid(k, j)) {
                return j;
            }
        }
        
        return -1;
    }
    
    /**
     * Check if placing a queen at (row, col) is valid
     */
    private boolean isValid(int row, int col) {
        for (int i = 0; i < row; i++) {
            // Check column
            if (queens[i] == col) {
                return false;
            }
            
            // Check diagonals
            if (Math.abs(queens[i] - col) == Math.abs(i - row)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Update the board display with current queen positions
     */
    private void updateBoard() {
        // Clear all queens
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].setText("");
            }
        }
        
        // Place queens
        for (int i = 0; i < SIZE; i++) {
            if (queens[i] >= 0) {
                board[i][queens[i]].setText("♛");
                board[i][queens[i]].setStyle(
                    board[i][queens[i]].getStyle() + 
                    "-fx-font-size: 30px; -fx-text-fill: red;"
                );
            }
        }
    }
    
    /**
     * Reset the board and counter
     */
    private void resetBoard() {
        if (isSearching) {
            return; // Don't reset while searching
        }
        
        // Clear queens array
        for (int i = 0; i < SIZE; i++) {
            queens[i] = -1;
        }
        
        // Clear board display
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j].setText("");
                // Reset to original style
                if ((i + j) % 2 == 0) {
                    board[i][j].setStyle("-fx-background-color: white; -fx-border-color: gray;");
                } else {
                    board[i][j].setStyle("-fx-background-color: lightgray; -fx-border-color: gray;");
                }
            }
        }
        
        // Reset counter
        solutionCounter = 0;
        counterLabel.setText("Solutions found: 0");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
