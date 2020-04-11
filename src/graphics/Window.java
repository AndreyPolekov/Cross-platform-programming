package graphics;

import graphics.pages.BattlePage;
import graphics.pages.MainPage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import logic.Battle;


public class Window extends Application {
    public static final int WINDOW_SIZE_X = 1200;
    public static final int WINDOW_SIZE_Y = 700;

    private static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setWidth((double) WINDOW_SIZE_X);
        this.stage.setHeight((double) WINDOW_SIZE_Y);
        this.stage.setTitle("Game Sea Battle");
        this.stage.setResizable(false);

        Battle.get().getField(0).setRandomShips();
        Battle.get().getField(1).setRandomShips();
        Window.setScene((new BattlePage()).getScene());

        this.stage.show();
    }

    //    @Override
//    public void start(Stage stage) throws Exception {
//        this.stage = stage;
//        this.stage.setWidth((double) WINDOW_SIZE_X);
//        this.stage.setHeight((double) WINDOW_SIZE_Y);
//        this.stage.setTitle("Game Sea Battle");
//        this.stage.setResizable(false);
//        this.stage.setScene((new MainPage()).getScene());
//        this.stage.show();
//    }

    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }
}

