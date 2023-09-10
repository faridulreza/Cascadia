package com.example.cascadia;

import com.example.cascadia.board.World;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        World w = new World();

        Scene scene = new Scene(w, 900,600);
        w.prefHeightProperty().bind(scene.heightProperty());
        w.prefWidthProperty().bind(scene.widthProperty());
        stage.setTitle("Cascadia");
        stage.setScene(scene);


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}























