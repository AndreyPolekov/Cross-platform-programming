package logic;

import graphics.FieldButton;
import graphics.GamePages;

public class Field {
    public static final int CELL_COUNT_X = 10;
    public static final int CELL_COUNT_Y = 10;

    public static final int SINGLE_SHIP_COUNT = 4;
    public static final int DOUBLE_SHIP_COUNT = 3;
    public static final int TRIPLE_SHIP_COUNT = 2;
    public static final int QUADRUPLE_SHIP_COUNT = 1;



    private Cell[][] cells = new Cell[CELL_COUNT_Y][CELL_COUNT_X];
    private Ship[] ships = new Ship[
        SINGLE_SHIP_COUNT +
        DOUBLE_SHIP_COUNT +
        TRIPLE_SHIP_COUNT +
        QUADRUPLE_SHIP_COUNT
    ];

    Field() {
        for (int y = 0; y < CELL_COUNT_Y; y++) {
            for (int x = 0; x < CELL_COUNT_X; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }
    public void setRandomShips() {

    }
}