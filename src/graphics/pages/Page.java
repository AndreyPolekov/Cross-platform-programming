package graphics.pages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Page {
    protected StackPane mainPane;

    Page() {
        mainPane = new StackPane();
        mainPane.setAlignment(Pos.TOP_LEFT);
    }

    public Scene getScene() {
        return new Scene(mainPane);
    }
}
