package com.csc3402.lab.ccs3104_lab.LAB1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class LAB1_SCHEME extends Application {
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
                text.setPrefWidth(44);
                text.setPrefHeight(44);
                text.setFont(Font.font("Monospaced", 20));
                text.setAlignment(Pos.CENTER);
                text.setEditable(false);
                text.setFocusTraversable(false);
                fields[row][col] = text;
                pane.add(text, col, row);
                GridPane.setHalignment(text, HPos.CENTER);
                GridPane.setValignment(text, VPos.CENTER);
                // Apply alternating blue shades and a light hover effect
                String baseStyle = "-fx-background-color: %s; -fx-border-color: #2b5f9e; -fx-border-radius:4; -fx-background-radius:4;";
                String normal = ( (row + col) % 2 == 0 ) ? "#bfe1ff" : "#7fb7ff"; // light blue / blue
                text.setStyle(String.format(baseStyle, normal));

                // Hover effect to slightly darken the cell
                text.setOnMouseEntered(ev -> text.setStyle(String.format(baseStyle, "#6fa8ff")));
                text.setOnMouseExited(ev -> text.setStyle(String.format(baseStyle, normal)));
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

        CheckBox toggleBorders = new CheckBox("Toggle Borders");
        toggleBorders.setSelected(true);
        toggleBorders.setOnAction(e -> {
            boolean show = toggleBorders.isSelected();
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    TextField t = fields[r][c];
                    if (show) {
                        t.setStyle(t.getStyle().replace("-fx-border-width: 0;", ""));
                    } else {
                        if (!t.getStyle().contains("-fx-border-width: 0;")) {
                            t.setStyle(t.getStyle() + "-fx-border-width: 0;");
                        }
                    }
                }
            }
        });

        VBox root = new VBox(8, title, pane, regen);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 420, 480);
        primaryStage.setTitle("Exercise 14.7 — LAB1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}