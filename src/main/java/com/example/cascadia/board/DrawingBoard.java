package com.example.cascadia.board;

import com.example.cascadia.bag.TileBag;
import com.example.cascadia.bag.TokenBag;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Flow;

public class DrawingBoard extends VBox {

    private final World world;
    private final Button useNatureToken;
    private ArrayList<GameTile> drawnTiles = new ArrayList<>();
    private ArrayList<WildLifeNames> drawnWildLife = new ArrayList<>();
    private ArrayList<BorderPane> panes = new ArrayList<>();


    public DrawingBoard(World world){
        this.world = world;
        this.prefWidthProperty().bind(world.widthProperty().multiply(.2));
        this.prefHeightProperty().bind(world.heightProperty().multiply(.8));
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px");
        this.setSpacing(20);

        for(int i = 0; i < 4; i++) {
            BorderPane pane = new BorderPane();
            pane.prefHeightProperty().bind(this.heightProperty().multiply(.2));
            panes.add(pane);
            this.getChildren().add(pane);
            this.getChildren().add(new javafx.scene.control.Separator());
        }

        useNatureToken = new Button("Use Nature Token");
        useNatureToken.setOnAction(e->{
            if(!isWildLifeChosen &&!isTileChosen && !usingNatureToken
                    && world.getCurrentPlayer().getNatureTokens()>0){
                usingNatureToken = true;
                useNatureToken.setText("Stop Using Nature Token");
                showTiles();
                world.getCurrentPlayer().removeNatureToken();
                usingNatureToken = true;
            }

        });

        FlowPane buttonHolder = new FlowPane(useNatureToken);
        buttonHolder.setHgap(10);
        buttonHolder.prefWidthProperty().bind(this.widthProperty());
        buttonHolder.setAlignment(javafx.geometry.Pos.CENTER);

        this.getChildren().add(buttonHolder);



        drawnTiles.add(TileBag.getTile());
        drawnTiles.add(TileBag.getTile());
        drawnTiles.add(TileBag.getTile());
        drawnTiles.add(TileBag.getTile());

        drawnWildLife.add(TokenBag.drawToken());
        drawnWildLife.add(TokenBag.drawToken());
        drawnWildLife.add(TokenBag.drawToken());
        drawnWildLife.add(TokenBag.drawToken());

        optionalCull();
        autoCull();
        showTiles();


    }

    private boolean usingNatureToken = false;

    private boolean optionalCulled = false;

    private boolean isTileChosen = false;
    private boolean isWildLifeChosen = false;


    public boolean drawNext(){
        if(TileBag.isEmpty())
            return false;

        usingNatureToken = false;
        optionalCulled = false;
        isTileChosen = false;
        isWildLifeChosen = false;

        useNatureToken.setText("Use Nature Token");

        GameTile tile = TileBag.getTile();

        for(int i = 0; i < 4; i++) {
            if(drawnTiles.get(i) == null) {
                drawnTiles.set(i, tile);
                System.out.println("Tile added");
                break;
            }
        }

        for(int i = 0; i < 4; i++) {
            if(drawnWildLife.get(i) == null) {
                drawnWildLife.set(i, TokenBag.drawToken());
                System.out.println("WildLife added");
                break;
            }
        }


        optionalCull();
        autoCull();
        showTiles();
        return true;
    }

