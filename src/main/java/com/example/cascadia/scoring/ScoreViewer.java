package com.example.cascadia.scoring;

import com.example.cascadia.Player;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ScoreViewer extends javafx.application.Application {




    private ArrayList<Player> players;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/cascadia/scoring/score_viewer.fxml"));
        Parent root = fxmlLoader.load();
        ScoreViewerController controller = fxmlLoader.getController();


        controller.loadScores(players);


        Scene scene = new Scene(root);

        stage.setTitle("Table View Sample");
        stage.setWidth(520);
        stage.setHeight(530);

        stage.setScene(scene);
        stage.show();
    }


    public  ScoreViewer(ArrayList<Player> players){
        this.players = players;
    }
}
