package com.kkulpa.checkers.checkers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class CheckersGame extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        Parent boardRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CheckersBoardReady.fxml")));
        Scene sceneBoard = new Scene(boardRoot);

        stage.setScene(sceneBoard);
        stage.show();
    }
}