    private void optionalCull() {
        if(optionalCulled)
            return;

        int max_same = 0;
        WildLifeNames maxWildLife = null;
        for (int i = 0; i <4 ; i++) {
            int same = 0;
            for (int j = 0; j <4 ; j++) {
                if(drawnWildLife.get(i)==drawnWildLife.get(j))
                    same++;
            }
            if(same>max_same) {
                max_same = same;
                maxWildLife = drawnWildLife.get(i);
            }
        }

        if(max_same==3){
            showTiles();
            WildLifeNames finalMaxWildLife = maxWildLife;
            Platform.runLater(()-> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Optional Culling");
                alert.setHeaderText("You can choose to cull the drawing board");
                alert.setContentText("Do you want to cull the drawing board?");
                alert.showAndWait();



                if (alert.getResult().getText().equals("OK")) {
                    optionalCulled = true;
                    for (int i = 0; i < 4; i++) {
                        if(drawnWildLife.get(i)== finalMaxWildLife){
                            drawnWildLife.set(i, TokenBag.drawToken());
                        }
                    }

                    //return the tokens to the bag
                    TokenBag.returnToken(finalMaxWildLife);
                    TokenBag.returnToken(finalMaxWildLife);
                    TokenBag.returnToken(finalMaxWildLife);
                    showTiles();


                }

            });
        }
    }

    private void showTiles(){
        isTileChosen = false;
        isWildLifeChosen = false;

        for(int i = 0; i < 4; i++) {
            FlowPane flowPane = new FlowPane();
            flowPane.setHgap(20);

            if(!usingNatureToken){
                flowPane.getChildren().add(drawnTiles.get(i));
                drawnTiles.get(i).setTilePosition(2,3);
                flowPane.getChildren().add(new ImageView(drawnWildLife.get(i).getImage()));
                flowPane.setAlignment(javafx.geometry.Pos.CENTER);
                int finalI = i;
                flowPane.setOnMouseClicked(e->{
                    if(!isTileChosen) {
                        world.setChosenTile(drawnTiles.get(finalI));
                        world.setChosenWildLife(drawnWildLife.get(finalI));
                        isTileChosen = true;
                        isWildLifeChosen = true;
                        panes.get(finalI).setCenter(null);
                        drawnTiles.set(finalI,null);
                        drawnWildLife.set(finalI, null);
                    }
                });

                flowPane.setOnMouseEntered(e->{
                    if(!isTileChosen){
                        flowPane.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                    }
                });

                flowPane.setOnMouseExited(e->{
                    if(!isTileChosen){
                        flowPane.setStyle("-fx-border-color: transparent; -fx-border-width: 1px; ");
                    }
                });
            }
            else{

                StackPane tilePane = new StackPane(drawnTiles.get(i));
                tilePane.setStyle("-fx-padding: 20px;");

                drawnTiles.get(i).setTilePosition(2,3);

                StackPane wildLifePane = new StackPane(new ImageView(drawnWildLife.get(i).getImage()));
                wildLifePane.setStyle("-fx-padding: 20px;");

                flowPane.getChildren().addAll(tilePane,wildLifePane);
                flowPane.setAlignment(javafx.geometry.Pos.CENTER);
                int finalI = i;

                tilePane.setOnMouseClicked(e->{
                    if(!isTileChosen) {
                        world.setChosenTile(drawnTiles.get(finalI));
                        isTileChosen = true;

                        drawnTiles.set(finalI,null);
                        flowPane.getChildren().remove(tilePane);

                    }
                });

                tilePane.setOnMouseEntered(e->{
                    if(!isTileChosen){
                        tilePane.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-padding: 20px; ");
                    }
                });

                tilePane.setOnMouseExited(e->{
                    if(!isTileChosen){
                        tilePane.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;-fx-padding: 20px; ");
                    }
                });


                wildLifePane.setOnMouseClicked(e->{
                    if(!isWildLifeChosen) {
                        world.setChosenWildLife(drawnWildLife.get(finalI));
                        isWildLifeChosen = true;

                        drawnWildLife.set(finalI,null);
                        flowPane.getChildren().remove(wildLifePane);

                    }
                });

                wildLifePane.setOnMouseEntered(e->{
                    if(!isWildLifeChosen){
                        wildLifePane.setStyle("-fx-border-color: red; -fx-border-width: 1px;-fx-padding: 20px; ");
                    }
                });

                wildLifePane.setOnMouseExited(e->{
                    if(!isWildLifeChosen){
                        wildLifePane.setStyle("-fx-border-color: transparent; -fx-border-width: 1px;-fx-padding: 20px;");
                    }
                });

            }
            panes.get(i).setCenter(null);
            panes.get(i).setCenter(flowPane);


        }
    }

    //automatically cull the drawing board
    //if there are more than 4 tiles on the drawing board
    private void autoCull() {
        WildLifeNames first = drawnWildLife.get(0);

        for(int i = 0; i < drawnWildLife.size(); i++) {
            if(drawnWildLife.get(i) != first) {
                return;
            }
        }

        showTiles();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Auto Culling");
            alert.setHeaderText("All tiles are the same");
            alert.setContentText("All tiles are the same, so the drawing board will be culled");
            alert.showAndWait();

            drawnWildLife.clear();
            for(int i = 0; i < 4; i++) {
                drawnWildLife.add(TokenBag.drawToken());
            }

            //return the tokens to the bag
            TokenBag.returnToken(first);
            TokenBag.returnToken(first);
            TokenBag.returnToken(first);
            TokenBag.returnToken(first);
            showTiles();

            //auto cull again if needed
            autoCull();

    }


}
