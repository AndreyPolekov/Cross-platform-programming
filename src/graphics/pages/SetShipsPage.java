package graphics.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import logic.Battle;

import graphics.Window;
import logic.Ship;

import static graphics.Window.WINDOW_SIZE_X;
import static graphics.Window.WINDOW_SIZE_Y;
import static graphics.pages.BattlePage.CELL_SIZE;
import static logic.Field.*;
import static logic.Ship.Orientation.HORIZONTAL;
import static logic.Ship.Orientation.VERTICAL;

public class SetShipsPage extends Page {
    private static int commanderIndex;

    static class SetShipsField {
        private static SetShipsField setShipsField;
        private StackPane fieldPane;
        private int     fieldPaneBeginX = 100,
                        fieldPaneBeginY = 200;

        private ImageView[][] cellsImages = new ImageView[CELL_COUNT_Y][CELL_COUNT_X];

        private SetShipsField() {
            fieldPane = new StackPane();
            fieldPane.setAlignment(Pos.TOP_LEFT);

            for (int y = 0; y < CELL_COUNT_Y; y++) {
                for (int x = 0; x < CELL_COUNT_X; x++) {
                    cellsImages[y][x] = new ImageView(new Image("resources/Cells/EmptyCell.png"));
                    cellsImages[y][x].setTranslateX(fieldPaneBeginX + x * CELL_SIZE);
                    cellsImages[y][x].setTranslateY(fieldPaneBeginY + y * CELL_SIZE);

                    fieldPane.getChildren().add(cellsImages[y][x]);
                }
            }
        }
        public static SetShipsField get() {
            if(setShipsField == null){
                setShipsField = new SetShipsField();
            }
            return setShipsField;
        }
        public boolean isTarget(int x, int y) {
            if (    (x >= fieldPaneBeginX && x <=  fieldPaneBeginX + CELL_COUNT_X * CELL_SIZE) &&
                    (y >= fieldPaneBeginY && y <=  fieldPaneBeginY + CELL_COUNT_Y * CELL_SIZE)) {
                return true;
            }
            return false;
        }
        public int getCellXByIndex(int x) {
            return fieldPaneBeginX + CELL_SIZE * x;
        }
        public int getCellYByIndex(int y) {
            return fieldPaneBeginY + CELL_SIZE * y;
        }
        public int getCellX(int x) {
            return fieldPaneBeginX + CELL_SIZE * getCellIndexX(x);
        }
        public int getCellY(int y) {
            return fieldPaneBeginY + CELL_SIZE * getCellIndexY(y);
        }
        public int getCellIndexX(int x) {
            return (x - fieldPaneBeginX) / CELL_SIZE;
        }
        public int getCellIndexY(int y) {
            return (y - fieldPaneBeginY) / CELL_SIZE;
        }
        public static void clear() {
            setShipsField = null;
        }
        public StackPane getPane() {
            return fieldPane;
        }
    }
    static class DraggableShips {
        private class DraggableShip extends ImageView {
            private boolean isLeftButtonDown = false,
                            isSetted = false;
            private Ship.Orientation    orientation = HORIZONTAL,
                                        selectedShipBeginOrientation;
            private int size,
                        selectedShipBeginX,
                        selectedShipBeginY;
            DraggableShip(int shipSize) {
            //DraggableShip(Image image) {
                this.size = shipSize;
                setImage(new Image("resources/Ships/Horizontal" + getStringSize() + "Ship.png"));
                setOnScroll(new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        if (isLeftButtonDown) {
                            if (orientation == HORIZONTAL) {
                                changeOrientation(VERTICAL);
                            } else {
                                changeOrientation(HORIZONTAL);
                            }
                        }
                    }
                });
                setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            isLeftButtonDown = true;
                            selectedShipBeginX = (int) getTranslateX();
                            selectedShipBeginY = (int) getTranslateY();
                            selectedShipBeginOrientation = orientation;
                            if (isSetted == true) {
                                Battle.get().getField(commanderIndex).deleteShip(
                                        SetShipsField.get().getCellIndexX(selectedShipBeginX + 1),
                                        SetShipsField.get().getCellIndexY(selectedShipBeginY + 1),
                                        size,
                                        orientation);
                            }
                        }
                    }
                });
                setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            ((ImageView)(event.getSource())).setTranslateX(event.getSceneX() - 17);
                            ((ImageView)(event.getSource())).setTranslateY(event.getSceneY() - 17);
                        }
                    }
                });
                setOnMouseReleased(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getButton() == MouseButton.PRIMARY) {
                            isLeftButtonDown = false;
                            if (SetShipsField.get().isTarget((int)event.getSceneX(), (int)event.getSceneY())) {
                                if (Battle.get().getField(commanderIndex).isRightShipSetting(
                                        SetShipsField.get().getCellIndexX((int)event.getSceneX()),
                                        SetShipsField.get().getCellIndexY((int)event.getSceneY()),
                                        size,
                                        orientation)) {

                                    Battle.get().getField(commanderIndex).setShip(
                                            SetShipsField.get().getCellIndexX((int)event.getSceneX()),
                                            SetShipsField.get().getCellIndexY((int)event.getSceneY()),
                                            size,
                                            orientation);
                                    setTranslateX(SetShipsField.get().getCellX((int)event.getSceneX()));
                                    setTranslateY(SetShipsField.get().getCellY((int)event.getSceneY()));
                                    isSetted = true;
                                    return;
                                }
                            }
                            if (isSetted == true) {
                                Battle.get().getField(commanderIndex).setShip(
                                        SetShipsField.get().getCellIndexX(selectedShipBeginX + 1),
                                        SetShipsField.get().getCellIndexY(selectedShipBeginY + 1),
                                        size,
                                        selectedShipBeginOrientation);
                            }
                            setTranslateX(selectedShipBeginX);
                            setTranslateY(selectedShipBeginY);
                            if (orientation != selectedShipBeginOrientation) {
                                if (orientation == HORIZONTAL) {
                                    changeOrientation(VERTICAL);
                                } else {
                                    changeOrientation(HORIZONTAL);
                                }
                            }
                        }
                    }
                });
            }
            public String getStringSize() {
                if (size == 4) {
                    return "Quadruple";
                } else if (size == 3) {
                    return "Triple";
                } else if (size == 2) {
                    return "Double";
                } else {
                    return "Single";
                }
            }
            private void changeOrientation(Ship.Orientation orientation) {
                this.orientation = orientation;
                if (orientation == HORIZONTAL) {
                    setImage(new Image("resources/Ships/Horizontal" + getStringSize() + "Ship.png"));
                } else {
                    setImage(new Image("resources/Ships/Vertical" + getStringSize() + "Ship.png"));
                }
            }
            public int getSize() {
                return size;
            }
            public void setOnField() {
                isSetted = true;
            }
            public void setSize(int size) {
                this.size = size;
            }
        }

        private static DraggableShips draggableShips;
        private StackPane shipsPane;
        private DraggableShip[] shipsImages = new DraggableShip[SHIP_COUNT];
        private int shipIndex = 0,
                    shipsPaneBeginX = WINDOW_SIZE_X - 450,
                    shipsPaneBeginY = 200,
                    shipsDistance = 80,
                    shipsInterval = 50;

        private DraggableShips() {
            shipsPane = new StackPane();
            shipsPane.setAlignment(Pos.TOP_LEFT);

            shipIndex = 0;
            for (int i = 4; i >= 1; i--) {
                for (int j = 0; j < Battle.get().getField(commanderIndex).getShipCountWithSize(i); j++, shipIndex++) {
                    shipsImages[shipIndex] = new DraggableShip(i);
                    shipsImages[shipIndex].setTranslateX(shipsPaneBeginX + j * (CELL_SIZE * shipsImages[shipIndex].getSize() + shipsInterval));
                    shipsImages[shipIndex].setTranslateY(shipsPaneBeginY + (4 - i) * shipsDistance);
                    shipsPane.getChildren().add(shipsImages[shipIndex]);
                }
            }
        }
        public void showDraggableShips() {
            shipIndex = 0;
            for (int i = 4; i >= 1; i--) {
                for (int j = 0; j < Battle.get().getField(commanderIndex).getShipCountWithSize(i); j++, shipIndex++) {
                    shipsImages[shipIndex].setTranslateX(SetShipsField.get().getCellXByIndex(Battle.get().getField(commanderIndex).getShip(shipIndex).getBaseX()));
                    shipsImages[shipIndex].setTranslateY(SetShipsField.get().getCellYByIndex(Battle.get().getField(commanderIndex).getShip(shipIndex).getBaseY()));
                    shipsImages[shipIndex].setOnField();
                    shipsImages[shipIndex].changeOrientation(Battle.get().getField(commanderIndex).getShip(shipIndex).getOrientation());
                }
            }
        }
        public boolean isAllShipsSetted() {
            for (DraggableShip ship :shipsImages) {
                if (!ship.isSetted) {
                    return false;
                }
            }
            return true;
        }
        public static DraggableShips get() {
            if(draggableShips == null){
                draggableShips = new DraggableShips();
            }
            return draggableShips;
        }
        public static void clear() {
            draggableShips = null;
        }
        public StackPane getPane() {
            return shipsPane;
        }
    }

    SetShipsPage(int newCommanderIndex) {
        ImageView backgroundImage = new ImageView(new Image("resources/WaterBackground.jpg"));
        mainPane.getChildren().add(backgroundImage);

        this.commanderIndex = newCommanderIndex;
        mainPane.getChildren().add(SetShipsField.get().getPane());
        mainPane.getChildren().add(DraggableShips.get().getPane());

        Button nextButton = new Button("Далее");
        nextButton.setTranslateX(WINDOW_SIZE_X - 70);
        nextButton.setTranslateY(WINDOW_SIZE_Y - 50);
        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!DraggableShips.get().isAllShipsSetted()) {
                    System.out.println("Not all ships is setted");
                    return;
                }
                SetShipsField.clear();
                DraggableShips.clear();
                if (Battle.get().getMode() == Battle.GameMoge.PvC) {
                    Battle.get().getField(++commanderIndex).setRandomShips();
                    Window.setScene((new BattlePage()).getScene());
                } else {
                    if (commanderIndex == 0) {
                        Window.setScene((new SetShipsPage(++commanderIndex)).getScene());
                    } else {
                        Window.setScene((new BattlePage()).getScene());
                    }
                }
            }
        });
        mainPane.getChildren().add(nextButton);

        Button randomSetShipsButton = new Button("Random");
        randomSetShipsButton.setTranslateX(WINDOW_SIZE_X - 340);
        randomSetShipsButton.setTranslateY(WINDOW_SIZE_Y - 50);
        randomSetShipsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.get().getField(commanderIndex).setRandomShips();
                DraggableShips.get().showDraggableShips();
            }
        });
        mainPane.getChildren().add(randomSetShipsButton);
    }
}
