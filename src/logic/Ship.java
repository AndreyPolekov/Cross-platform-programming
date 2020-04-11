package logic;

import java.util.Vector;

public class Ship {
    private int size,
                baseX,
                baseY;




    private enum State {
        DAMAGET, WORKING
    }
    private Vector<State> partStates = new Vector<State>();
    public enum Orientation {
        HORIZONTAL, VERTICAL
    }
    private Orientation orientation;

    Ship(int x, int y, int size, Orientation orientation) {
        this.baseX = x;
        this.baseY = y;
        this.size = size;
        this.orientation = orientation;
        for (int i = 0; i < this.size; i++) {
            partStates.add(State.WORKING);
        }
    }
    public int getBaseX() {
        return baseX;
    }
    public int getBaseY() {
        return baseY;
    }
    public int getSize() {
        return size;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public void damage(int x, int y) {
        //System.out.println("проверка повреждения");///////////////////////////////////////
        int i;
        if (orientation == Orientation.HORIZONTAL) {
            for (i = 0; x != baseX; i++, x--);
        } else {
            for (i = 0; y != baseY; i++, y--);
        }
        //System.out.println("повреждён на " + i);///////////////////////////////////////
        partStates.set(i, State.DAMAGET);

//        System.out.println("проверка повреждения");///////////////////////////////////////
//        int i;
//        for (i = 0; x != baseX || y != baseY; i++, x--, y--);
//        System.out.println("повреждён на " + i);///////////////////////////////////////
//        partStates.set(i, State.DAMAGET);
    }
    public boolean isDestroyed() {
        for (State state :partStates) {
            if (state == State.WORKING) {
                return false;
            }
        }
        return true;
    }
    public boolean contain(int x, int y) {
        if (orientation == Orientation.HORIZONTAL) {
            if (    x >= baseX &&
                    x <= baseX + size - 1 &&
                    y == baseY) {
                return true;
            }
        } else  {
            if (    y >= baseY &&
                    y <= baseY + size - 1 &&
                    x == baseX) {
                return true;
            }
        }
        return false;
    }
    //public void setState(State state) {
//        this.state = state;
//    }
}
