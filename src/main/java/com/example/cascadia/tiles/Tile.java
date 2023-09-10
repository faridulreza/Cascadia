package com.example.cascadia.tiles;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Tile extends Polygon {
    private final static double r = 60; // the inner radius from hexagon center to outer corner
    private final static double n = Math.sqrt(r * r * 0.75); // the inner radius from hexagon center to middle of the axis
    private final static double TILE_HEIGHT = 2 * r;
    private final static double TILE_WIDTH = 2 * n;

    private final static  double xStartOffset=10;
    private final  static double yStartOffset=12;


    protected int row,col;


    public Tile(int i, int j) {
        setTilePosition(i,j);

        setFill(Color.ANTIQUEWHITE);

//        setStrokeWidth(1);
//        setStroke(Color.BLACK);



    }

    //copy the points from another tile
    public Tile(Tile t){
        if(t!=null) {
            setTilePosition(t.row, t.col);
        }
        this.setOnMouseClicked(e->{
            System.out.println("row: "+row+" col: "+col);
        });
    }

    //calculate the boundaries depending on the position of the hexagon on the 2d grid
    public void setTilePosition(int i, int j){
        //reference: https://stackoverflow.com/questions/54165602/create-hexagonal-field-with-javafx
        double x= i * TILE_WIDTH + (j % 2) * n + xStartOffset;
        double y = j * TILE_HEIGHT * 0.75 + yStartOffset;
        getPoints().clear();
        getPoints().addAll(
                x, y,
                x, y + r,
                x + n, y + r * 1.5,
                x + TILE_WIDTH, y + r,
                x + TILE_WIDTH, y,
                x + n, y - r * 0.5
        );

        this.row = i;
        this.col = j;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public boolean canConvertToActiveTile(){
        return true;
    }


}
