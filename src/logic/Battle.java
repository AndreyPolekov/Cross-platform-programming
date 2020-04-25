package logic;

import java.util.Random;
import java.util.Vector;

public class Battle {
    public class ComputerLogic {
        private Vector<Integer> cellNumbers = new Vector<Integer>();
        private Vector<Cell> checkableCells = new Vector<Cell>();
        private int checkingStage = 1,
                checkableCellIndex = 0;

        ComputerLogic() {
            for (int i = 0; i < 100; i++) {
                cellNumbers.add(i);
            }
        }
        //        private boolean isSelected(Cell cell) {
//            if (    cell.getState() == Cell.State.EMPTY ||
//                    (cell.getState() == Cell.State.SHIP &&
//                            fields[0].getShip(cell.getX(), cell.getY()).getState(cell.getX(), cell.getY()) == Ship.State.WORKING)) {
//                return false;
//            }
//            return true;
//        }
        private boolean isWorkingShip(Cell cell) {
            if (    cell.getState() == Cell.State.SHIP &&
                    fields[0].getShip(cell.getX(), cell.getY()).getState(cell.getX(), cell.getY()) == Ship.State.WORKING) {
                return true;
            }
            return false;
        }
        private void initializeStage1() {
            checkingStage = 1;/////////////////////////////
            checkableCells.clear();
            checkableCellIndex = 0;
            int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
                    randomCell = cellNumbers.get(randomIndex);
            cellNumbers.remove(randomIndex);
            checkableCells.add(fields[0].getCell(randomCell % 10, randomCell / 10));
        }
        private void initializeStage2() {
            checkingStage = 2;
            checkableCellIndex = 0;

            if (checkableCells.get(checkableCellIndex).getX() == 0) {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
            } else if (checkableCells.get(checkableCellIndex).getX() == 9) {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
            } else {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
            }
            if (checkableCells.get(checkableCellIndex).getY() == 0) {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
            } else if (checkableCells.get(checkableCellIndex).getY() == 9) {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
            } else {
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
            }
        }
        private void initializeStage3() {
            checkingStage = 3;
            checkableCells.set(1, checkableCells.get(checkableCellIndex));
            checkableCellIndex = 1;
            cellNumbers.removeElement(checkableCells.get(checkableCellIndex).getY() * 10 + checkableCells.get(checkableCellIndex).getX());
        }
        public Cell selectCell() {
            while (true) {
                if (checkingStage == 1) {
                    initializeStage1();
                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
                        initializeStage2();
                    }
                } else if (checkingStage == 2) {
                    if (fields[0].getShip(checkableCells.get(0).getX(), checkableCells.get(0).getY()).isDestroyed()) {
                        checkingStage = 1;
                        continue;
                    }
                    checkableCellIndex = (new Random()).nextInt(checkableCells.size() - 1) + 1;
                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
                        initializeStage3();
                        //System.out.println("\ninit stage 3: \n"+checkableCells.get(1).getX()+" "+ checkableCells.get(1).getY());///////////////
                    }
                } else if (checkingStage == 3) {
                    if (fields[0].getShip(checkableCells.get(0).getX(), checkableCells.get(0).getY()).isDestroyed()) {
                        //System.out.println("\nstage 3 (dest >2)\n");///////////////
                        checkingStage = 1;
                        continue;
                    }
                    if (checkableCells.get(checkableCellIndex).getState() != Cell.State.SHIP) {
                        if (checkableCellIndex == 1) {
                            checkableCellIndex = 0;
                            //System.out.println("ind -> 0");
                        }
                    }
                    if (checkableCells.get(0).getX() < checkableCells.get(1).getX()) {
                        if (checkableCellIndex == 1) {
                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
                                checkableCellIndex = 0;
                                //System.out.println("bound ind -> 0");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
                        } else {
                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
                                checkingStage = 1;
                                //System.out.println("bound exit");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
                        }
                    } else if (checkableCells.get(0).getX() > checkableCells.get(1).getX()) {
                        if (checkableCellIndex == 1) {
                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
                                checkableCellIndex = 0;
                                //System.out.println("bound ind -> 0");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
                        } else {
                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
                                checkingStage = 1;
                                //System.out.println("bound exit");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
                        }
                    } else if (checkableCells.get(0).getY() < checkableCells.get(1).getY()) {
                        if (checkableCellIndex == 1) {
                            if (checkableCells.get(checkableCellIndex).getY() == 9) {
                                checkableCellIndex = 0;
                                //System.out.println("bound ind -> 0");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
                        } else {
                            if (checkableCells.get(checkableCellIndex).getY() == 0) {
                                checkingStage = 1;
                                //System.out.println("bound exit");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
                        }
                    } else if (checkableCells.get(0).getY() > checkableCells.get(1).getY()) {
                        if (checkableCellIndex == 1) {
                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
                                checkableCellIndex = 0;
                                //System.out.println("bound ind -> 0");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
                        } else {
                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
                                checkingStage = 1;
                                //System.out.println("bound exit");
                                continue;
                            }
                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
                        }
                    }


                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
                        cellNumbers.removeElement(checkableCells.get(checkableCellIndex).getY() * 10 + checkableCells.get(checkableCellIndex).getX());
                    } else {
                        if (checkableCellIndex == 0) {
                            checkingStage = 1;
                            //System.out.println("ind 0   no work    "+checkableCells.get(checkableCellIndex).getX() +"_" + checkableCells.get(checkableCellIndex).getY());
                            continue;
                        }
                    }
                }



