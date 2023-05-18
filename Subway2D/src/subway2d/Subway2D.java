/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subway2d;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Subway2D extends Application {

    Timeline timeline = new Timeline();
    Random random = new Random();
    int[] positionNewEveryThing = {100, 300, 500};

    int cycle = 1;
    ArrayList<Position> cars = new ArrayList<>();
    ArrayList<Position> foods = new ArrayList<>();

    Image man = new Image(getClass().getResourceAsStream("/images/car7.png"));
    
    Image train = new Image(getClass().getResourceAsStream("/images/car5.png"));
    Image coin = new Image(getClass().getResourceAsStream("/images/car1.png"));

    Position positionMan = new Position(300, 400);
    int totalScore = 0;

    int color = 0;

    boolean gameOver = false;
    int speadGame = 10;

    Label label;

    void drawShapes(GraphicsContext gc) {

        cycle++;

        gc.setFill(Color.GRAY);
        gc.fillRect(0, 0, 750, 500);

        gc.setFill(Color.color(0.2, 0.2, 0.2));

        for (int i = 6; i <= 618; i += 17) {
            for (int j = 6; j <= 600; j += 17) {
                gc.fillRect(i, j, 13, 13);
            }
        }
        gc.setFill(Color.GRAY);
        gc.fillRect(20, 20, 600, 618);

//        if (color == 1) {
//            gc.setFill(Color.RED);
//        } else {
//            gc.setFill(Color.YELLOW);
//        }
//        for (int i = 100; i <= 618; i += 40) {
//            for (int j = 100; j <= 600; j += 40) {
//               gc.fillRect(100, 100, 30, 30);
//            }
//        }

        gc.setFill(Color.BLACK);
        gc.fillRect(635, 0, 225, 600);

        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        gc.fillText("Total Score : ", 640, 80);
        gc.fillText(totalScore + "", 750, 80);

        gc.drawImage(man, positionMan.getX(), positionMan.getY());

        if (gameOver) {

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 50));
            gc.fillText("Game Over", 110, 220);

        } else {
            if (cycle % 5 == 0) {

                if (cycle % 2 == 0) {
                    int newX = positionNewEveryThing[random.nextInt(positionNewEveryThing.length)];
                    cars.add(new Position(newX, 30));
                } else {
                    int newX = positionNewEveryThing[random.nextInt(positionNewEveryThing.length)];
                    foods.add(new Position(newX, 30));
                }
            }
            // to drow cars every cycle
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).setY(cars.get(i).getY() + speadGame);
                gc.drawImage(train, cars.get(i).getX(), cars.get(i).getY());

                if (positionMan.getX() == cars.get(i).getX() && positionMan.getY() == cars.get(i).getY()) {
                    gameOver = true;
                }

            }

            for (int i = 0; i < foods.size(); i++) {
                foods.get(i).setY(foods.get(i).getY() + speadGame);
                gc.drawImage(coin, foods.get(i).getX() + 5, foods.get(i).getY());
                if (positionMan.getX() == foods.get(i).getX() && positionMan.getY() <= foods.get(i).getY()+15 ) {
                    totalScore += 5;
                    foods.remove(i);
                }
            }
       }
    }

    @Override
    public void start(Stage primaryStage) {

        Canvas canvas = new Canvas(850, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Pane root = new Pane();
        root.setStyle("-fx-background-color: GRAY;");
        root.getChildren().add(canvas);

        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200), (ActionEvent event) -> {
            drawShapes(gc);
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Scene scene = new Scene(root, 850, 600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Subway 2D");
        primaryStage.setScene(scene);

       scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if(event.getSceneY() >= 100 && event.getSceneY() <= 130 && event.getSceneX()>= 100 && event.getSceneX()<= 130  ){
                color = 1;
            }
            System.out.println("mouse click detected! "+event.getSceneY());
        }
       });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {

            if (null != e.getCode()) {
                switch (e.getCode()) {

                    case RIGHT:
                        if (positionMan.getX() < 500) {
                            positionMan.setX(positionMan.getX() + 200);
                        }

                        break;

                    case LEFT:
                        if (positionMan.getX() > 100) {
                            positionMan.setX(positionMan.getX() - 200);
                        }
                        break;
                    case SPACE:
                        gameOver = false;
                        totalScore = 0;
                        cars.clear();
                        foods.clear();
                        break;

                }
            }
        });

        primaryStage.show();

    }

public static void main(String[] args) {
        launch(args);
    }
}
