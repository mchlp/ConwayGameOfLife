package backend;

public enum Directions {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    TOPLEFT(-1, -1),
    TOPRIGHT(-1, 1),
    DOWNLEFT(1, -1),
    DOWNRIGHT(1, 1);

    public final int changeX;
    public final int changeY;

    Directions(int y, int x) {
        changeX = x;
        changeY = y;
    }
}
