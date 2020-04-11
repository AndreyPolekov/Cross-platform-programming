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
import logic.Battle;
import logic.Cell;
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
                super(new Image("resources/Cells/ClosedCell.png"));
                buttonX = x;
                buttonY = y;
                setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            setDisable(true);//System.out.println("+");

                            //игровой ход
                            Battle.get().openFireOnField(activeFieldIndex, buttonX, buttonY);

                            //System.out.println("Логика пройдена");
                            //Battle.get().getField(activeFieldIndex).print();//////////////////////////////////////////

                            //анализ изменений
                            if (Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.STRAFED) {
                                setImage(new Image("resources/Cells/StrafedCell.png"));
                                changeActiveField();
                            } else if (Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.SHIP) {
                                Ship ship = Battle.get().getField(activeFieldIndex).getShip(buttonX, buttonY);
                                if (ship.isDestroyed()) {
                                    for (Cell cell :Battle.get().getField(activeFieldIndex).getShipEnvirons(ship)) {
                                        if (!ship.contain(cell.getX(), cell.getY())) {
                                            buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/StrafedCell.png"));
                                            buttons[cell.getY()][cell.getX()].setDisable(true);
                                        } else {
                                            buttons[cell.getY()][cell.getX()].setImage(new Image("resources/Cells/DestroyedShipCell.png"));
                                        }
                                    }
                                    if (Battle.get().getField(activeFieldIndex).isAllShipsDestriyed()) {
                                        setDisableOnAllFields();

                                        Label battleResultMessage = new Label();
                                        if (activeFieldIndex == 1) {
                                            battleResultMessage.setText("Игрок 1 победил!");
                                        } else {
                                            battleResultMessage.setText("Игрок 2 победил!");
                                        }
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
                                    setImage(new Image("resources/Cells/DamagedShipCell.png"));
                                }
                            }
//                            if (Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.EMPTY) {
//                                setImage(new Image("resources/Cells/StrafedCell.png"));
//                                Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).setState(Cell.State.STRAFED);
//                                changeActiveField();
//                            } else if (Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).getState() == Cell.State.SHIP) {
//                                //setImage(new Image("resources/Cells/WorkingShipCell.png"));
//                                //Battle.get().getField(activeFieldIndex).getCell(buttonX, buttonY).setState(Cell.State.STRAFED);
//                            }
                        }
                    }
                });
            }
        }
        private StackPane fieldPane;
        private int     fieldPaneBeginX,
                        fieldPaneBeginY;

        private CellButton[][] buttons = new CellButton[CELL_COUNT_Y][CELL_COUNT_X];
        BattleField(int x, int y) {
            fieldPane = new StackPane();
            fieldPane.setAlignment(Pos.TOP_LEFT);

            fieldPaneBeginX = x;
            fieldPaneBeginY = y;

            for (y = 0; y < CELL_COUNT_Y; y++) {
                for (x = 0; x < CELL_COUNT_X; x++) {
                    buttons[y][x] = new CellButton(x ,y);
                    buttons[y][x].setTranslateX(fieldPaneBeginX + x * CELL_SIZE);
                    buttons[y][x].setTranslateY(fieldPaneBeginY + y * CELL_SIZE);
                    fieldPane.getChildren().add(buttons[y][x]);
                }
            }
        }
        public StackPane getPane() {
            return fieldPane;
        }
    }
    private BattleField[] battleFields = new BattleField[2];
    private int activeFieldIndex = 1;
    public void changeActiveField() {
        if (activeFieldIndex == 1) {
            battleFields[0].getPane().setDisable(false);
            battleFields[1].getPane().setDisable(true);
            activeFieldIndex = 0;
        } else {
            battleFields[0].getPane().setDisable(true);
            battleFields[1].getPane().setDisable(false);
            activeFieldIndex = 1;
        }
    }
    public void setDisableOnAllFields() {
        for (int i = 0; i < 2; i++) {
            battleFields[i].getPane().setDisable(true);
        }
    }
    public BattlePage() {
        battleFields[0] = new BattleField(100, 200);
        battleFields[1] = new BattleField(WINDOW_SIZE_X - CELL_SIZE * CELL_COUNT_X - 100, 200);
        mainPane.getChildren().addAll(battleFields[0].getPane(), battleFields[1].getPane());
        battleFields[0].getPane().setDisable(true);
    }
}
