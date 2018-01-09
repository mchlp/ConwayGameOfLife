package backend;

import java.awt.*;

public class Cell {

    private boolean filled;
    private Point pos;
    private Cell[][] grid;

    public Cell(Point pos, Cell[][] grid) {
        filled = false;
        this.pos = pos;
        this.grid = grid;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void update(Cell[][] newGrid) {
        int liveCount = 0;

        for (Directions directions : Directions.values()) {
            Cell checkCell = grid[(pos.x + directions.changeX + (grid.length-1))%(grid.length-1)][(pos.y + directions.changeY + (grid[0].length-1))%(grid[0].length-1)];
            if (checkCell.isFilled()) {
                liveCount++;
            }
        }

        Cell newCell = new Cell(new Point(pos.x, pos.y), newGrid);

        newGrid[pos.x][pos.y] = newCell;

        if ((filled && liveCount == 2 || liveCount == 3) || (!filled && liveCount == 3)) {
            newCell.setFilled(true);
        }
    }
}
