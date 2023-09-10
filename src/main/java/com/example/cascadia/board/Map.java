package com.example.cascadia.board;

import com.example.cascadia.tiles.ActiveTile;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.StartingTile;
import com.example.cascadia.tiles.Tile;
import javafx.scene.Group;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Consumer;

public class Map extends Group {
    private static int WORLD_DIAMETER=80;

    private Tile allTiles[][];
    private final HashSet<ActiveTile> activeTiles= new HashSet<>();
    private final LinkedList<GameTile> gameTiles = new LinkedList<>();
    private Consumer<ActiveTile> activeTileClickListener;
    private Consumer<GameTile> gameTileClickListener;



    public Map(StartingTile startingTile) {
        allTiles = new Tile[WORLD_DIAMETER][WORLD_DIAMETER];

        //fill in the grid with initial tiles
        for (int i = 0; i < WORLD_DIAMETER; i++) {
            for (int j = 0; j < WORLD_DIAMETER; j++) {
                allTiles[i][j] = new Tile(i, j);
                this.getChildren().add(allTiles[i][j]);
            }
        }

        //make the three tiles in center as active tile
        Tile t1 = allTiles[WORLD_DIAMETER / 2][WORLD_DIAMETER / 2];
        Tile t2 = allTiles[WORLD_DIAMETER / 2][WORLD_DIAMETER / 2 + 1];
        Tile t3 = allTiles[WORLD_DIAMETER / 2 + 1][WORLD_DIAMETER / 2];

        ActiveTile at1 = new ActiveTile(t1);
        ActiveTile at2 = new ActiveTile(t2);
        ActiveTile at3 = new ActiveTile(t3);

        allTiles[WORLD_DIAMETER / 2][WORLD_DIAMETER / 2] = at1;
        allTiles[WORLD_DIAMETER / 2][WORLD_DIAMETER / 2 + 1] = at2;
        allTiles[WORLD_DIAMETER / 2 + 1][WORLD_DIAMETER / 2] = at3;

        activeTiles.add(at1);
        activeTiles.add(at2);
        activeTiles.add(at3);

        this.getChildren().removeAll(t1, t2, t3);
        this.getChildren().addAll(at1, at2, at3);

        putGameTile(at1,startingTile.startingTile1);
        putGameTile(at2,startingTile.startingTile2);
        putGameTile(at3,startingTile.startingTile3);



    }


    //put a gameTile in place of an active tiles
    public void putGameTile(ActiveTile active, GameTile gameTile){


        int row = active.getRow();
        int col = active.getCol();

        gameTile = gameTile.clone();

        gameTile.setTilePosition(row,col);

        //remove the active tile from the map
        this.getChildren().remove(active);
        activeTiles.remove(active);


        //add the game tile to the map
        allTiles[row][col] = gameTile;
        this.getChildren().add(gameTile);


        gameTiles.add(gameTile);
        gameTile.setOnMouseClicked(e->{
            GameTile gt = (GameTile) e.getSource();
            try {
                if(gt.getWildLife()==null && gameTileClickListener!=null)
                    gameTileClickListener.accept(gt);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });





        //add the new active tiles to the map
        LinkedList<Tile> neighbours = getNeighbours(row,col);

        for (Tile t: neighbours) {
            if(t.canConvertToActiveTile()){
                ActiveTile at = new ActiveTile(t);
                allTiles[t.getRow()][t.getCol()] = at;
                this.getChildren().remove(t);
                this.getChildren().add(at);
                activeTiles.add(at);

                at.setOnMouseClicked(e->{
                    ActiveTile at1 = (ActiveTile) e.getSource();
                    try {
                        if(activeTileClickListener!=null)
                            activeTileClickListener.accept(at1);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });
            }
        }


    }


    public void setActiveTileClickListener(Consumer<ActiveTile> func){
        this.activeTileClickListener = func;
    }

    public void setGameTileClickListener(Consumer<GameTile> func){
        this.gameTileClickListener = func;
    }


    public LinkedList<Tile> getNeighbours(int x,int y){
        LinkedList<Tile> neighbours = new LinkedList<>();

        //method for getting the neighbours of a hexagon at position x,y
        //reference: https://stackoverflow.com/questions/4585135/hexagonal-tiles-and-finding-their-adjacent-neighbours
        int range=1;
        int minX = x - range, maxX = x + range;
        for (int i = minX; i <= maxX; ++i) {
            if (i != x && isInRange(i, y)) {
                neighbours.add(allTiles[i][y]);
            }

        }
        for (int yOff = 1; yOff <= range; ++yOff) {
            if ((y+yOff) % 2 == 1) --maxX; else ++minX;
            for (int i=minX; i<=maxX; ++i) {
                if(isInRange(i, y-yOff))
                    neighbours.add(allTiles[i][y-yOff]);
                if(isInRange(i, y+yOff))
                    neighbours.add(allTiles[i][y+yOff]);
            }
        }

        return neighbours;
    }

    public LinkedList<Tile> getNeighbours(Tile t){
        return getNeighbours(t.getRow(),t.getCol());
    }
    public LinkedList<GameTile> getGameTiles(){
        return new LinkedList<>(gameTiles);
    }
    private boolean isInRange(int x, int y) {
        return x>=0 && x<WORLD_DIAMETER && y>=0 && y<WORLD_DIAMETER;
    }




}












