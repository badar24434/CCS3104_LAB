package com.csc3402.lab.ccs3104_lab.LAB5;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

nt connects to the remote server and requests area calculations
 */
public class AreaCalculatorClient extends Application {
    private TextField radiusField;
    private TextArea resultArea;
    private AreaCalculator calculator;

    @Override
    public void start(Stage primaryStage) {
        // Setup UI Components
        Label titleLabel = new Label("Circle Area Calculator - RMI Client");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        
        Label radiusLabel = new Label("Enter radius:");
        radiusField = new TextField();
        radiusField.setPromptText("e.g., 4.0");
        radiusField.setPrefWidth(200);
        
        Button calculateButton = new Button("Calculate Area");
        calculateButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setPrefRowCount(6);
        resultArea.setWrapText(true);
        
        // Connect to RMI server
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            calculator = (AreaCalculator) registry.lookup("AreaCalculator");
            resultArea.appendText("✓ Successfully connected to RMI server\n");
            resultArea.appendText("Server address: localhost:1099\n\n");
        } catch (Exception e) {
            resultArea.appendText("✗ Error connecting to server:\n");
            resultArea.appendText(e.getMessage() + "\n");
            resultArea.appendText("\nPlease ensure the server is running first!\n");
            calculateButton.setDisable(true);
        }
        
        // Button action
        calculateButton.setOnAction(e -> calculateArea());
        
        // Allow Enter key to trigger calculation
        radiusField.setOnAction(e -> calculateArea());
        
        // Layout
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(
            titleLabel,
            radiusLabel,
            radiusField,
            calculateButton,
            new Label("Results:"),
            resultArea
        );
        
        Scene scene = new Scene(layout, 450, 400);
        primaryStage.setTitle("RMI Client - Area Calculator");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Method to calculate area by calling the remote server
     */
    private void calculateArea() {
        try {
            String radiusText = radiusField.getText().trim();
            if (radiusText.isEmpty()) {
                showAlert("Please enter a radius value");
                return;
            }
            
            double radius = Double.parseDouble(radiusText);
            
            if (radius < 0) {
                showAlert("Radius cannot be negative");
                return;
            }
            
            // Call the remote method
            double area = calculator.computeArea(radius);
            
            // Display result
            resultArea.appendText("─────────────────────────────\n");
            resultArea.appendText("Radius: " + radius + "\n");
            resultArea.appendText("Area received from server: " + area + "\n");
            resultArea.appendText("─────────────────────────────\n\n");
            
            // Clear input field
            radiusField.clear();
            radiusField.requestFocus();
            
        } catch (NumberFormatException e) {
            showAlert("Invalid input! Please enter a valid number");
        } catch (Exception e) {
            resultArea.appendText("Error calling remote method:\n");
            resultArea.appendText(e.getMessage() + "\n\n");
            e.printStackTrace();
        }
    }
    
    /**
     * Helper method to show alert dialogs
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
