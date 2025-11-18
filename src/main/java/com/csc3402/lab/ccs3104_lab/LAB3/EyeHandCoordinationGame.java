package com.csc3402.lab.ccs3104_lab.LAB3;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Eye-Hand Coordination Game
 * Tests reflexes by requiring players to click 20 randomly placed circles
 * Measures total time taken in milliseconds
 */
public class EyeHandCoordinationGame extends Application {
    
    private static final int TOTAL_CIRCLES = 20;
    private static final int CIRCLE_RADIUS = 10;
    private static final int GAME_WIDTH = 800;
    private static final int GAME_HEIGHT = 600;
    
    private Pane gamePane;
    private Label statusLabel;
    private Label timerLabel;
    private Label circleCountLabel;
    private Button startButton;
    private VBox startScreen;
    private VBox resultScreen;
    
    private int circlesClicked = 0;
    private long startTime;
    private long endTime;
    private Random random;
    private AnimationTimer timer;
    
    // Color palette for random circles
    private final Color[] circleColors = {
            Color.rgb(255, 107, 107),  // Red
            Color.rgb(255, 184, 77),   // Orange
            Color.rgb(255, 234, 167),  // Yellow
            Color.rgb(132, 250, 176),  // Green
            Color.rgb(111, 168, 220),  // Light Blue
            Color.rgb(162, 155, 254),  // Purple
            Color.rgb(255, 118, 117),  // Pink
            Color.rgb(119, 221, 231),  // Cyan
    };
    
