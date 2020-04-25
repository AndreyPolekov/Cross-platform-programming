package graphics.pages;

import graphics.Window;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.Battle;
import logic.Cell;
import logic.Field;
import logic.Ship;


import static graphics.Window.WINDOW_SIZE_X;
import static graphics.Window.WINDOW_SIZE_Y;
import static logic.Field.CELL_COUNT_X;
import static logic.Field.CELL_COUNT_Y;

public class BattlePage extends Page {
    public static final int CELL_SIZE = 35;

    private class BattleField {
        private class CellButton extends ImageView {
            private int buttonX,
                        buttonY;

            CellButton(int x, int y) {
                buttonX = x;
                buttonY = y;
                setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            setDisable(true);
                            openFireAndAnalyze(buttonX, buttonY);
                        }
                    }
                });
            }
        }
        private StackPane fieldPane;
        private int     fieldPaneBeginX,
                        fieldPaneBeginY;

        private CellButton[][] buttons = new CellButton[CELL_COUNT_Y][CELL_COUNT_X];
        BattleField(int x, int y, int fieldIndex) {
            fieldPane = new StackPane();
            fieldPane.setAlignment(Pos.TOP_LEFT);

            fieldPaneBeginX = x;
            fieldPaneBeginY = y;

            for (y = 0; y < CELL_COUNT_Y; y++) {
                for (x = 0; x < CELL_COUNT_X; x++) {
                    buttons[y][x] = new CellButton(x ,y);
                    buttons[y][x].setTranslateX(fieldPaneBeginX + x * CELL_SIZE);
                    buttons[y][x].setTranslateY(fieldPaneBeginY + y * CELL_SIZE);
                    if (    Battle.get().getMode() == Battle.GameMoge.PvP ||
                            Battle.get().getField(fieldIndex).getCommander() == Field.Commander.COMPUTER) {
                        buttons[y][x].setImage(new Image("resources/Cells/ClosedCell.png"));
                    } else {
                        if ( Battle.get().getField(fieldIndex).getCell(x, y).getState() == Cell.State.EMPTY) {
                            buttons[y][x].setImage(new Image("resources/Cells/EmptyCell.png"));
                        } else {
                            buttons[y][x].setImage(new Image("resources/Cells/WorkingShipCell.png"));
                        }
                    }

                    fieldPane.getChildren().add(buttons[y][x]);
                }
            }
        }
        public void openFireAndAnalyze(int x, int y) {
            int beginningActiveFieldIndex = Battle.get().getActiveFieldIndex();

            //игровой ход
            Battle.get().openFireOnField(x, y);

            //анализ изменений
            if (Battle.get().getField(beginningActiveFieldIndex).getCell(x, y).getState() == Cell.State.STRAFED) {
                {battleFields[beginningActiveFieldIndex].buttons[y][x].setImage(new Image("resources/Cells/StrafedCell.png"));}
                if (Battle.get().getMode() == Battle.GameMoge.PvP) {
                    setActiveField();
                } else {
                    if (beginningActiveFieldIndex == 0) {
                        setActiveField();
                    } else {
                        setDisableOnAllFields();
                        openFireAndAnalyzeByComputer();
                    }
                }
            } else if (Battle.get().getField(beginningActiveFieldIndex).getCell(x, y).getState() == Cell.State.SHIP) {
                Ship ship = Battle.get().getField(beginningActiveFieldIndex).getShip(x, y);
                if (ship.isDestroyed()) {
                    for (Cell cell :Battle.get().getField(beginningActiveFieldIndex).getShipEnvirons(ship)) {
                        if (!ship.contain(cell.getX(), cell.getY())) {
                            battleFields[beginningActiveFieldIndex].buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/StrafedCell.png"));
                            battleFields[beginningActiveFieldIndex].buttons[cell.getY()][cell.getX()].setDisable(true);
                        } else {
                            battleFields[beginningActiveFieldIndex].buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/DestroyedShipCell.png"));
                        }
                    }
                    if (Battle.get().gameOver()) {
                        setDisableOnAllFields();

                        Label battleResultMessage = new Label();
                        battleResultMessage.setText("Игрок " + (2 - beginningActiveFieldIndex) + " победил!");
                        battleResultMessage.setFont(Font.font("Cambria", 32));
                        battleResultMessage.setTextFill(Color.WHITE);
                        battleResultMessage.setTranslateX(WINDOW_SIZE_X / 2 - 170);
                        battleResultMessage.setTranslateY(10);
                        mainPane.getChildren().add(battleResultMessage);

                        Button returnToMainPageButton = new Button("На главную страницу");
                        returnToMainPageButton.setTranslateX(WINDOW_SIZE_X - 150);
                        returnToMainPageButton.setTranslateY(WINDOW_SIZE_Y - 70);
                        returnToMainPageButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                Battle.clear();
                                Window.setScene((new MainPage()).getScene());
                            }
                        });
                        mainPane.getChildren().add(returnToMainPageButton);
                    }
                } else {
                    battleFields[beginningActiveFieldIndex].buttons[y][x].setImage(new Image("resources/Cells/DamagedShipCell.png"));
                }
                if (    Battle.get().getMode() == Battle.GameMoge.PvC &&
                        !Battle.get().gameOver() &&
                        beginningActiveFieldIndex == 0) {
                    openFireAndAnalyzeByComputer();
                }
            }
        }
        public void openFireAndAnalyzeByComputer() {
            //System.out.print("b   ");/////////////
            //try { Thread.sleep(3000); } catch (InterruptedException ex) {System.out.print("!!!!!!!!!!!!!");}
            //System.out.println("e");///////////////
            Cell selectedCell = Battle.get().getComputerLogic().selectCell();
            System.out.println("x="+selectedCell.getX() +"   y="+ selectedCell.getY());/////////////////////////////////////////
            openFireAndAnalyze(selectedCell.getX(), selectedCell.getY());
        }
        public StackPane getPane() {
            return fieldPane;
        }
    }
    private BattleField[] battleFields = new BattleField[2];

    public void setActiveField() {
        if (Battle.get().getActiveFieldIndex() == 1) {
            battleFields[0].getPane().setDisable(true);
            battleFields[1].getPane().setDisable(false);
        } else {
            battleFields[0].getPane().setDisable(false);
            battleFields[1].getPane().setDisable(true);
        }
    }
    public void setDisableOnAllFields() {
        for (int i = 0; i < 2; i++) {
            battleFields[i].getPane().setDisable(true);
        }
    }
    public BattlePage() {
        ImageView backgroundImage = new ImageView(new Image("resources/WaterBackground.jpg"));
        mainPane.getChildren().add(backgroundImage);

//        Label label = new Label();
//        battleResultMessage.setText("1");
//        battleResultMessage.setFont(Font.font("Cambria", 32));
//        battleResultMessage.setTextFill(Color.WHITE);
//        battleResultMessage.setTranslateX(WINDOW_SIZE_X / 2 - 170);
//        battleResultMessage.setTranslateY(10);
//        mainPane.getChildren().add(battleResultMessage);

        battleFields[0] = new BattleField(100, 200, 0);
        battleFields[1] = new BattleField(WINDOW_SIZE_X - CELL_SIZE * CELL_COUNT_X - 100, 200, 1);
        mainPane.getChildren().addAll(battleFields[0].getPane(), battleFields[1].getPane());

        setActiveField();
    }
}

