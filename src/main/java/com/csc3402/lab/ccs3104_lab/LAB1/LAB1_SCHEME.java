package com.csc3402.lab.ccs3104_lab.LAB1;

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
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public class LAB1_SCHEME extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create a GridPane and set its properties
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(5, 5, 5, 5));
        pane.setHgap(2);
        pane.setVgap(2);

        final int SIZE = 10;
        TextField[][] fields = new TextField[SIZE][SIZE];

        // Define two alternating colors for checker pattern
        Color lightBlue = Color.LIGHTBLUE;
        Color blue = Color.CORNFLOWERBLUE;

        // Create the 10x10 grid
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
                text.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

                // Apply checkerboard color pattern
                Color bgColor = (row + col) % 2 == 0 ? lightBlue : blue;
                text.setBackground(new Background(
                        new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY)));

                fields[row][col] = text;
                pane.add(text, col, row);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);
            }
        }

        // Helper to fill grid with random 0/1 values
        Runnable fill = () -> {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    fields[r][c].setText(String.valueOf((int) (Math.random() * 2)));
                }
            }
        };
        fill.run();

        // Add title and regenerate button
        Label title = new Label("Random 10 x 10 (0 / 1) Checkerboard");
        title.setFont(Font.font(18));

        Button regen = new Button("Regenerate");
        regen.setOnAction(e -> fill.run());

        VBox root = new VBox(10, title, pane, regen);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 420, 480);
        primaryStage.setTitle("Exercise 14.7 — LAB1 Checker Matrix");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
