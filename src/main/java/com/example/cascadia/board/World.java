package com.example.cascadia.board;

import com.example.cascadia.Player;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.scoring.ScoreViewer;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.StartingTile;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class World extends BorderPane {


    private final ArrayList<Player> players = new ArrayList<>();


    private final DrawingBoard drawingBorad;

    private final ChosenPieceManger choosenPieceManger;


    private int currentPlayer = 0;

    private final ScrollPane mapHolder= new ScrollPane();

    private final Random random = new Random();

    private final Text mapName = new Text();

    private int currentShowingMap = 0;

    public World(){

        players.add(Player.getPlayer(this,"Player 1"));
        players.add(Player.getPlayer(this,"Player 2"));

        //randomly select a player to start
        currentPlayer = random.nextInt(players.size());

        drawingBorad = new DrawingBoard(this);
        choosenPieceManger = new ChosenPieceManger(this);



        mapHolder.setContent(players.get(currentPlayer).getMap());
        mapName.setText("viewing map of "+players.get(currentShowingMap).getName());
        mapName.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-fill: #000000; -fx-stroke: #000000; -fx-stroke-width: 1px;");
        setCenter(mapHolder);

        choosenPieceManger.setPlayerClickListener();


        VBox vBox = new VBox();
        vBox.prefWidthProperty().bind(this.widthProperty().multiply(.2));
        vBox.prefHeightProperty().bind(this.heightProperty());



        FlowPane flowPane = new FlowPane();
        Button changeMap = new Button("Change Map");
        flowPane.getChildren().addAll(mapName, changeMap);
        flowPane.setStyle("-fx-padding: 10px;");
        flowPane.setAlignment(javafx.geometry.Pos.CENTER);
        flowPane.setHgap(20);
        flowPane.setVgap(10);

        vBox.getChildren().addAll(flowPane,choosenPieceManger, drawingBorad);

        changeMap.setOnMouseClicked(mouseEvent -> {
            currentShowingMap = (currentShowingMap+1)%players.size();
            mapHolder.setContent(players.get(currentShowingMap).getMap());
            mapName.setText("viewing map of "+players.get(currentShowingMap).getName());
        });

        setLeft(vBox);



        mapHolder.setHvalue(.55);
        mapHolder.setVvalue(.5);

    }


    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public void setChosenWildLife(WildLifeNames wildLifeNames) {
        choosenPieceManger.setChosenWildLife(wildLifeNames);
        LinkedList<GameTile> tiles = getCurrentPlayer().getMap().getGameTiles();

        for (GameTile tile : tiles) {
            if(tile.canPlaceWildLife(wildLifeNames)){
                tile.setHighLightTile(true);
            }
            else {
                tile.setHighLightTile(false);
            }
        }
    }

    public void setChosenTile(GameTile gameTile) {
        choosenPieceManger.setChosenTile(gameTile);
    }

    public void nextTurn() {

        currentPlayer = (currentPlayer+1)%players.size();

        //no more tiles to draw
        if(!drawingBorad.drawNext()){
            endGame();
            return;
        }
        choosenPieceManger.setPlayerClickListener();
        mapHolder.setContent(players.get(currentPlayer).getMap());
        mapName.setText("viewing map of "+players.get(currentPlayer).getName());
        currentShowingMap = currentPlayer;



    }

    private void endGame() {
        ScoreViewer scoreViewer = new ScoreViewer(players);
        try {
            Stage stage = new Stage();
            stage.setTitle("Score Viewer");
            scoreViewer.start(stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
