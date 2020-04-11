package graphics.pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.Battle;

import graphics.Window;

import java.util.Random;

import static graphics.Window.WINDOW_SIZE_X;

public class MainPage extends Page {
    public MainPage() {
        super();

        ImageView backgroundImage = new ImageView(new Image("resources/Ship.png"));
        mainPane.getChildren().add(backgroundImage);

        Button startPvPGameButton = new Button("Играть PvP");
        startPvPGameButton.setMinSize(100, 30);
        startPvPGameButton.setTranslateX((WINDOW_SIZE_X - startPvPGameButton.getMinWidth()) / 2);
        startPvPGameButton.setTranslateY(100);
        startPvPGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.get().setMode(Battle.GameMoge.PvP);
                Window.setScene((new SetShipsPage(0)).getScene());
            }
        });
        mainPane.getChildren().add(startPvPGameButton);

        Button startPvEGameButton = new Button("Играть PvE");
        startPvEGameButton.setMinSize(100, 30);
        startPvEGameButton.setTranslateX((WINDOW_SIZE_X - startPvEGameButton.getMinWidth()) / 2);
        startPvEGameButton.setTranslateY(150);
        startPvEGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Battle.get().setMode(Battle.GameMoge.PvE);
                Window.setScene((new SetShipsPage(0)).getScene());
            }
        });
        mainPane.getChildren().add(startPvEGameButton);
    }
}
