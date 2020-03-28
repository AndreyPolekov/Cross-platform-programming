package graphics;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import logic.Field;

public class FieldController extends StackPane {
    private FieldButton[][] fieldButtons = new FieldButton[Field.CELL_COUNT_Y][Field.CELL_COUNT_X];

    FieldController(int beginX, int beginY) {
        for (int y = 0; y < Field.CELL_COUNT_Y; y++) {
            for (int x = 0; x < Field.CELL_COUNT_X; x++) {
                fieldButtons[y][x] = new FieldButton(x, y, beginX, beginY);
                getChildren().add(fieldButtons[y][x]);
            }
        }
        this.setAlignment(Pos.TOP_LEFT);
    }
}