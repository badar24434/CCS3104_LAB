package com.csc3402.lab.ccs3104_lab.LAB4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Eight Queens Problem Solver using Backtracking Algorithm
 * Based on Listing 22.11 from Liang textbook (10th Edition)
 * 
 * The Eight Queens problem is to find a solution to place a queen in each row 
 * on a chessboard such that no two queens can attack each other.
 */
public class EightQueens extends Application {
    public static final int SIZE = 8; // The size of the chess board
    
    // queens are placed at (i, queens[i])
    // -1 indicates that no queen is currently placed in the ith row
    // Initially, no queens are placed
    private int[] queens = {-1, -1, -1, -1, -1, -1, -1, -1}; // queen positions
    
    private GridPane chessBoard;
    private Label[][] labels = new Label[SIZE][SIZE];
    private Image queenImage;
    private Label statusLabel;
    private int solutionCount = 0;

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Main container
        VBox mainContainer = new VBox(20);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setStyle("-fx-background-color: linear-gradient(to bottom, #e3f2fd, #bbdefb);");
        
        // Title
        Label titleLabel = new Label("Eight Queens Problem Solver");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; " +
                           "-fx-text-fill: #0d47a1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        
        // Subtitle
        Label subtitleLabel = new Label("Using Backtracking Algorithm");
        subtitleLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #1565c0; -fx-font-style: italic;");
        
