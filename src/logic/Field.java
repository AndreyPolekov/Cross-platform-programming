package logic;

import java.util.Random;
import java.util.Vector;
//import graphics.GamePages;

public class Field {
    public static final int CELL_COUNT_X = 10;
    public static final int CELL_COUNT_Y = 10;

    public static final int SINGLE_SHIP_COUNT = 4;
    public static final int DOUBLE_SHIP_COUNT = 3;
    public static final int TRIPLE_SHIP_COUNT = 2;
    public static final int QUADRUPLE_SHIP_COUNT = 1;

    public static final int SHIP_COUNT =
        SINGLE_SHIP_COUNT +
        DOUBLE_SHIP_COUNT +
        TRIPLE_SHIP_COUNT +
        QUADRUPLE_SHIP_COUNT;


    private Cell[][] cells = new Cell[CELL_COUNT_Y][CELL_COUNT_X];
    private Vector<Ship> ships = new Vector<Ship>();
    private int destroyedShipsCount = 0;

    Field() {
        for (int y = 0; y < CELL_COUNT_Y; y++) {
            for (int x = 0; x < CELL_COUNT_X; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }
    }
    public Vector<Cell> getShipEnvirons(Ship ship) {
        Vector<Cell> environs = new Vector<Cell>();
        int     countX = 3,
                countY = 3,
                startX = ship.getBaseX() - 1,
                startY = ship.getBaseY() - 1;
        if (ship.getOrientation() == Ship.Orientation.HORIZONTAL) {
            countX = ship.getSize() + 2;
        } else {
            countY = ship.getSize() + 2;
        }
        if (ship.getBaseX() == 0) {
            startX++;
            countX--;
        }
        if (ship.getBaseY() == 0) {
            startY++;
            countY--;
        }
        if (    ship.getBaseX() == CELL_COUNT_X - 1 ||
                (ship.getOrientation() == Ship.Orientation.HORIZONTAL && ship.getBaseX() + ship.getSize() == CELL_COUNT_X)) {
            countX--;
        }
        if (    ship.getBaseY() == CELL_COUNT_Y - 1 ||
                (ship.getOrientation() == Ship.Orientation.VERTICAL && ship.getBaseY() + ship.getSize() == CELL_COUNT_Y)) {
            countY--;
        }
        for (int i = 0; i < countY; i++) {
            for (int j = 0; j < countX; j++) {
                environs.add(cells[startY + i][startX + j]);
            }
        }
        return environs;
    }
    public boolean isRightShipSetting(int x, int y, int size, Ship.Orientation orientation) {
        if (orientation == Ship.Orientation.HORIZONTAL) {
            if (x + size > CELL_COUNT_X) {
                return false;
            }
        } else {
            if (y + size > CELL_COUNT_Y) {
                return false;
            }
        }
        for (Cell cell : getShipEnvirons(new Ship(x, y, size, orientation))) {
            if (cell.getState() == Cell.State.SHIP) {
                return false;
            }
        }
        return true;
    }
    public Ship getShip(int shipIndex) {
        return ships.get(shipIndex);
    }
    public void setShip(int x, int y, int size, Ship.Orientation orientation) {
        //System.out.println("set");////////////////////
        //print();
        ships.add(new Ship(x, y, size, orientation));
        for (int i = 0; i < size; i++) {
            cells[y][x].setState(Cell.State.SHIP);
            if (orientation == Ship.Orientation.HORIZONTAL) {
                x++;
            } else {
                y++;
            }
        }
        //print();///////////////
    }
    public void deleteShip(int x, int y, int size, Ship.Orientation orientation) {
        //System.out.println("delete");/////////////////////
        //System.out.println(x + "   " + y);
        //print();
        for (Ship ship :ships) {
            if (ship.getBaseX() == x && ship.getBaseY() == y) {
                ships.remove(ship);
                break;
            }
        }
        for (int i = 0; i < size; i++) {
            cells[y][x].setState(Cell.State.EMPTY);
            if (orientation == Ship.Orientation.HORIZONTAL) {
                x++;
            } else {
                y++;
            }
        }

        //print();//////////////
    }
    public void setRandomShips() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {}
            //System.out.print("+");//////////////////////////////////////////////
            //print();/////
            for (int y = 0; y < CELL_COUNT_Y; y++) {
                for (int x = 0; x < CELL_COUNT_X; x++) {
                    cells[y][x] = new Cell(x, y);
                }
            }
            ships.clear();
            Vector<Integer> cellNumbers = new Vector<Integer>();
            for (int i = 0; i < 100; i++) {
                cellNumbers.add(i);
            }
            for (int i = 4; i >= 1; i--) {
                //System.out.println(shipCount);////////////////////////////////////
                for (int j = 0; j < getShipCountWithSize(i); j++) {
                    //System.out.print("_");///////////////////////////////////////////////
                    while (cellNumbers.size() != 0) {
                        Random random = new Random();
                        int randomNumber = random.nextInt(cellNumbers.size()),
                                x = cellNumbers.get(randomNumber) % 10,
                                y = cellNumbers.get(randomNumber) / 10,
                                intRandomOrientation = random.nextInt(2);
                        Ship.Orientation orientation = intRandomOrientation == 0 ? Ship.Orientation.HORIZONTAL : Ship.Orientation.VERTICAL;
                        cellNumbers.remove(randomNumber);
                        if (isRightShipSetting(x, y, i, orientation)) {
                            setShip(x, y, i, orientation);
                            break;
                        } else {
                            if (orientation == Ship.Orientation.HORIZONTAL) {
                                orientation = Ship.Orientation.VERTICAL;
                            } else {
                                orientation = Ship.Orientation.HORIZONTAL;
                            }
                            if (isRightShipSetting(x, y, i, orientation)) {
                                setShip(x, y, i, orientation);
                                break;
                            }
                        }
                    }
                    if (ships.size() == SHIP_COUNT) {
                        ///
                        System.out.println("rand");/////////////////////////////////////////////////////////////////////
                        print();
                        ///
                        return;
                    }
                    if (cellNumbers.size() == 0) {
                        break;
                    }
                }
                if (cellNumbers.size() == 0) {
                    break;
                }
            }
        }
    }
    public int getShipCountWithSize(int shipSize) {
        if (shipSize == 4) {
            return QUADRUPLE_SHIP_COUNT;
        } else if (shipSize == 3) {
            return TRIPLE_SHIP_COUNT;
        } else if (shipSize == 2) {
            return DOUBLE_SHIP_COUNT;
        } else {
            return SINGLE_SHIP_COUNT;
        }
    }
    public Cell getCell(int x, int y) {
        return cells[y][x];
    }

    public Ship getShip(int x, int y) {
        for (Ship ship :ships) {
            if (ship.contain(x, y)) {
                return ship;
            }
        }
        return null;
    }
    public void openFireOnCell(int x, int y) {
        if (cells[y][x].getState() == Cell.State.EMPTY) {
            cells[y][x].setState(Cell.State.STRAFED);
        } else if (cells[y][x].getState() == Cell.State.SHIP) {
            Ship ship = getShip(x, y);
            ship.damage(x, y);
            //System.out.println("было повреждение, проверка уничтожения");///////////////////////////////////////
            if (ship.isDestroyed()) {
                for (Cell cell :getShipEnvirons(ship)) {
                    if (!ship.contain(cell.getX(), cell.getY())) {
                        cell.setState(Cell.State.STRAFED);
                    }
                }
                destroyedShipsCount++;
            }
        }
    }
    public boolean isAllShipsDestriyed() {
        return destroyedShipsCount == SHIP_COUNT;
    }

    ////
    public void print() {
        for (int y = 0; y < CELL_COUNT_Y; y++) {
            for (int x = 0; x < CELL_COUNT_X; x++) {
                if (cells[y][x].getState() == Cell.State.EMPTY) {
                    System.out.print(".   ");
                } else if (cells[y][x].getState() == Cell.State.SHIP) {
                    System.out.print("#   ");
                } else {
                    System.out.print("-   ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    //////
}