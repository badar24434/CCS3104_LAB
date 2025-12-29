package com.csc3402.lab.ccs3104_lab.LAB6;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StudentClient extends Application {
    private StudentServerInterface studentServer;
    private TextField nameField;
    private Label scoreLabel;
    private Label statusLabel;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Student Score Lookup System");

        // Main container
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(25));
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setStyle("-fx-background-color: #f5f5f5;");

        // Title
        Label titleLabel = new Label("Student Score Lookup");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#2c3e50"));

        // Input section
        VBox inputSection = new VBox(8);
        inputSection.setAlignment(Pos.CENTER_LEFT);

        Label nameLabel = new Label("Enter Student Name:");
        nameLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        nameField = new TextField();
        nameField.setPromptText("e.g., John, Michael, Michelle");
        nameField.setPrefWidth(280);
        nameField.setStyle("-fx-font-size: 14px; -fx-padding: 8px;");

        inputSection.getChildren().addAll(nameLabel, nameField);

        // Lookup button
        Button lookupButton = new Button("Look Up Score");
        lookupButton.setPrefWidth(280);
        lookupButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;");
        lookupButton.setOnMouseEntered(e -> lookupButton.setStyle(
                "-fx-background-color: #2980b9; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;"));
        lookupButton.setOnMouseExited(e -> lookupButton.setStyle(
                "-fx-background-color: #3498db; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 10px; -fx-cursor: hand;"));

        // Result section
        VBox resultSection = new VBox(5);
        resultSection.setAlignment(Pos.CENTER);
        resultSection.setPadding(new Insets(15));
        resultSection.setStyle("-fx-background-color: white; -fx-background-radius: 8px; " +
                "-fx-border-color: #ddd; -fx-border-radius: 8px;");

        Label resultTitle = new Label("Score Result");
        resultTitle.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        resultTitle.setTextFill(Color.web("#7f8c8d"));

        scoreLabel = new Label("—");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        scoreLabel.setTextFill(Color.web("#2c3e50"));

        resultSection.getChildren().addAll(resultTitle, scoreLabel);

        // Status label
        statusLabel = new Label("Connecting to server...");
        statusLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        statusLabel.setTextFill(Color.web("#95a5a6"));

        // Add all to main container
        mainContainer.getChildren().addAll(titleLabel, inputSection, lookupButton, resultSection, statusLabel);

        // Button action
        lookupButton.setOnAction(e -> lookupScore());
        nameField.setOnAction(e -> lookupScore()); // Allow Enter key

        Scene scene = new Scene(mainContainer, 330, 320);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        initializeRMI();
    }

    private void initializeRMI() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1098);
            studentServer = (StudentServerInterface) registry.lookup("StudentServerInterface");
            statusLabel.setText("✓ Connected to server");
            statusLabel.setTextFill(Color.web("#27ae60"));
        } catch (Exception e) {
            statusLabel.setText("✗ Server not available");
            statusLabel.setTextFill(Color.web("#e74c3c"));
            System.err.println("Client exception: " + e.toString());
        }
    }

    private void lookupScore() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            scoreLabel.setText("—");
            scoreLabel.setTextFill(Color.web("#e74c3c"));
            return;
        }

        try {
            double score = studentServer.findScore(name);
            if (score >= 0) {
                scoreLabel.setText(String.format("%.1f", score));
                scoreLabel.setTextFill(Color.web("#27ae60"));
            } else {
                scoreLabel.setText("No Access");
                scoreLabel.setTextFill(Color.web("#e67e22"));
            }
        } catch (Exception e) {
            scoreLabel.setText("Error");
            scoreLabel.setTextFill(Color.web("#e74c3c"));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}