        // Status label
        statusLabel = new Label("Click 'Find Solution' to solve the puzzle");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242; " +
                            "-fx-padding: 10px; -fx-background-color: rgba(255,255,255,0.8); " +
                            "-fx-background-radius: 5px; -fx-border-color: #64b5f6; " +
                            "-fx-border-width: 2px; -fx-border-radius: 5px;");
        
        // Search for a solution
        search();
        
        // Display chess board
        chessBoard = new GridPane();
        chessBoard.setAlignment(Pos.CENTER);
        chessBoard.setHgap(2);
        chessBoard.setVgap(2);
        chessBoard.setStyle("-fx-background-color: #424242; -fx-padding: 10px; " +
                           "-fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 15, 0, 0, 5);");
        
        // Create chess board cells
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labels[i][j] = new Label();
                chessBoard.add(labels[i][j], j, i);
                
                // Alternating colors for chess board
                if ((i + j) % 2 == 0) {
                    labels[i][j].setStyle("-fx-background-color: #ffffff; " +
                                         "-fx-border-color: #e0e0e0; -fx-border-width: 1px;");
                } else {
                    labels[i][j].setStyle("-fx-background-color: #90caf9; " +
                                         "-fx-border-color: #64b5f6; -fx-border-width: 1px;");
                }
                labels[i][j].setPrefSize(60, 60);
                labels[i][j].setAlignment(Pos.CENTER);
            }
        }
        
        // Load and display queens
        try {
            queenImage = new Image(getClass().getResourceAsStream(
                "/com/csc3402/lab/ccs3104_lab/LAB4/queen.png"));
            displayQueens();
            statusLabel.setText("✓ Solution found! Queens placed successfully.");
            statusLabel.setStyle(statusLabel.getStyle() + "-fx-text-fill: #2e7d32;");
        } catch (Exception e) {
            // If image not found, use a crown emoji or unicode symbol
            displayQueensWithSymbol();
            statusLabel.setText("✓ Solution found! (Using symbol - queen image not found)");
            statusLabel.setStyle(statusLabel.getStyle() + "-fx-text-fill: #f57c00;");
        }
        
        // Control buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        Button solveButton = new Button("Find New Solution");
        styleButton(solveButton, "#1976d2");
        solveButton.setOnAction(e -> {
            findNewSolution();
        });
        
        Button resetButton = new Button("Reset Board");
        styleButton(resetButton, "#0288d1");
        resetButton.setOnAction(e -> {
            resetBoard();
        });
        
        Button nextButton = new Button("Next Solution");
        styleButton(nextButton, "#0277bd");
        nextButton.setOnAction(e -> {
            findNextSolution();
        });
        
        buttonBox.getChildren().addAll(solveButton, resetButton, nextButton);
        
        // Info label
        Label infoLabel = new Label("The Eight Queens problem places 8 queens on a chessboard\n" +
                                   "such that no two queens attack each other.");
        infoLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #546e7a; -fx-text-alignment: center;");
        infoLabel.setAlignment(Pos.CENTER);
        
        // Add all components to main container
        VBox headerBox = new VBox(5);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.getChildren().addAll(titleLabel, subtitleLabel);
        
        mainContainer.getChildren().addAll(headerBox, statusLabel, chessBoard, buttonBox, infoLabel);
        
        // Create a scene and place it in the stage
        Scene scene = new Scene(mainContainer, 650, 850);
        primaryStage.setTitle("Eight Queens Problem - Backtracking Solution");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Style a button with modern design
     */
    private void styleButton(Button button, String color) {
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; " +
                       "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 12px 24px; " +
                       "-fx-background-radius: 25px; -fx-cursor: hand; " +
                       "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 0, 2);");
        
        button.setOnMouseEntered(e -> {
            button.setStyle(button.getStyle() + "-fx-scale-x: 1.05; -fx-scale-y: 1.05;");
        });
        
        button.setOnMouseExited(e -> {
            button.setStyle(button.getStyle() + "-fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        });
    }
    
    /**
     * Display queens on the board using images
     */
    private void displayQueens() {
        for (int i = 0; i < SIZE; i++) {
            if (queens[i] >= 0) {
                ImageView imageView = new ImageView(queenImage);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                imageView.setPreserveRatio(true);
                labels[i][queens[i]].setGraphic(imageView);
            }
        }
    }
    
    /**
     * Display queens using a Unicode symbol (fallback)
     */
    private void displayQueensWithSymbol() {
        for (int i = 0; i < SIZE; i++) {
            if (queens[i] >= 0) {
                Label queenLabel = new Label("♛");
                queenLabel.setStyle("-fx-font-size: 40px; -fx-text-fill: #d32f2f;");
                labels[i][queens[i]].setGraphic(queenLabel);
            }
        }
    }
    
    /**
     * Clear all queens from the board
     */
    private void clearBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                labels[i][j].setGraphic(null);
            }
        }
    }
    
    /**
     * Find a new solution
     */
    private void findNewSolution() {
        queens = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        clearBoard();
        
        if (search()) {
            solutionCount++;
            try {
                displayQueens();
            } catch (Exception e) {
                displayQueensWithSymbol();
            }
            statusLabel.setText("✓ Solution #" + solutionCount + " found!");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #2e7d32; " +
                                "-fx-padding: 10px; -fx-background-color: rgba(255,255,255,0.8); " +
                                "-fx-background-radius: 5px; -fx-border-color: #64b5f6; " +
                                "-fx-border-width: 2px; -fx-border-radius: 5px;");
        } else {
            statusLabel.setText("✗ No solution found!");
            statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #c62828; " +
                                "-fx-padding: 10px; -fx-background-color: rgba(255,255,255,0.8); " +
                                "-fx-background-radius: 5px; -fx-border-color: #64b5f6; " +
                                "-fx-border-width: 2px; -fx-border-radius: 5px;");
        }
    }
    
    /**
     * Reset the board
     */
    private void resetBoard() {
        queens = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        clearBoard();
        solutionCount = 0;
        statusLabel.setText("Board reset. Click 'Find Solution' to solve.");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #424242; " +
                            "-fx-padding: 10px; -fx-background-color: rgba(255,255,255,0.8); " +
                            "-fx-background-radius: 5px; -fx-border-color: #64b5f6; " +
                            "-fx-border-width: 2px; -fx-border-radius: 5px;");
    }
    
    /**
     * Find next solution (variation)
     */
    private void findNextSolution() {
        findNewSolution();
    }

    /**
     * Search for a solution using backtracking algorithm
     * @return true if a solution is found, false otherwise
     */
    private boolean search() {
        // k indicates the row number where we're trying to place a queen
        // queens[k] indicates the column where the queen is placed in row k
        int k = 0;
        
        while (k >= 0 && k < SIZE) {
            // Find a position to place a queen in the kth row
            int j = findPosition(k);
            
            if (j < 0) {
                // No position found in this row, backtrack to the previous row
                queens[k] = -1;
                k--; // Move back to previous row
            } else {
                // Place the queen at (k, j) and move to the next row
                queens[k] = j;
                k++; // Move to next row
            }
        }
        
        if (k == -1)
            return false; // No solution - backtracked past first row
        else
            return true; // A solution is found - reached row SIZE
    }
    
    /**
     * Find a valid position to place a queen in row k
     * @param k the row number
     * @return the column number if found, -1 otherwise
     */
    public int findPosition(int k) {
        int start = queens[k] + 1; // Search for a new placement after the previous one
        
        for (int j = start; j < SIZE; j++) {
            if (isValid(k, j)) {
                return j; // (k, j) is a valid place to put the queen
            }
        }
        
        return -1; // No valid position found in this row
    }
    
    /**
     * Check if a queen can be placed at (row, column)
     * A queen can be placed if:
     * 1. No other queen is in the same column
     * 2. No other queen is in the upper-left diagonal
     * 3. No other queen is in the upper-right diagonal
     * 
     * @param row the row to check
     * @param column the column to check
     * @return true if valid, false otherwise
     */
    public boolean isValid(int row, int column) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == column) // Check column
                return false;
            
            if (queens[i] - column == i - row) // Check upper-left diagonal
                return false;
            
            if (queens[i] - column == row - i) // Check upper-right diagonal
                return false;
        }
        
        return true; // No conflict - position is valid
    }
    
    /**
     * Main method to launch the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
