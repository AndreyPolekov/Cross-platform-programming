package graphics.pages;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Page {
    //private Scene scene;
    protected StackPane mainPane;

    Page() {
        mainPane = new StackPane();
        mainPane.setAlignment(Pos.TOP_LEFT);
        //scene = new Scene(mainPane);
    }

    public Scene getScene() {
        return new Scene(mainPane);
    }
}
