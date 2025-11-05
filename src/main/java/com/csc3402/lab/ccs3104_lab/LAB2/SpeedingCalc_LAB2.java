package com.csc3402.lab.ccs3104_lab.LAB2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SpeedingCalc_LAB2 extends Application {

    private TextField speedInput;
    private TextField limitInput;
    private TextField fineOutput;
    private RadioButton carRadioButton;
    private RadioButton bikeRadioButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Speed Fine Calculator");

        // Create a grid pane for layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Label title = new Label("Speed Fine Calculator");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        // Input fields
        Label speedLabel = new Label("Speed (km/h):");
        grid.add(speedLabel, 0, 1);
        speedInput = new TextField();
        grid.add(speedInput, 1, 1);

        Label limitLabel = new Label("Speed Limit (km/h):");
        grid.add(limitLabel, 0, 2);
        limitInput = new TextField();
        grid.add(limitInput, 1, 2);

        // Radio buttons
        Label vehicleLabel = new Label("Vehicle Type:");
        grid.add(vehicleLabel, 0, 3);
        carRadioButton = new RadioButton("Car");
        bikeRadioButton = new RadioButton("Bike");
        ToggleGroup vehicleGroup = new ToggleGroup();
        carRadioButton.setToggleGroup(vehicleGroup);
        bikeRadioButton.setToggleGroup(vehicleGroup);
        HBox radioBox = new HBox(10, carRadioButton, bikeRadioButton);
        grid.add(radioBox, 1, 3);

        // Calculate button
        Button calculateButton = new Button("Calculate Fine");
        calculateButton.setOnAction(e -> calculateFine());

        HBox buttonBox = new HBox(calculateButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10, 0, 10, 0));
        grid.add(buttonBox, 0, 4, 3, 1);

        // Fine output
        Label fineLabel = new Label("Fine to be paid:");
        grid.add(fineLabel, 0, 5);
        fineOutput = new TextField();
        fineOutput.setEditable(false);
        grid.add(fineOutput, 1, 5);

        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void calculateFine() {
        try {
            double speed = Double.parseDouble(speedInput.getText());
            double limit = Double.parseDouble(limitInput.getText());
            double fine = 0.0;

            if (!carRadioButton.isSelected() && !bikeRadioButton.isSelected()) {
                fineOutput.setText("Please select a vehicle type.");
                return;
            }

            if (speed > limit) {
                if (carRadioButton.isSelected()) {
                    fine = (speed - limit) * (speed - limit) * 0.5;
                } else if (bikeRadioButton.isSelected()) {
                    fine = 30 + (speed - limit);
                }
                fineOutput.setText(String.format("RM %.2f", fine));
            } else {
                fineOutput.setText("No fine applicable.");
            }
        } catch (NumberFormatException ex) {
            fineOutput.setText("Invalid input. Please enter valid numbers.");
        }
    }
}