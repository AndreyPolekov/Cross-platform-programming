package graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import logic.Battle;

import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.Thread.sleep;
import static logic.Battle.GameMoge;
import static logic.Field.CELL_COUNT_X;


public class GamePages extends Application {
    public static final int WINDOW_SIZE_X = 650;
    public static final int WINDOW_SIZE_Y = 400;

    public static final int CELL_SIZE = 20;

    private Stage stage;
    private StackPane stackPane;

    //private Scene scene;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.stage.setWidth((double) WINDOW_SIZE_X);
        this.stage.setHeight((double) WINDOW_SIZE_Y);
        this.stage.setTitle("Game Sea Battle");
        this.stage.setResizable(false);
        this.stage.setScene(mainPage());
        this.stage.show();
    }

    private Scene mainPage() {
        stackPane = new StackPane();
        //stackPane.setAlignment(Pos.TOP_LEFT);

        ImageView backgroundImage = new ImageView(new Image("resources/Ship.png"));
        stackPane.getChildren().add(backgroundImage);

        Button startGameButton = new Button("Играть");
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.getBattle().setMode(GameMoge.PvP);
                stage.setScene(setShipsPage(0));
            }
        });
        stackPane.getChildren().add(startGameButton);

        return new Scene(stackPane);
    }

    private Scene setShipsPage(int playerIndex) {
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_LEFT);







        //Battle.getBattle().getField(playerIndex);
        //init


        Button nextButton = new Button("Далее");
        nextButton.setTranslateX(WINDOW_SIZE_X - 70);
        nextButton.setTranslateY(WINDOW_SIZE_Y - 50);

        if (Battle.getBattle().getMode() == GameMoge.PvE) {
            nextButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Battle.getBattle().getField(1).setRandomShips();
                    stage.setScene(BattlePage());
                }
            });
        } else {
            if (playerIndex++ == 0) {
                nextButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.setScene(setShipsPage(1));
                    }
                });
            } else {
                nextButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        stage.setScene(BattlePage());
                    }
                });
            }
        }
        stackPane.getChildren().add(nextButton);

        Button randomSetShipsButton = new Button("Random");
        randomSetShipsButton.setTranslateX(WINDOW_SIZE_X - 140);
        randomSetShipsButton.setTranslateY(WINDOW_SIZE_Y - 50);
        randomSetShipsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ///goto logic
            }
        });
        stackPane.getChildren().add(randomSetShipsButton);

        return new Scene(stackPane);
    }

    private Scene BattlePage() {
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.TOP_LEFT);


        FieldController player1Controller = new FieldController(50, 100);
        FieldController player2Controller = new FieldController(WINDOW_SIZE_X - CELL_SIZE * CELL_COUNT_X - 50, 100);
        stackPane.getChildren().addAll(player2Controller, player1Controller);

        return new Scene(stackPane);
    }
}


//package graphics;
//
//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Pos;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.StackPane;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//
//import static java.lang.Thread.sleep;
//
//public class GamePages extends Application {
//    public static final int SCENE_SIZE_X = 650;
//    public static final int SCENE_SIZE_Y = 400;
//
//    public static final int CELL_SIZE = 20;
//
//    private Stage stage;
//    private StackPane stackPane;
//    private Scene scene;
//
//    public static void main(String[] args) {
//        Application.launch(args);
//    }
//
//    @Override
//    public void start(Stage stage) throws Exception {
//        this.stage = stage;
//        stage.setTitle("Game Sea Battle");
//        stage.setResizable(false);
//        mainPage();
//    }
//
//    private void mainPage() throws FileNotFoundException {
//        ImageView imageView = new ImageView(new Image("resources/Ship.png"));
//        Button startGameButton = new Button("Играть");
//
//        stackPane = new StackPane();
//        stackPane.getChildren().add(imageView);
//        stackPane.getChildren().add(startGameButton);
//
//        scene = new Scene(stackPane, SCENE_SIZE_X, SCENE_SIZE_Y);
//        stage.setScene(scene);
//        stage.show();
//
//        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                PvPsetShipsPage();
//            }
//        });
//    }
//
//    private void PvPsetShipsPage() {
//        //////////////////////////////////////////////////////////////////////////////////////
//        stackPane = new StackPane();
//        stackPane.setAlignment(Pos.TOP_LEFT);
//
//        StackPane setShipButtonsPane = new StackPane();
//        setShipButtonsPane.setAlignment(Pos.TOP_LEFT);
//        final Button[] setShipButtons = new Button[4];
//        //int a = 4;
//        for (int i = 0; i < 4; i++) {
//            setShipButtons[i] = new Button(String.valueOf(4 - i));
//            setShipButtons[i].setTranslateX(550);
//            setShipButtons[i].setTranslateY(100 + i * 30);
//            setShipButtons[i].setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//
////                    if (false) {
////                        System.out.println("q");
////                        //a--;
////                    } else {
////                        setShipButtons[i].setDisable(true);
////                    }
//                }
//            });
//            setShipButtonsPane.getChildren().add(setShipButtons[i]);
//        }
//        stackPane.getChildren().add(setShipButtonsPane);
//
//
//
//
//
//        Button nextButton = new Button("Далее");
//        nextButton.setTranslateX(SCENE_SIZE_X - 70);
//        nextButton.setTranslateY(SCENE_SIZE_Y - 50);
//        stackPane.getChildren().add(nextButton);
//
//        Button randomSetShipsButton = new Button("Random");
//        randomSetShipsButton.setTranslateX(SCENE_SIZE_X - 140);
//        randomSetShipsButton.setTranslateY(SCENE_SIZE_Y - 50);
//        stackPane.getChildren().add(randomSetShipsButton);
//
//        Scene scene = new Scene(stackPane, SCENE_SIZE_X, SCENE_SIZE_Y);
//        stage.setScene(scene);
//        //stage.show();
//
//        nextButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                PvPBattlePage();
//            }
//        });
//        randomSetShipsButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ///goto logic
//            }
//        });
//    }
//
//
//    private void PvEsetShipsPage() {
//
//    }
//
//    private void PvPBattlePage() {
//        stackPane = new StackPane();
//        FieldController player1Controller = new FieldController(50, 100);////////////глобал
//        FieldController player2Controller = new FieldController(390, 100);
//        stackPane.getChildren().addAll(player2Controller, player1Controller);
//        scene = new Scene(stackPane, SCENE_SIZE_X, SCENE_SIZE_Y);
//        stage.setScene(scene);
//        //stage.show();
//        player2Controller.setDisable(true);///////////////////////////  вынести в функцию и вызывать из логики, в логику сигнас из обработчика
//        //player2Controller.setDisable(false);
//    }
//}