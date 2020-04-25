package logic;

import java.util.Vector;

import static logic.Field.CELL_COUNT_X;
import static logic.Field.CELL_COUNT_Y;

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
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
}


