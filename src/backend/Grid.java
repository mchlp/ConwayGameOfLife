package backend;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class Grid {

    private static final int NUM_COLS = 50;
    private static final int NUM_ROWS = 50;
    private static final double UPDATE_TIME = 0.1;

    private Cell[][] grid;
    private Canvas canvas;
    private double cellWidth;
    private double cellHeight;
    private double lastUpdate;

    public Grid(Canvas canvas) {
        this.canvas = canvas;
        cellWidth = canvas.getWidth()/NUM_COLS;
        cellHeight = canvas.getHeight()/NUM_ROWS;
        grid = new Cell[NUM_COLS][NUM_ROWS];

        for (int i = 0; i< NUM_COLS; i++) {
            for (int j = 0; j< NUM_ROWS; j++) {
                grid[i][j] = new Cell(new Point(i, j), grid);
            }
        }
        draw();
    }

    public void clicked(double x, double y) {
        int xPos = (int) (x/cellWidth);
        int yPos = (int) (y/cellHeight);
        grid[xPos][yPos].setFilled(true);
        draw();
    }

    private void draw() {

        GraphicsContext gc = canvas.getGraphicsContext2D();

        for (int i = 0; i < NUM_COLS; i++) {
            for (int j = 0; j < NUM_ROWS; j++) {
                gc.setStroke(Color.BLACK);
                if (grid[i][j].isFilled()) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(Color.WHITE);
                }
                gc.fillRect(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
                gc.strokeRect(cellWidth * i, cellHeight * j, cellWidth, cellHeight);
            }
        }
    }

    public void update(double deltaTime) {


        if (lastUpdate >= UPDATE_TIME) {

            lastUpdate += deltaTime;
            Cell[][] newGrid = new Cell[NUM_COLS][NUM_ROWS];
            for (int i = 0; i < NUM_COLS; i++) {
                for (int j = 0; j < NUM_ROWS; j++) {
                    grid[i][j].update(newGrid);
                }
            }

            grid = newGrid;
            draw();

            lastUpdate = 0;
        } else {
            lastUpdate = UPDATE_TIME;
        }
    }

}