                if (    checkableCells.get(checkableCellIndex).getState() == Cell.State.EMPTY ||
                        isWorkingShip(checkableCells.get(checkableCellIndex))) {
                    return checkableCells.get(checkableCellIndex);
                }
            }
        }

        //    public int selectCellByComputer() {
//        while (true) {
//            int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
//                    randomCell = cellNumbers.get(randomIndex),
//                    x = randomCell % 10,
//                    y = randomCell / 10;
//            cellNumbers.remove(randomIndex);
//            if (    fields[0].getCell(x, y).getState() == Cell.State.EMPTY ||
//                    (fields[0].getCell(x, y).getState() == Cell.State.SHIP && fields[0].getShip(x, y).getState(x, y) == Ship.State.WORKING)) {
//                return randomCell;
//            }
//        }
//    }
    }
    public enum GameMoge {
        PvP, PvC
    }
    private GameMoge mode;

    private static Battle battle;

    private Field[] fields = new Field[2];
    private int activeFieldIndex = 1;
    private boolean isGameOver = false;
    private ComputerLogic computerLogic;

    private Battle() {
        for (int i = 0; i < 2; i++) {
            fields[i] = new Field();
        }
    }
    public static Battle get() {
        if(battle == null){
            battle = new Battle();
        }
        return battle;
    }
    public Field getField(int i) {
        return fields[i];
    }
    public void openFireOnField(int x, int y) {
        if (!fields[activeFieldIndex].openFireOnCell(x, y)) {
            activeFieldIndex = 1 - activeFieldIndex;
            System.out.println("------------------------");
        }
        if (fields[activeFieldIndex].isAllShipsDestriyed()) {
            isGameOver = true;
            battleResult(2 - activeFieldIndex);
        }
    }
    private void battleResult(int player) {
        System.out.println("Игрок " + player + " победил!");
    }
    public GameMoge getMode() {
        return mode;
    }
    public void setMode(GameMoge mode) {
        this.mode = mode;
        if (this.mode == GameMoge.PvP) {
            fields[0].setCommander(Field.Commander.PLAYER);
            fields[1].setCommander(Field.Commander.PLAYER);
        } else {
            fields[0].setCommander(Field.Commander.PLAYER);
            fields[1].setCommander(Field.Commander.COMPUTER);
            computerLogic = new ComputerLogic();
        }
    }
    public int getActiveFieldIndex() {
        return activeFieldIndex;
    }
    public boolean gameOver() {
        return isGameOver;
    }
    public ComputerLogic getComputerLogic() {
        return computerLogic;
    }




    public static void clear() {
        battle = null;
    }
}



