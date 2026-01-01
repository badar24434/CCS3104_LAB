package com.csc3402.lab.ccs3104_lab.LAB6;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

public class crcle extends Application {
    int click = 0;
    int maxClick = 5;
    long startTime = System.currentTimeMillis();
    int radius = 20;
    Label lbTotTime = new Label(); // to display total time
    Pane circlePane = new Pane();
    Label lbClick = new Label("Circle clicked: 0"); // Initialize with 0 clicks

    @Override
    public void start(Stage primaryStage) {
        // Create a Pane to hold the circle
        
        // Set label font
        btClick.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        btClick.setTextFill(Color.WHITE);
        lbTotTime.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        
        // Style the click counter label
        lbClick.setFont(Font.font("Times New Roman", FontWeight.BOLD, 16));
        lbClick.setLayoutX(10);
        lbClick.setLayoutY(10);

        HBox hbox = new HBox();
        hbox.getChildren().add(lbTotTime);
        hbox.setAlignment(Pos.CENTER);

        btClick.setOnAction(e -> {
            startTime = System.currentTimeMillis();
            click = 0; // Reset click counter when starting new game
            lbClick.setText("Circle clicked: 0"); // Reset label text
            circleHandler();
        });

        BorderPane pane = new BorderPane();
        pane.setTop(clickPane);
        pane.setCenter(circlePane);
        pane.setBottom(hbox);

        // Add the click counter label to the circlePane
        circlePane.getChildren().add(lbClick);

        // Set up the scene and stage
        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Random Position Circle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void circleHandler() {
        // Check if maximum clicks reached before creating new circle
        if (click == maxClick) {
            long endTime = System.currentTimeMillis();
            long totalTime = (endTime - startTime) / 1000;
            lbTotTime.setText("Total time spend: " + totalTime + " seconds");
            return;
        }

        // Create a circle and set its properties
        Circle circle = new Circle();
        circle.setRadius(10);
        circle.setFill(Color.RED);

        // Clear previous circle but keep the label
        circlePane.getChildren().clear();
        circlePane.getChildren().add(lbClick); // Re-add the label after clearing

        Random rand = new Random();

        double x = radius + rand.nextDouble() * (circlePane.getWidth() - 2 * radius);
        double y = radius + rand.nextDouble() * (circlePane.getHeight() - 2 * radius);

        circle.setCenterX(x);
        circle.setCenterY(y);
        circle.setStroke(Color.PINK);
        circle.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));

        circle.setOnMouseClicked(e -> {
            click++;
            lbClick.setText("Circle clicked: " + click);
            circleHandler();
        });

        circlePane.getChildren().add(circle);
        circlePane.setStyle("-fx-border-color: #d15ed1; -fx-background-color: #f3d5db;");
    }

    public static void main(String[] args) {
        launch(args);
    }
}