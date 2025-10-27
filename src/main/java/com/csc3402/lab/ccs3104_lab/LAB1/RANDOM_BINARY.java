package com.csc3402.lab.ccs3104_lab.LAB1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;

public class RANDOM_BINARY extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a GridPane and set its properties
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setHgap(5);
        pane.setVgap(5);

        // Place text fields containing a centered,
        // randomly generated string of 0 or 1 in the pane
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                TextField text = new TextField();
                text.setPrefColumnCount(1);
                text.setText(String.valueOf((int)(Math.random() * 2)));
                pane.add(text, i, j);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);
            }
        }

        // Create a scene and plane it in the stage
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }
}