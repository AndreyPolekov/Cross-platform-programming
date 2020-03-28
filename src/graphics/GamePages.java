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

import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.Thread.sleep;

public class GamePages extends Application {
    public static final int SCENE_SIZE_X = 650;
    public static final int SCENE_SIZE_Y = 400;

    public static final int CELL_COUNT_X = 10;
    public static final int CELL_COUNT_Y = 10;
    public static final int CELL_SIZE = 20;

    private Stage stage;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        stage.setTitle("Game Sea Battle");
        stage.setResizable(false);
        mainPage();
    }

    private void mainPage() throws FileNotFoundException {
        //FileInputStream inputstream = new FileInputStream("C:\\Program Files\\IntelliJ IDEA\\Projects\\Game Sea Battle\\src\\resourcess\\Ship.jpg");
        //clazz.getResourceAsStream("C:/Program Files/IntelliJ IDEA/Projects/Game Sea Battle/src/resources/Ship.png");
        //Image image = new Image(new FileInputStream("C:\\Program Files\\IntelliJ IDEA\\Projects\\Game Sea Battle\\src\\resourcess\\Ship.jpg"));
        //Image image = new Image(new FileInputStream("C:\Program Files\IntelliJ IDEA\Projects\Game Sea Battle\src\resourcess\Ship.jpg"));
        //Image image = new Image(input);        //ImageView imageView = new ImageView(new Image("resourcess\\Ship.jpg"));
//        Class<?> clazz = this.getClass();
//        InputStream input = clazz.getResourceAsStream("C:/Program Files/IntelliJ IDEA/Projects/Game Sea Battle/src/resources/Ship.png");
//
//        Image image = new Image(input);
//
//        ImageView imageView = new ImageView(image);
//C:/Program Files/IntelliJ IDEA/Projects/Game Sea Battle/
//        Image image = new Image("src/resources/Ship.png");
//        ImageView imageView = new ImageView(image);
//
        //stackPane.getChildren().add(imageView);


        Button startGameButton = new Button("Играть");



        StackPane stackPane = new StackPane();

        stackPane.getChildren().add(startGameButton);



        Scene scene = new Scene(stackPane, SCENE_SIZE_X, SCENE_SIZE_Y);
        stage.setScene(scene);
        stage.show();

        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PvPsetShipsPage();
            }
        });
    }

    private void PvPsetShipsPage() {
        //////////////////////////////////////////////////////////////////////////////////////

        Button nextButton = new Button("Далее");
        Button randomSetShipsButton = new Button("Random");
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER_RIGHT);/////////////////////////////////////////
        stackPane.getChildren().add(nextButton);
        nextButton.setTranslateY(20);
        //stackPane.setAlignment(Pos.CENTER_RIGHT);/////////////////////////////////////////
        stackPane.getChildren().add(randomSetShipsButton);
        Scene scene = new Scene(stackPane, SCENE_SIZE_X, SCENE_SIZE_Y);
        stage.setScene(scene);
        stage.show();

        nextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PvPBattlePage();
            }
        });
        randomSetShipsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ///goto logic
            }
        });
    }

    private void PvEsetShipsPage() {

    }

    private void PvPBattlePage() {
        FieldController player1Controller = new FieldController(50, 100);////////////глобал
        FieldController player2Controller = new FieldController(390, 100);
        Group group = new Group();
        group.getChildren().addAll(player2Controller, player1Controller);
        Scene scene = new Scene(group, SCENE_SIZE_X, SCENE_SIZE_Y);
        stage.setScene(scene);
        stage.show();
        player2Controller.setDisable(true);///////////////////////////  вынести в функцию и вызывать из логики, в логику сигнас из обработчика
        //player2Controller.setDisable(false);
    }
}