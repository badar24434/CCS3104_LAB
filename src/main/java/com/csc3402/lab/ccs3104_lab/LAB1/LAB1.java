package com.csc3402.lab.ccs3104_lab;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LAB1 extends Application {
    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        // Create a GridPane and set its properties
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setHgap(5);
        pane.setVgap(5);
        // Create and place text fields containing a centered,
        // randomly generated 0 or 1 in the pane. Make them
        // non-editable and use a monospace font for alignment.
        final int SIZE = 10;
        TextField[][] fields = new TextField[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                TextField text = new TextField();
                text.setPrefColumnCount(1);
                text.setPrefWidth(36);
                text.setPrefHeight(36);
                text.setFont(Font.font("Monospaced", 18));
                text.setAlignment(Pos.CENTER);
                text.setEditable(false);
                text.setFocusTraversable(false);
                fields[row][col] = text;
                pane.add(text, col, row);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);
            }
        }

        // Helper to fill the grid with random 0/1 values
        Runnable fill = () -> {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    fields[r][c].setText(String.valueOf((int) (Math.random() * 2)));
                }
            }
        };
        fill.run();

        // Add a simple title and a regenerate button
        Label title = new Label("Random 10 x 10 (0 / 1)");
        title.setFont(Font.font(18));

        Button regen = new Button("Regenerate");
        regen.setOnAction(e -> fill.run());

        VBox root = new VBox(8, title, pane, regen);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 420, 480);
        primaryStage.setTitle("Exercise 14.7 — LAB1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}