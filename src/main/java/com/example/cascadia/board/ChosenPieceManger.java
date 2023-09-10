package com.example.cascadia.board;

import com.example.cascadia.bag.TokenBag;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.ActiveTile;
import com.example.cascadia.tiles.GameTile;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.function.Consumer;

public class ChosenPieceManger extends BorderPane {

    private final World world;

    private final StackPane chosenTileHolder = new StackPane();
    private boolean tileChosen = false;
    private final StackPane chosenTokenHolder = new StackPane();
    private boolean tokenChosen = false;

    private GameTile chosenTile;
    private WildLifeNames chosenWildLife;

    private Text playerInfo = new Text();


    public ChosenPieceManger(World world){
        this.world = world;
        this.prefWidthProperty().bind(world.widthProperty().multiply(.2));
        this.prefHeightProperty().bind(world.heightProperty().multiply(.2));
        this.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px");


        FlowPane chosenPieceHolder = new FlowPane();
        chosenPieceHolder.setHgap(20);
        chosenPieceHolder.prefWidthProperty().bind(this.widthProperty());
        chosenPieceHolder.setAlignment(javafx.geometry.Pos.CENTER);
        chosenPieceHolder.setPadding(new javafx.geometry.Insets(10));


        FlowPane buttonHolder = new FlowPane();

        Button rotateLeft = new Button("Rotate Left");
        Button rotateRight = new Button("Rotate Right");
        Button returnWildLife = new Button("Return Token");

        buttonHolder.setHgap(10);
        buttonHolder.prefWidthProperty().bind(this.widthProperty());
        buttonHolder.setAlignment(javafx.geometry.Pos.CENTER);


        buttonHolder.getChildren().addAll(rotateLeft, rotateRight, returnWildLife);

        rotateLeft.setOnAction(e->{
            if(chosenTile!=null){
                chosenTile.rotateLeft();
            }
        });

        returnWildLife.setOnAction(e->{
            returnActiveWildLifeByChoice();
        });

        rotateRight.setOnAction(e->{
            if(chosenTile!=null){
                chosenTile.rotateRight();
            }
        });


        chosenPieceHolder.getChildren().addAll(chosenTileHolder, chosenTokenHolder);
        FlowPane playerInfoHolder = new FlowPane(playerInfo);
        playerInfoHolder.setAlignment(javafx.geometry.Pos.CENTER);
        playerInfoHolder.prefWidthProperty().bind(this.widthProperty());

        this.setTop(playerInfoHolder);
        this.setCenter(chosenPieceHolder);
        this.setBottom(buttonHolder);

    }



    public void setChosenTile(GameTile gameTile) {

        this.chosenTile = gameTile;

        chosenTileHolder.getChildren().clear();

        if(gameTile==null)
            return;
        gameTile.setTilePosition(1,1);
        chosenTileHolder.getChildren().add(gameTile);

    }

    public void setChosenWildLife(WildLifeNames wildLifeName) {

        this.chosenWildLife = wildLifeName;
        chosenTokenHolder.getChildren().clear();

        if(wildLifeName==null)
            return;

        ImageView token = new ImageView(wildLifeName.getImage());
        chosenTokenHolder.getChildren().add(token);

    }

    public void returnActiveWildLife(){

        if(!tileChosen || chosenWildLife==null)
            return;

        LinkedList<GameTile> tiles = world.getCurrentPlayer().getMap().getGameTiles();
        for(GameTile tile: tiles){
            if(tile.canPlaceWildLife(chosenWildLife)){
                return;
            }
        }

        TokenBag.returnToken(chosenWildLife);
        setChosenWildLife(null);
        tokenChosen = true;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("WildLife Returned");
        alert.setContentText("The wild life token has been returned to the bag as you don't have any place to put it");
        alert.showAndWait();

    }
    private void returnActiveWildLifeByChoice() {
        if(chosenWildLife==null)
            return;
        TokenBag.returnToken(chosenWildLife);
        world.setChosenWildLife(null);
        tokenChosen = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("WildLife Returned");
        alert.setContentText("The wild life token has been returned.");
        alert.show();
    }

    public void setPlayerClickListener(){
        tokenChosen = false;
        tileChosen = false;
        playerInfo.setText(world.getCurrentPlayer().getName()+"'s Turn \t\t Available Nature Token: "+world.getCurrentPlayer().getNatureTokens());

        world.getCurrentPlayer().getMap().setActiveTileClickListener(
                new Consumer<ActiveTile>() {
                    @Override
                    public void accept(ActiveTile activeTile) {

                        if(chosenTile!=null){
                            world.getCurrentPlayer().getMap().putGameTile(activeTile, chosenTile);

                            if(chosenTile.canPlaceWildLife(chosenWildLife)){
                                world.setChosenWildLife(chosenWildLife);
                            }

                            world.setChosenTile(null);
                            tileChosen = true;

                            //try to return the wild life token if there is no place to put it
                            returnActiveWildLife();



                            if(tokenChosen && tileChosen)
                                world.nextTurn();
                        }
                    }
                }
        );

        world.getCurrentPlayer().getMap().setGameTileClickListener(
                new Consumer<GameTile>() {
                    @Override
                    public void accept(GameTile tile) {
                        if(chosenWildLife!=null && tile.canPlaceWildLife(chosenWildLife)){
                            tile.placeWildLife(chosenWildLife);
                            if(tile.isKeyStoneTile())
                                world.getCurrentPlayer().addNatureToken();
                            world.setChosenWildLife(null);
                            tokenChosen = true;


                            if(tokenChosen && tileChosen)
                                world.nextTurn();

                        }
                    }
                }
        );
    }






}