    @Override
    public void start(Stage primaryStage) {
        random = new Random();
        
        // Main container
        StackPane root = new StackPane();
        root.setId("root");
        
        // Create start screen
        createStartScreen();
        
        // Create game pane
        gamePane = new Pane();
        gamePane.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        gamePane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.95); " +
                         "-fx-background-radius: 15; " +
                         "-fx-border-color: #87CEEB; " +
                         "-fx-border-width: 3; " +
                         "-fx-border-radius: 15;");
        gamePane.setVisible(false);
        
        // Create HUD (Heads Up Display)
        VBox hud = createHUD();
        
        // Result screen
        createResultScreen();
        
        // Add all layers to root
        root.getChildren().addAll(gamePane, hud, startScreen, resultScreen);
        
        // Create scene
        Scene scene = new Scene(root, GAME_WIDTH, GAME_HEIGHT);
        scene.getStylesheets().add(
            getClass().getResource("/com/csc3402/lab/ccs3104_lab/LAB3/eye-hand-game.css").toExternalForm()
        );
        
        primaryStage.setTitle("Eye-Hand Coordination Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private void createStartScreen() {
        startScreen = new VBox(30);
        startScreen.setAlignment(Pos.CENTER);
        startScreen.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        startScreen.getStyleClass().add("start-screen");
        
        Label titleLabel = new Label("Eye-Hand Coordination");
        titleLabel.getStyleClass().add("title-label");
        
        Label subtitleLabel = new Label("Test Your Reflexes!");
        subtitleLabel.getStyleClass().add("subtitle-label");
        
        VBox instructionsBox = new VBox(10);
        instructionsBox.setAlignment(Pos.CENTER);
        instructionsBox.getStyleClass().add("instructions-box");
        
        Label instructionTitle = new Label("How to Play");
        instructionTitle.getStyleClass().add("instruction-title");
        
        Label instruction1 = new Label("• Click on " + TOTAL_CIRCLES + " circles as they appear");
        Label instruction2 = new Label("• Each circle appears one at a time");
        Label instruction3 = new Label("• Try to complete as fast as possible!");
        
        instruction1.getStyleClass().add("instruction-text");
        instruction2.getStyleClass().add("instruction-text");
        instruction3.getStyleClass().add("instruction-text");
        
        instructionsBox.getChildren().addAll(instructionTitle, instruction1, instruction2, instruction3);
        
        startButton = new Button("START GAME");
        startButton.getStyleClass().add("start-button");
        startButton.setOnAction(e -> startGame());
        
        startScreen.getChildren().addAll(titleLabel, subtitleLabel, instructionsBox, startButton);
    }
    
    private VBox createHUD() {
        VBox hud = new VBox(10);
        hud.setAlignment(Pos.TOP_CENTER);
        hud.setPadding(new Insets(20));
        hud.setMouseTransparent(true);
        
        // Status label
        statusLabel = new Label("Click the circle!");
        statusLabel.getStyleClass().add("status-label");
        
        // Timer label
        timerLabel = new Label("Time: 0 ms");
        timerLabel.getStyleClass().add("timer-label");
        
        // Circle count label
        circleCountLabel = new Label("Circles: 0 / " + TOTAL_CIRCLES);
        circleCountLabel.getStyleClass().add("circle-count-label");
        
        hud.getChildren().addAll(statusLabel, circleCountLabel, timerLabel);
        hud.setVisible(false);
        
        return hud;
    }
    
    private void createResultScreen() {
        resultScreen = new VBox(30);
        resultScreen.setAlignment(Pos.CENTER);
        resultScreen.setPrefSize(GAME_WIDTH, GAME_HEIGHT);
        resultScreen.getStyleClass().add("result-screen");
        resultScreen.setVisible(false);
    }
    
    private void startGame() {
        // Hide start screen, show game elements
        startScreen.setVisible(false);
        gamePane.setVisible(true);
        statusLabel.getParent().setVisible(true);
        
        // Reset game state
        circlesClicked = 0;
        gamePane.getChildren().clear();
        updateLabels();
        
        // Start timer and spawn first circle
        startTime = System.currentTimeMillis();
        
        // Create and start live timer
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateLabels();
            }
        };
        timer.start();
        
        spawnCircle();
    }
    
    private void spawnCircle() {
        // Calculate random position (ensure circle is fully visible)
        double x = CIRCLE_RADIUS + random.nextDouble() * (GAME_WIDTH - 2 * CIRCLE_RADIUS);
        double y = CIRCLE_RADIUS + random.nextDouble() * (GAME_HEIGHT - 2 * CIRCLE_RADIUS);
        
        // Create circle with random color
        Circle circle = new Circle(x, y, CIRCLE_RADIUS);
        Color randomColor = circleColors[random.nextInt(circleColors.length)];
        circle.setFill(randomColor);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(2);
        circle.getStyleClass().add("game-circle");
        
        // Add hover effect
        circle.setOnMouseEntered(e -> {
            circle.setRadius(CIRCLE_RADIUS * 1.3);
            circle.setStrokeWidth(3);
        });
        
        circle.setOnMouseExited(e -> {
            circle.setRadius(CIRCLE_RADIUS);
            circle.setStrokeWidth(2);
        });
        
        // Handle click
        circle.setOnMouseClicked(e -> {
            circlesClicked++;
            
            // Remove clicked circle
            gamePane.getChildren().remove(circle);
            
            if (circlesClicked < TOTAL_CIRCLES) {
                // Spawn next circle
                spawnCircle();
            } else {
                // Game completed
                endGame();
            }
        });
        
        gamePane.getChildren().add(circle);
    }
    
    private void updateLabels() {
        circleCountLabel.setText("Circles: " + circlesClicked + " / " + TOTAL_CIRCLES);
        long currentTime = System.currentTimeMillis() - startTime;
        timerLabel.setText("Time: " + currentTime + " ms");
    }
    
    private void endGame() {
        // Stop the live timer
        if (timer != null) {
            timer.stop();
        }
        
        endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        
        // Hide game elements
        gamePane.setVisible(false);
        statusLabel.getParent().setVisible(false);
        
        // Show result screen
        showResults(totalTime);
    }
    
    private void showResults(long totalTime) {
        resultScreen.getChildren().clear();
        
        Label congratsLabel = new Label("🎉 Congratulations! 🎉");
        congratsLabel.getStyleClass().add("congrats-label");
        
        Label completedLabel = new Label("You clicked all " + TOTAL_CIRCLES + " circles!");
        completedLabel.getStyleClass().add("completed-label");
        
        // Result box
        VBox resultBox = new VBox(15);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.getStyleClass().add("result-box");
        
        Label timeResultLabel = new Label("Total Time");
        timeResultLabel.getStyleClass().add("result-title");
        
        Label timeValueLabel = new Label(totalTime + " ms");
        timeValueLabel.getStyleClass().add("result-value");
        
        Label avgTimeLabel = new Label("Average per circle: " + (totalTime / TOTAL_CIRCLES) + " ms");
        avgTimeLabel.getStyleClass().add("avg-time-label");
        
        resultBox.getChildren().addAll(timeResultLabel, timeValueLabel, avgTimeLabel);
        
        // Performance rating
        Label ratingLabel = new Label(getRating(totalTime));
        ratingLabel.getStyleClass().add("rating-label");
        
        Button playAgainButton = new Button("PLAY AGAIN");
        playAgainButton.getStyleClass().add("play-again-button");
        playAgainButton.setOnAction(e -> {
            resultScreen.setVisible(false);
            startScreen.setVisible(true);
        });
        
        resultScreen.getChildren().addAll(
            congratsLabel, completedLabel, resultBox, ratingLabel, playAgainButton
        );
        resultScreen.setVisible(true);
    }
    
    private String getRating(long totalTime) {
        double avgTime = totalTime / (double) TOTAL_CIRCLES;
        
        if (avgTime < 300) {
            return "⚡ LIGHTNING FAST! ⚡";
        } else if (avgTime < 400) {
            return "🔥 EXCELLENT! 🔥";
        } else if (avgTime < 500) {
            return "👍 GREAT JOB! 👍";
        } else if (avgTime < 700) {
            return "✨ GOOD! ✨";
        } else {
            return "💪 KEEP PRACTICING! 💪";
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}