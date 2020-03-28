package graphics;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class FieldButton extends Button {
    private static int x;
    private int y;

    FieldButton(int x, int y, int beginX, int beginY) {
        this.x = x;
        this.y = y;
        setPrefSize(GamePages.CELL_SIZE, GamePages.CELL_SIZE);
        setTranslateX(x * (GamePages.CELL_SIZE + 2) + beginX);
        setTranslateY(y * (GamePages.CELL_SIZE + 2) + beginY);
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                setDisable(true);

                //setStyle("-fx-background-color: red");
            }
        });
    }

    public static int getxx() {
        return x;
    }

}