////////////////////////////////////////////// 3, 4 bad
//package logic;
//
//import java.util.Random;
//import java.util.Vector;
//
//public class Battle {
//    public class ComputerLogic {
//        private Vector<Integer> cellNumbers = new Vector<Integer>();
//        private Vector<Cell> checkableCells = new Vector<Cell>();
//        private int checkingStage = 1,
//                checkableCellIndex = 0;
//
//        ComputerLogic() {
//            for (int i = 0; i < 100; i++) {
//                cellNumbers.add(i);
//            }
//        }
////        private boolean isSelected(Cell cell) {
////            if (    cell.getState() == Cell.State.EMPTY ||
////                    (cell.getState() == Cell.State.SHIP &&
////                            fields[0].getShip(cell.getX(), cell.getY()).getState(cell.getX(), cell.getY()) == Ship.State.WORKING)) {
////                return false;
////            }
////            return true;
////        }
//        private boolean isWorkingShip(Cell cell) {
//            if (    cell.getState() == Cell.State.SHIP &&
//                    fields[0].getShip(cell.getX(), cell.getY()).getState(cell.getX(), cell.getY()) == Ship.State.WORKING) {
//                return true;
//            }
//            return false;
//        }
//        private void initializeStage1() {
//            checkingStage = 1;/////////////////////////////
//            checkableCells.clear();
//            checkableCellIndex = 0;
//            int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
//                    randomCell = cellNumbers.get(randomIndex);
//            cellNumbers.remove(randomIndex);
//            checkableCells.add(fields[0].getCell(randomCell % 10, randomCell / 10));
//        }
//        private void initializeStage2() {
//            checkingStage = 2;
//            checkableCellIndex = 0;
//
//            if (checkableCells.get(checkableCellIndex).getX() == 0) {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
//            } else if (checkableCells.get(checkableCellIndex).getX() == 9) {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
//            } else {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
//            }
//            if (checkableCells.get(checkableCellIndex).getY() == 0) {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
//            } else if (checkableCells.get(checkableCellIndex).getY() == 9) {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
//            } else {
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
//                checkableCells.add(fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
//            }
//        }
//        private void initializeStage3() {
//            checkingStage = 3;
//            checkableCells.set(1, checkableCells.get(checkableCellIndex));
//            checkableCellIndex = 1;
//            cellNumbers.removeElement(checkableCells.get(checkableCellIndex).getY() * 10 + checkableCells.get(checkableCellIndex).getX());
//        }
//        public Cell selectCell() {
//            while (true) {
//                if (checkingStage == 1) {
//                    initializeStage1();
//                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
//                        initializeStage2();
//                    }
//                } else if (checkingStage == 2) {
//                    if (fields[0].getShip(checkableCells.get(0).getX(), checkableCells.get(0).getY()).isDestroyed()) {
//                        checkingStage = 1;
//                        continue;
//                    }
//                    checkableCellIndex = (new Random()).nextInt(checkableCells.size() - 1) + 1;
//                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
//                        initializeStage3();
//                    }
//                } else if (checkingStage == 3) {
//                    if (fields[0].getShip(checkableCells.get(0).getX(), checkableCells.get(0).getY()).isDestroyed()) {
//                        checkingStage = 1;
//                        continue;
//                    }
//                    if (!isWorkingShip(checkableCells.get(checkableCellIndex))) {
//                        if (checkableCellIndex == 1) {
//                            checkableCellIndex = 0;
//                        }
//                    }
//                    if (checkableCells.get(0).getX() < checkableCells.get(1).getX()) {
//                        if (checkableCellIndex == 1) {
//                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
//                                checkableCellIndex = 0;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
//                        } else {
//                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
//                                checkingStage = 1;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
//                        }
//                    } else if (checkableCells.get(0).getX() > checkableCells.get(1).getX()) {
//                        if (checkableCellIndex == 1) {
//                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
//                                checkableCellIndex = 0;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() - 1, checkableCells.get(checkableCellIndex).getY()));
//                        } else {
//                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
//                                checkingStage = 1;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX() + 1, checkableCells.get(checkableCellIndex).getY()));
//                        }
//                    } else if (checkableCells.get(0).getY() < checkableCells.get(1).getY()) {
//                        if (checkableCellIndex == 1) {
//                            if (checkableCells.get(checkableCellIndex).getY() == 9) {
//                                checkableCellIndex = 0;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
//                        } else {
//                            if (checkableCells.get(checkableCellIndex).getY() == 0) {
//                                checkingStage = 1;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
//                        }
//                    } else if (checkableCells.get(0).getY() > checkableCells.get(1).getY()) {
//                        if (checkableCellIndex == 1) {
//                            if (checkableCells.get(checkableCellIndex).getX() == 0) {
//                                checkableCellIndex = 0;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() - 1));
//                        } else {
//                            if (checkableCells.get(checkableCellIndex).getX() == 9) {
//                                checkingStage = 1;
//                                continue;
//                            }
//                            checkableCells.set(checkableCellIndex, fields[0].getCell(checkableCells.get(checkableCellIndex).getX(), checkableCells.get(checkableCellIndex).getY() + 1));
//                        }
//                    }
//
//
//                    if (isWorkingShip(checkableCells.get(checkableCellIndex))) {
//                        cellNumbers.removeElement(checkableCells.get(checkableCellIndex).getY() * 10 + checkableCells.get(checkableCellIndex).getX());
//                    } else {
//                        if (checkableCellIndex == 0) {
//                            checkingStage = 1;
//                            continue;
//                        }
//                    }
//                }
//
//
//
//                if (    checkableCells.get(checkableCellIndex).getState() == Cell.State.EMPTY ||
//                        isWorkingShip(checkableCells.get(checkableCellIndex))) {
//                    return checkableCells.get(checkableCellIndex);
//                }
//            }
//        }
//
//        //    public int selectCellByComputer() {
////        while (true) {
////            int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
////                    randomCell = cellNumbers.get(randomIndex),
////                    x = randomCell % 10,
////                    y = randomCell / 10;
////            cellNumbers.remove(randomIndex);
////            if (    fields[0].getCell(x, y).getState() == Cell.State.EMPTY ||
////                    (fields[0].getCell(x, y).getState() == Cell.State.SHIP && fields[0].getShip(x, y).getState(x, y) == Ship.State.WORKING)) {
////                return randomCell;
////            }
////        }
////    }
//    }
//    public enum GameMoge {
//        PvP, PvC
//    }
//    private GameMoge mode;
//
//    private static Battle battle;
//
//    private Field[] fields = new Field[2];
//    private int activeFieldIndex = 1;
//    private boolean isGameOver = false;
//    private ComputerLogic computerLogic;
//
//    private Battle() {
//        for (int i = 0; i < 2; i++) {
//            fields[i] = new Field();
//        }
//    }
//    public static Battle get() {
//        if(battle == null){
//            battle = new Battle();
//        }
//        return battle;
//    }
//    public Field getField(int i) {
//        return fields[i];
//    }
//    public void openFireOnField(int x, int y) {
//        if (!fields[activeFieldIndex].openFireOnCell(x, y)) {
//            activeFieldIndex = 1 - activeFieldIndex;
//            System.out.println("------------------------");
//        }
//        if (fields[activeFieldIndex].isAllShipsDestriyed()) {
//            isGameOver = true;
//            battleResult(2 - activeFieldIndex);
//        }
//    }
//    private void battleResult(int player) {
//        System.out.println("Игрок " + player + " победил!");
//    }
//    public GameMoge getMode() {
//        return mode;
//    }
//    public void setMode(GameMoge mode) {
//        this.mode = mode;
//        if (this.mode == GameMoge.PvP) {
//            fields[0].setCommander(Field.Commander.PLAYER);
//            fields[1].setCommander(Field.Commander.PLAYER);
//        } else {
//            fields[0].setCommander(Field.Commander.PLAYER);
//            fields[1].setCommander(Field.Commander.COMPUTER);
//            computerLogic = new ComputerLogic();
//        }
//    }
//    public int getActiveFieldIndex() {
//        return activeFieldIndex;
//    }
//    public boolean gameOver() {
//        return isGameOver;
//    }
//    public ComputerLogic getComputerLogic() {
//        return computerLogic;
//    }
//
//
//
//
//    public static void clear() {
//        battle = null;
//    }
//}


