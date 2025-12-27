package com.csc3402.lab.ccs3104_lab.LAB2;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SpeedingFineCalculatorModern extends Application {
    
    private TextField speedField;
    private TextField speedLimitField;
    private RadioButton carRadio;
    private RadioButton bikeRadio;
    private Label resultLabel;
    private Label fineAmountLabel;
    private Button calculateButton;
    private Button clearButton;
    private VBox resultBox;
    private ProgressBar speedIndicator;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speeding Fine Calculator - Modern UI");
        
        // Main container with gradient background
        BorderPane root = new BorderPane();
        root.setStyle(createGradientBackground());
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // Main content
        VBox mainContent = createMainContent();
        root.setCenter(mainContent);
        
        // Footer
        HBox footer = createFooter();
        root.setBottom(footer);
        
        // Create scene with animations
        Scene scene = new Scene(root, 650, 750);
        scene.setFill(Color.TRANSPARENT);
        
        // Apply fade-in animation to the entire scene
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), root);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private String createGradientBackground() {
     // soft blue-to-white gradient
     return "-fx-background-color: linear-gradient(to bottom right, " +
         "#ffffff 0%, #eaf5ff 50%, #d6eeff 100%);";
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(30, 20, 20, 20));
        
    // Title with blue color and subtle glow
    Label titleLabel = new Label("🚗 Speeding Fine Calculator");
    titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
    titleLabel.setTextFill(Color.web("#1E88E5"));

    DropShadow glow = new DropShadow();
    glow.setColor(Color.web("#BBDEFB", 0.9));
    glow.setRadius(16);
    titleLabel.setEffect(glow);

    Label subtitleLabel = new Label("Calculate your fine instantly");
    subtitleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));
    subtitleLabel.setTextFill(Color.web("#1565C0"));
        
        header.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Pulse animation for title
        ScaleTransition pulse = new ScaleTransition(Duration.millis(1500), titleLabel);
        pulse.setFromX(1.0);
        pulse.setFromY(1.0);
        pulse.setToX(1.05);
        pulse.setToY(1.05);
        pulse.setCycleCount(Animation.INDEFINITE);
        pulse.setAutoReverse(true);
        pulse.play();
        
        return header;
    }
    
   
        
    // Speed Indicator
    speedIndicator = new ProgressBar(0);
    speedIndicator.setPrefWidth(400);
    // default accent in light-blue
    speedIndicator.setStyle("-fx-accent: #90CAF9;");
    Label indicatorLabel = new Label("Speed Status");
    indicatorLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 12));
    indicatorLabel.setTextFill(Color.rgb(30, 60, 90));

    VBox indicatorBox = new VBox(5, indicatorLabel, speedIndicator);
    indicatorBox.setAlignment(Pos.CENTER);
        
        // Buttons Section
        HBox buttonBox = createButtonSection();
        
        // Result Section
        resultBox = createResultSection();
        resultBox.setVisible(false);
        resultBox.setManaged(false);
        
        card.getChildren().addAll(
            vehicleSection,
            speedSection,
            speedLimitSection,
            indicatorBox,
            buttonBox,
            resultBox
        );
        
        mainContent.getChildren().add(card);
        return mainContent;
    }
    
    private String createCardStyle() {
     return "-fx-background-color: rgba(255, 255, 255, 0.98); " +
         "-fx-background-radius: 20; " +
         "-fx-border-radius: 20; " +
         "-fx-border-color: rgba(30,136,229,0.08); " +
         "-fx-border-width: 1;";
    }
    
    private DropShadow createCardShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setRadius(25);
        shadow.setOffsetX(0);
        shadow.setOffsetY(10);
        shadow.setColor(Color.rgb(0, 0, 0, 0.3));
        return shadow;
    }
    
    private VBox createVehicleTypeSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.CENTER);
        
        Label label = new Label("Select Vehicle Type");
        label.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        label.setTextFill(Color.rgb(60, 60, 60));
        
        ToggleGroup vehicleGroup = new ToggleGroup();
        
        HBox radioBox = new HBox(30);
        radioBox.setAlignment(Pos.CENTER);
        
        carRadio = createStyledRadioButton("🚗 Car", vehicleGroup);
        bikeRadio = createStyledRadioButton("🏍️ Bike", vehicleGroup);
        
        carRadio.setSelected(true);
        
        radioBox.getChildren().addAll(carRadio, bikeRadio);
        
        // Add hover animation to radio buttons
        addHoverEffect(carRadio);
        addHoverEffect(bikeRadio);
        
        section.getChildren().addAll(label, radioBox);
        return section;
    }
    
    private RadioButton createStyledRadioButton(String text, ToggleGroup group) {
        RadioButton radio = new RadioButton(text);
        radio.setToggleGroup(group);
        radio.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16));
        radio.setTextFill(Color.rgb(60, 60, 60));
        radio.setStyle("-fx-cursor: hand;");
        return radio;
    }
    
    private VBox createSpeedInputSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label label = new Label("Vehicle Speed (km/h)");
        label.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.rgb(80, 80, 80));
        
        speedField = createStyledTextField("Enter speed (e.g., 120)");
        
        // Real-time validation and indicator update
        speedField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateNumericInput(speedField, newVal);
            updateSpeedIndicator();
        });
        
        section.getChildren().addAll(label, speedField);
        return section;
    }
    
    private VBox createSpeedLimitSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        
        Label label = new Label("Speed Limit (km/h)");
        label.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.rgb(80, 80, 80));
        
        speedLimitField = createStyledTextField("Enter speed limit (e.g., 90)");
        
        // Real-time validation and indicator update
        speedLimitField.textProperty().addListener((obs, oldVal, newVal) -> {
            validateNumericInput(speedLimitField, newVal);
            updateSpeedIndicator();
        });
        
        section.getChildren().addAll(label, speedLimitField);
        return section;
    }
    
    private TextField createStyledTextField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setPrefWidth(400);
        field.setPrefHeight(45);
        field.setFont(Font.font("Segoe UI", 14));
        field.setStyle(
            "-fx-background-color: #f5f5f5; " +
            "-fx-background-radius: 10; " +
            "-fx-border-color: #ddd; " +
            "-fx-border-radius: 10; " +
            "-fx-border-width: 2; " +
            "-fx-padding: 10;"
        );
        
        // Focus animation
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) {
                    field.setStyle(
                        "-fx-background-color: #ffffff; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #1E88E5; " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-width: 2; " +
                        "-fx-padding: 10;"
                    );
            } else {
                field.setStyle(
                    "-fx-background-color: #f5f5f5; " +
                    "-fx-background-radius: 10; " +
                    "-fx-border-color: #ddd; " +
                    "-fx-border-radius: 10; " +
                    "-fx-border-width: 2; " +
                    "-fx-padding: 10;"
                );
            }
        });
        
        return field;
    }
    
    private HBox createButtonSection() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        
    // Primary button (blue gradient) and secondary clear button (white outline)
    calculateButton = createStyledButton("Calculate Fine", "#1E88E5", "#64B5F6", false);
    clearButton = createStyledButton("Clear", "#ffffff", "#1E88E5", true);
        
        calculateButton.setOnAction(e -> calculateFine());
        clearButton.setOnAction(e -> clearFields());
        
        buttonBox.getChildren().addAll(calculateButton, clearButton);
        return buttonBox;
    }
    
    private Button createStyledButton(String text, String color1, String color2, boolean outlined) {
        Button button = new Button(text);
        button.setPrefWidth(180);
        button.setPrefHeight(45);
        button.setFont(Font.font("Segoe UI", FontWeight.BOLD, 15));
        button.setStyle("-fx-background-radius: 25; -fx-cursor: hand;");

        if (outlined) {
            // white/transparent background with blue border and blue text
            button.setStyle(button.getStyle() +
                "-fx-background-color: transparent; " +
                "-fx-border-color: " + color2 + "; " +
                "-fx-border-width: 2; " +
                "-fx-text-fill: " + color2 + ";");
        } else {
            // filled blue gradient
            button.setStyle(button.getStyle() +
                "-fx-background-color: linear-gradient(to right, " + color1 + ", " + color2 + "); " +
                "-fx-text-fill: white;");
        }

        DropShadow shadow = new DropShadow();
        shadow.setRadius(8);
        shadow.setColor(Color.rgb(30, 60, 90, 0.12));
        button.setEffect(shadow);

        // Hover animation
        button.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        button.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });

        // Click animation
        button.setOnMousePressed(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(0.95);
            st.setToY(0.95);
            st.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
            st.setToX(1.05);
            st.setToY(1.05);
            st.play();
        });

        return button;
    }
    
    private VBox createResultSection() {
        VBox section = new VBox(15);
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(20, 0, 0, 0));
        
        resultLabel = new Label();
        resultLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        resultLabel.setWrapText(true);
        resultLabel.setAlignment(Pos.CENTER);
        
        fineAmountLabel = new Label();
        fineAmountLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
        fineAmountLabel.setWrapText(true);
        fineAmountLabel.setAlignment(Pos.CENTER);
        
        // Result card
        VBox resultCard = new VBox(10);
        resultCard.setAlignment(Pos.CENTER);
        resultCard.setPadding(new Insets(25));
        resultCard.setStyle(
            "-fx-background-color: rgba(30,136,229,0.06); " +
            "-fx-background-radius: 15; " +
            "-fx-border-color: #1E88E5; " +
            "-fx-border-radius: 15; " +
            "-fx-border-width: 1;"
        );
        
        resultCard.getChildren().addAll(resultLabel, fineAmountLabel);
        section.getChildren().add(resultCard);
        
        return section;
    }
    
    private HBox createFooter() {
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(15));
        
        Label footerLabel = new Label("💡 Drive safely and follow speed limits!");
        footerLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 13));
    footerLabel.setTextFill(Color.rgb(20, 40, 70, 0.9));
        
        footer.getChildren().add(footerLabel);
        return footer;
    }
    
    private void validateNumericInput(TextField field, String newValue) {
        if (!newValue.matches("\\d*\\.?\\d*")) {
            field.setText(newValue.replaceAll("[^\\d.]", ""));
        }
    }
    
    private void updateSpeedIndicator() {
        try {
            if (!speedField.getText().isEmpty() && !speedLimitField.getText().isEmpty()) {
                double speed = Double.parseDouble(speedField.getText());
                double limit = Double.parseDouble(speedLimitField.getText());
                
                if (limit > 0) {
                    double ratio = speed / limit;
                    speedIndicator.setProgress(Math.min(ratio, 2.0) / 2.0);
                    
                    // Change color based on speed (blue-themed)
                    if (ratio <= 1.0) {
                        speedIndicator.setStyle("-fx-accent: #90CAF9;"); // Light blue (OK)
                    } else if (ratio <= 1.3) {
                        speedIndicator.setStyle("-fx-accent: #42A5F5;"); // Medium blue (caution)
                    } else {
                        speedIndicator.setStyle("-fx-accent: #0D47A1;"); // Dark blue (danger)
                    }
                }
            } else {
                speedIndicator.setProgress(0);
            }
        } catch (NumberFormatException e) {
            speedIndicator.setProgress(0);
        }
    }
    
    private void calculateFine() {
        try {
            // Validate inputs
            if (speedField.getText().isEmpty() || speedLimitField.getText().isEmpty()) {
                showError("Please fill in all fields!");
                return;
            }
            
            double speed = Double.parseDouble(speedField.getText());
            double speedLimit = Double.parseDouble(speedLimitField.getText());
            
            if (speed <= 0 || speedLimit <= 0) {
                showError("Speed values must be greater than 0!");
                return;
            }
            
            double fine;
            String vehicleType;
            
            if (carRadio.isSelected()) {
                vehicleType = "Car";
                fine = calculateCarFine(speed, speedLimit);
            } else {
                vehicleType = "Bike";
                fine = calculateBikeFine(speed, speedLimit);
            }
            
            displayResult(vehicleType, speed, speedLimit, fine);
            
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values!");
        }
    }
    
    private double calculateCarFine(double speed, double speedLimit) {
        if (speed <= speedLimit) {
            return 0.0;
        }
        double excess = speed - speedLimit;
        return excess * excess * 0.5;
    }
    
    private double calculateBikeFine(double speed, double speedLimit) {
        if (speed <= speedLimit) {
            return 0.0;
        }
        return 30.0 + (speed - speedLimit);
    }
    
    private void displayResult(String vehicleType, double speed, double speedLimit, double fine) {
        resultBox.setVisible(true);
        resultBox.setManaged(true);
        
        if (fine == 0) {
            resultLabel.setText("✅ No Fine - Within Speed Limit!");
            resultLabel.setTextFill(Color.web("#1E88E5"));
            fineAmountLabel.setText("RM 0.00");
            fineAmountLabel.setTextFill(Color.web("#1E88E5"));
        } else {
            resultLabel.setText("⚠️ Speeding Violation Detected!");
            resultLabel.setTextFill(Color.rgb(244, 67, 54));
            fineAmountLabel.setText(String.format("RM %.2f", fine));
            fineAmountLabel.setTextFill(Color.rgb(244, 67, 54));
        }
        
        // Animate result appearance
        FadeTransition fade = new FadeTransition(Duration.millis(500), resultBox);
        fade.setFromValue(0);
        fade.setToValue(1);
        
        TranslateTransition slide = new TranslateTransition(Duration.millis(500), resultBox);
        slide.setFromY(20);
        slide.setToY(0);
        
        ParallelTransition parallel = new ParallelTransition(fade, slide);
        parallel.play();
        
        // Bounce animation for fine amount
        if (fine > 0) {
            ScaleTransition bounce = new ScaleTransition(Duration.millis(600), fineAmountLabel);
            bounce.setFromX(0.5);
            bounce.setFromY(0.5);
            bounce.setToX(1.0);
            bounce.setToY(1.0);
            bounce.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
            bounce.play();
        }
    }
    
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-font-family: 'Segoe UI';"
        );
        
        alert.showAndWait();
    }
    
    private void clearFields() {
        speedField.clear();
        speedLimitField.clear();
        carRadio.setSelected(true);
        resultBox.setVisible(false);
        resultBox.setManaged(false);
        speedIndicator.setProgress(0);
        speedIndicator.setStyle("-fx-accent: #90CAF9;");
        
        // Shake animation for clear action
        TranslateTransition shake = new TranslateTransition(Duration.millis(100), resultBox);
        shake.setFromX(0);
        shake.setByX(10);
        shake.setCycleCount(4);
        shake.setAutoReverse(true);
        shake.play();
    }
    
    private void addHoverEffect(RadioButton radio) {
        radio.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), radio);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        
        radio.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(200), radio);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
