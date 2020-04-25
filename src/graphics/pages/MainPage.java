package graphics.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import logic.Battle;

import graphics.Window;

import java.util.Random;

import static graphics.Window.WINDOW_SIZE_X;

public class MainPage extends Page {
    public MainPage() {
        super();

        //ImageView backgroundImage = new ImageView(new Image("resources/Ship.png"));
        ImageView backgroundImage = new ImageView(new Image("resources/Ship.jpg"));
        mainPane.getChildren().add(backgroundImage);

        Label gameName = new Label("Морской бой");
        gameName.setFont(Font.font("Cambria", 64));
        gameName.setTextFill(Color.WHITE);
        gameName.setTranslateX(WINDOW_SIZE_X / 2 - 170);
        gameName.setTranslateY(100);
        mainPane.getChildren().add(gameName);

        Button startPvPGameButton = new Button("Играть PvP");
        startPvPGameButton.setMinSize(150, 50);
        startPvPGameButton.setTranslateX((WINDOW_SIZE_X - startPvPGameButton.getMinWidth()) / 2);
        startPvPGameButton.setTranslateY(270);
        startPvPGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.get().setMode(Battle.GameMoge.PvP);
                Window.setScene((new SetShipsPage(0)).getScene());
            }
        });
        mainPane.getChildren().add(startPvPGameButton);

        Button startPvEGameButton = new Button("Играть PvC");
        startPvEGameButton.setMinSize(150, 50);
        startPvEGameButton.setTranslateX((WINDOW_SIZE_X - startPvEGameButton.getMinWidth()) / 2);
        startPvEGameButton.setTranslateY(350);
        startPvEGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.get().setMode(Battle.GameMoge.PvC);
                Window.setScene((new SetShipsPage(0)).getScene());
            }
        });
        mainPane.getChildren().add(startPvEGameButton);
    }
}