//package graphics.pages;
//
//import graphics.Window;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.StackPane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Font;
//import logic.Battle;
//import logic.Cell;
//import logic.Field;
//import logic.Ship;
//
//
//import static graphics.Window.WINDOW_SIZE_X;
//import static graphics.Window.WINDOW_SIZE_Y;
//import static logic.Field.CELL_COUNT_X;
//import static logic.Field.CELL_COUNT_Y;
//
//public class BattlePage extends Page {
//    public static final int CELL_SIZE = 35;
//
//    private class BattleField {
//        private class CellButton extends ImageView {
//            private int buttonX,
//                        buttonY;
//
//            CellButton(int x, int y) {
//                buttonX = x;
//                buttonY = y;
//                setOnMousePressed(new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        if (event.getButton() == MouseButton.PRIMARY) {
//                            setDisable(true);
//
//
//                            int beginningActiveFieldIndex = Battle.get().getActiveFieldIndex();
//
//                            //игровой ход
//                            Battle.get().openFireOnField(buttonX, buttonY);
//
//                            //анализ изменений
//                            if (Battle.get().getField(beginningActiveFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.STRAFED) {
//                                setImage(new Image("resources/Cells/StrafedCell.png"));
//                                if (Battle.get().getMode() == Battle.GameMoge.PvP) {
//                                    setActiveField();
//                                } else {
//                                }
//                            } else if (Battle.get().getField(beginningActiveFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.SHIP) {
//                                Ship ship = Battle.get().getField(beginningActiveFieldIndex).getShip(buttonX, buttonY);
//                                if (ship.isDestroyed()) {
//                                    for (Cell cell :Battle.get().getField(beginningActiveFieldIndex).getShipEnvirons(ship)) {
//                                        if (!ship.contain(cell.getX(), cell.getY())) {
//                                            buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/StrafedCell.png"));
//                                            buttons[cell.getY()][cell.getX()].setDisable(true);
//                                        } else {
//                                            buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/DestroyedShipCell.png"));
//                                        }
//                                    }
//                                    if (Battle.get().gameOver()) {
//                                        setDisableOnAllFields();
//
//                                        Label battleResultMessage = new Label();
//                                        battleResultMessage.setText("Игрок " + (2 - beginningActiveFieldIndex) + " победил!");
//                                        battleResultMessage.setFont(Font.font("Cambria", 32));
//                                        battleResultMessage.setTextFill(Color.WHITE);
//                                        battleResultMessage.setTranslateX(WINDOW_SIZE_X / 2 - 170);
//                                        battleResultMessage.setTranslateY(10);
//                                        mainPane.getChildren().add(battleResultMessage);
//
//                                        Button returnToMainPageButton = new Button("На главную страницу");
//                                        returnToMainPageButton.setTranslateX(WINDOW_SIZE_X - 150);
//                                        returnToMainPageButton.setTranslateY(WINDOW_SIZE_Y - 70);
//                                        returnToMainPageButton.setOnAction(new EventHandler<ActionEvent>() {
//                                            @Override
//                                            public void handle(ActionEvent event) {
//                                                Battle.clear();
//                                                Window.setScene((new MainPage()).getScene());
//                                            }
//                                        });
//                                        mainPane.getChildren().add(returnToMainPageButton);
//                                    }
//                                } else {
//                                    setImage(new Image("resources/Cells/DamagedShipCell.png"));
//                                }
//                            }
//                        }
//                    }
//                });
//            }
//            private void openFireAndAnalyze(int x, int y) {
//
//            }
//        }
//        private StackPane fieldPane;
//        private int     fieldPaneBeginX,
//                        fieldPaneBeginY;
//
//        private CellButton[][] buttons = new CellButton[CELL_COUNT_Y][CELL_COUNT_X];
//        BattleField(int x, int y, int fieldIndex) {
//            fieldPane = new StackPane();
//            fieldPane.setAlignment(Pos.TOP_LEFT);
//
//            fieldPaneBeginX = x;
//            fieldPaneBeginY = y;
//
//            for (y = 0; y < CELL_COUNT_Y; y++) {
//                for (x = 0; x < CELL_COUNT_X; x++) {
//                    buttons[y][x] = new CellButton(x ,y);
//                    buttons[y][x].setTranslateX(fieldPaneBeginX + x * CELL_SIZE);
//                    buttons[y][x].setTranslateY(fieldPaneBeginY + y * CELL_SIZE);
//                    if (    Battle.get().getMode() == Battle.GameMoge.PvP ||
//                            Battle.get().getField(fieldIndex).getCommander() == Field.Commander.Computer) {
//                        buttons[y][x].setImage(new Image("resources/Cells/ClosedCell.png"));
//                    } else {
//                        if ( Battle.get().getField(fieldIndex).getCell(x, y).getState() == Cell.State.EMPTY) {
//                            buttons[y][x].setImage(new Image("resources/Cells/EmptyCell.png"));
//                        } else {
//                            buttons[y][x].setImage(new Image("resources/Cells/WorkingShipCell.png"));
//                        }
//                    }
//
//                    fieldPane.getChildren().add(buttons[y][x]);
//                }
//            }
//        }
//        public StackPane getPane() {
//            return fieldPane;
//        }
//    }
//    private BattleField[] battleFields = new BattleField[2];
//
//    public void setActiveField() {
//        if (Battle.get().getActiveFieldIndex() == 1) {
//            battleFields[0].getPane().setDisable(true);
//            battleFields[1].getPane().setDisable(false);
//        } else {
//            battleFields[0].getPane().setDisable(false);
//            battleFields[1].getPane().setDisable(true);
//        }
//    }
//    public void setDisableOnAllFields() {
//        for (int i = 0; i < 2; i++) {
//            battleFields[i].getPane().setDisable(true);
//        }
//    }
//    public BattlePage() {
//        ImageView backgroundImage = new ImageView(new Image("resources/WaterBackground.jpg"));
//        mainPane.getChildren().add(backgroundImage);
//
//        battleFields[0] = new BattleField(100, 200, 0);
//        battleFields[1] = new BattleField(WINDOW_SIZE_X - CELL_SIZE * CELL_COUNT_X - 100, 200, 1);
//        mainPane.getChildren().addAll(battleFields[0].getPane(), battleFields[1].getPane());
//
//        setActiveField();
//    }
//}