////////////////////////////////////////////////////////        rand select
//package logic;
//
//import java.util.Random;
//import java.util.Vector;
//
//public class Battle {
//    public class ComputerLogic {
//        private Vector<Integer> cellNumbers = new Vector<Integer>();
//        private Vector<Cell> checkableCells = new Vector<Cell>();
//        private int checkingStage = 1,
//                    checkableCellIndex = 0;
//
//        ComputerLogic() {
//            for (int i = 0; i < 100; i++) {
//                cellNumbers.add(i);
//            }
//        }
//        private boolean isChecked(Cell cell) {
//            if (    cell.getState() == Cell.State.EMPTY ||
//                    (cell.getState() == Cell.State.SHIP &&
//                    fields[0].getShip(cell.getX(), cell.getY()).getState(cell.getX(), cell.getY()) == Ship.State.WORKING)) {
//                return false;
//            }
//            return true;
//        }
//        public Cell selectCell() {
//            while (true) {
//
//
//                if (checkingStage == 1) {
//                    checkableCells.clear();
//                    checkableCellIndex = 0;
//                    int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
//                            randomCell = cellNumbers.get(randomIndex);
//                    checkableCells.add(fields[0].getCell(randomCell % 10, randomCell / 10));
//                    cellNumbers.remove(randomIndex);
//                }
//
//
//                if (!isChecked(checkableCells.get(checkableCellIndex))) {
//                    return checkableCells.get(checkableCellIndex);
//                } else {
//                    checkableCellIndex++;
//                }
//            }
//        }
//
//        //    public int selectCellByComputer() {
////        while (true) {
////            int     randomIndex = (new Random()).nextInt(cellNumbers.size()),
////                    randomCell = cellNumbers.get(randomIndex),
////                    x = randomCell % 10,
////                    y = randomCell / 10;
////            cellNumbers.remove(randomIndex);
////            if (    fields[0].getCell(x, y).getState() == Cell.State.EMPTY ||
////                    (fields[0].getCell(x, y).getState() == Cell.State.SHIP && fields[0].getShip(x, y).getState(x, y) == Ship.State.WORKING)) {
////                return randomCell;
////            }
////        }
////    }
//    }
//    public enum GameMoge {
//        PvP, PvC
//    }
//    private GameMoge mode;
//
//    private static Battle battle;
//
//    private Field[] fields = new Field[2];
//    private int activeFieldIndex = 1;
//    private boolean isGameOver = false;
//    private ComputerLogic computerLogic;
//
//    private Battle() {
//        for (int i = 0; i < 2; i++) {
//            fields[i] = new Field();
//        }
//    }
//    public static Battle get() {
//        if(battle == null){
//            battle = new Battle();
//        }
//        return battle;
//    }
//    public Field getField(int i) {
//        return fields[i];
//    }
//    public void openFireOnField(int x, int y) {
//        if (!fields[activeFieldIndex].openFireOnCell(x, y)) {
//            activeFieldIndex = 1 - activeFieldIndex;
//        }
//        if (fields[activeFieldIndex].isAllShipsDestriyed()) {
//            isGameOver = true;
//            battleResult(2 - activeFieldIndex);
//        }
//    }
//    private void battleResult(int player) {
//        System.out.println("Игрок " + player + " победил!");
//    }
//    public GameMoge getMode() {
//        return mode;
//    }
//    public void setMode(GameMoge mode) {
//        this.mode = mode;
//        if (this.mode == GameMoge.PvP) {
//            fields[0].setCommander(Field.Commander.PLAYER);
//            fields[1].setCommander(Field.Commander.PLAYER);
//        } else {
//            fields[0].setCommander(Field.Commander.PLAYER);
//            fields[1].setCommander(Field.Commander.COMPUTER);
//            computerLogic = new ComputerLogic();
//        }
//    }
//    public int getActiveFieldIndex() {
//        return activeFieldIndex;
//    }
//    public boolean gameOver() {
//        return isGameOver;
//    }
//    public ComputerLogic getComputerLogic() {
//        return computerLogic;
//    }
//
//
//
//
//    public static void clear() {
//        battle = null;
//    }
//}