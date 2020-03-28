package logic;

public class Cell {
    private int x;
    private int y;

    public enum State {
        EMPTY, STRAFED, SHIP
    }
    private State state;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
        state = State.EMPTY;
    }
}
