package frontend;

import backend.Grid;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;


public class Window extends Application {

    private boolean paused;
    private Grid grid;
    private long prevTime;
    private Canvas canvas;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root);

        canvas = new Canvas(800, 800);
        root.getChildren().add(canvas);

        paused = true;
        grid = new Grid(canvas);
        prevTime = System.nanoTime();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double deltaTime = (now - prevTime) / 1E12;
                onUpdate(deltaTime);
            }
        };

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case P:
                        paused = !paused;
                }
            }
        });

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (paused) {
                    grid.clicked(event.getX(), event.getY());
                }
            }
        });

        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (paused) {
                    if (event.getX() > 0 && event.getX() < canvas.getWidth() &&
                            event.getY() > 0 && event.getY() < canvas.getHeight()) {
                        grid.clicked(event.getX(), event.getY());
                    }
                }
            }
        });


        timer.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void onUpdate(double deltaTime) {
        if (!paused) {
            grid.update(deltaTime);
        }
    }
}
