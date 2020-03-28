package graphics;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class FieldController extends StackPane {
    private FieldButton[][] fieldButtons = new FieldButton[GamePages.CELL_COUNT_Y][GamePages.CELL_COUNT_X];

    FieldController(int beginX, int beginY) {
        for (int y = 0; y < GamePages.CELL_COUNT_Y; y++) {
            for (int x = 0; x < GamePages.CELL_COUNT_X; x++) {
                fieldButtons[y][x] = new FieldButton(x, y, beginX, beginY);
                getChildren().add(fieldButtons[y][x]);
            }
        }
        this.setAlignment(Pos.TOP_LEFT);
    }
}