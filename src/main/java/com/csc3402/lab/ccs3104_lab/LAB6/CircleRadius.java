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


public class CircleRadius extends Application {


   int click=0;
   int maxClick=5;
   long startTime = System.currentTimeMillis();
   int radius=20;
   Label lbTotTime= new Label();//to display total time
   Pane circlePane = new Pane();
   Label lbClick = new Label();




   @Override
   public void start(Stage primaryStage) {




       // Create a Pane to hold the circle
       Pane clickPane = new Pane();
       Button btClick= new Button("Start Game!");
       clickPane.getChildren().add(btClick);
       btClick.setLayoutX(145);
       btClick.setLayoutY(10);
       btClick.setStyle("-fx-background-color: #79d879");
       clickPane.setPadding(new Insets(5,5,5,5));


       //set label font
       btClick.setFont(Font.font("Times New Roman",
               FontWeight.BOLD, 20));
       btClick.setTextFill(Color.WHITE);
       lbTotTime.setFont(Font.font("Times New Roman",
               FontWeight.BOLD, 20));


       HBox hbox= new HBox();
       hbox.getChildren().add(lbTotTime);
       hbox.setAlignment(Pos.CENTER);


       btClick.setOnAction(e->{
           startTime = System.currentTimeMillis();
           circleHandler();
       });


       BorderPane pane= new BorderPane();
       pane.setTop(clickPane);
       pane.setCenter(circlePane);
       pane.setBottom(hbox);

       // Set up the scene and stage
       Scene scene = new Scene(pane, 400, 400);
       primaryStage.setTitle("Random Position Circle");
       primaryStage.setScene(scene);
       primaryStage.show();  // Show the primary stage (window)
   }


   public void circleHandler(){




       // Create a circle and set its properties
       Circle circle = new Circle();
       circle.setRadius(10);// Set the radius of the circle to 10
       circle.setFill(Color.RED);//initial circle colour


           if(click==maxClick){
               long endTime=System.currentTimeMillis();
               long totalTime= (endTime-startTime)/1000;
               lbTotTime.setText("Total time spend: "+totalTime+" seconds");
               return;//end the method


           }


               circlePane.getChildren().clear();//only one circle at a time
               Random rand= new Random();//to set the random coordinate of the circle
               double x = radius + rand.nextDouble() * (circlePane.getWidth() - 2 * radius);
               double y = radius + rand.nextDouble() * (circlePane.getHeight() - 2 * radius);


               circle.setCenterX(x);
               circle.setCenterY(y);
               circle.setStroke(Color.PINK);
               circle.setFill(Color.color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble()));


               circle.setOnMouseClicked(e-> {
                   click++;
                   lbClick.setText("Circle clicked: " + click); //display the circle clicked
                   circleHandler();});
                   circlePane.getChildren().add(circle);
                   circlePane.setStyle("-fx-border-color: #d15ed1; -fx-background-color: #f3d5db;");
                   Label clickLabel = new Label("Circle clicked: " + click);
                   clickLabel.setLayoutX(10); // Position the label
                   clickLabel.setLayoutY(10);
                   circlePane.getChildren().add(clickLabel);
   }
   public static void main(String[] args) {
       launch(args);
   }
}
