package com.example.cascadia.tiles;

import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.constants.TileNames;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import java.net.URISyntaxException;
import java.net.URL;
import java.text.CollationElementIterator;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class GameTile extends Tile{

    public  enum  Direction{
        TOP_LEFT,
        TOP_RIGHT,
        RIGHT,
        BOTTOM_RIGHT,
        BOTTOM_LEFT,
        LEFT
    }

    //first entry is the terrainType of the northEast side of the hexagon
    //second is the terrain Type of the East side and so on
    private final LinkedList<TileNames>  terrainType = new LinkedList<>();
    private int rotation=0;

    private WildLifeNames[] wildLifeNames;
    private TileNames[] tileNames;

    private WildLifeNames activeWildLife;

    private final Image bgImage;

    private boolean keyStoneTile;


    public GameTile(Tile toReplace, TileNames[] tileNames , WildLifeNames[]wildLife) {
        super(toReplace);

        keyStoneTile =false;

        //set the terrain types of the hexagon sides
        if(tileNames.length==2){
            terrainType.add(tileNames[1]);
            terrainType.add(tileNames[1]);
            terrainType.add(tileNames[1]);

            terrainType.add((tileNames[0]));
            terrainType.add((tileNames[0]));
            terrainType.add((tileNames[0]));
        }
        else{
            for (int k = 0; k <6 ; k++) {
                terrainType.add(tileNames[0]);
            }
        }
        this.tileNames = tileNames;
        this.wildLifeNames = wildLife;
        this.bgImage = new Image(getBGImageName(tileNames));

        try{
            WritableImage snap = getPicture();
            ImagePattern pattern = new ImagePattern(snap);
            setFill(pattern);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        setStrokeWidth(5);

    }

    public GameTile(Tile toReplace, TileNames[] tileNames , WildLifeNames[]wildLife,boolean isKeyStoneTile){
        this(toReplace,tileNames,wildLife);
        this.keyStoneTile = isKeyStoneTile;
    }

    public void rotateRight(){
        rotation = (rotation+1)%6;
        TileNames temp = terrainType.removeLast();
        terrainType.addFirst(temp);
        resetPicture();
    }

    public void rotateLeft(){
        rotation = (rotation+5)%6;
        TileNames temp = terrainType.removeFirst();
        terrainType.addLast(temp);

        resetPicture();
    }

    public boolean isKeyStoneTile(){
        return keyStoneTile;
    }

    public boolean canPlaceWildLife(WildLifeNames wildLife){
        if(activeWildLife!=null || wildLife==null)
            return false;

        for (int i = 0; i <wildLifeNames.length ; i++) {
            if(wildLifeNames[i]==wildLife) {
                return true;
            }
        }

        return false;
    }

    public void placeWildLife(WildLifeNames wildLife){
        if(canPlaceWildLife(wildLife)){
            activeWildLife = wildLife;
            resetPicture();
        }
    }

    public void setHighLightTile(boolean b){
        if(b)
            setStroke(Color.RED);
        else
            setStroke(Color.TRANSPARENT);
    }

    public WildLifeNames getWildLife(){
        return activeWildLife;
    }



    public boolean canConvertToActiveTile(){
        return false;
    }

    //gets the background image depending on the terrain type names
    private String getBGImageName(TileNames[] tileNames) {

        try {

            if (tileNames.length == 2) {
                String s ="/com/example/cascadia/assets/tiles/" + tileNames[0] + "+" + tileNames[1] + ".png";
                System.out.println(s);
                URL url = getClass().getResource(s);

                //if the image is not found, try the reverse order name
                if(url==null) {
                    s = "/com/example/cascadia/assets/tiles/" + tileNames[1] + "+" + tileNames[0] + ".png";
                    //reverser the sides
                    Collections.reverse(terrainType);
                }
                else{
                    return url.toURI().toString();
                }
                System.out.println(s);
                return getClass().getResource(s).toURI().toString();
            }

            return getClass().getResource("/com/example/cascadia/assets/tiles/" + tileNames[0]+".png").toURI().toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return  "";
        }
    }


    //combines the background image with the active/inactive tokens on the game tile
    //returns an image that can be rendered
    private WritableImage getPicture() throws URISyntaxException {

        Canvas canvas = new Canvas(103, 115);
        GraphicsContext context = canvas.getGraphicsContext2D();


        context.translate(52,57);
        context.rotate(60*rotation);
        context.translate(-52,-57);
        context.drawImage(bgImage,0,0);


        Image[] tokenImages = new Image[wildLifeNames.length];
        for (int i = 0; i <wildLifeNames.length ; i++) {
            if(activeWildLife==null)
                tokenImages[i] =  wildLifeNames[i].getImage();
        }
        Group g = new Group();


        double group_width=30;
        if(activeWildLife!=null){
            group_width=40;
            ImageView i1 = new ImageView(activeWildLife.getActiveImage());i1.setScaleX(.7);i1.setScaleY(.7);
            i1.setX((group_width/2)); i1.setY(group_width/2);
            g.getChildren().addAll(i1);
        }
        else if(wildLifeNames.length==3){
            ImageView i1 = new ImageView(tokenImages[0]);
            ImageView i2 = new ImageView(tokenImages[1]);
            ImageView i3 = new ImageView(tokenImages[2]);

            i1.setX(0); i1.setY((group_width/4));i1.setScaleX(.6);i1.setScaleY(.6);
            i2.setX((group_width/6)*5); i2.setY(group_width/4);i2.setScaleX(.6);i2.setScaleY(.6);
            i3.setX(group_width/3); i3.setY((group_width));i3.setScaleX(.6);i3.setScaleY(.6);

            g.getChildren().addAll(i1,i2,i3);


        } else if (wildLifeNames.length==2) {
            ImageView i1 = new ImageView(tokenImages[0]);i1.setScaleX(.6);i1.setScaleY(.6);
            ImageView i2 = new ImageView(tokenImages[1]);i2.setScaleX(.6);i2.setScaleY(.6);

            i1.setX(0); i1.setY(group_width);
            i2.setX((group_width/6)*5); i2.setY(group_width);
            g.getChildren().addAll(i1,i2);
        }
        else{
            ImageView i1 = new ImageView(tokenImages[0]);i1.setScaleX(.6);i1.setScaleY(.6);
            i1.setX((group_width/2)); i1.setY(group_width/2);
            g.getChildren().addAll(i1);
        }

        SnapshotParameters params = new SnapshotParameters();
        WritableImage snap = canvas.snapshot(params, null);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(new ImageView(snap),g);

        snap = stackPane.snapshot(params, null);




        return snap;
    }

    public void resetPicture() {
        try{
            WritableImage snap = getPicture();
            ImagePattern pattern = new ImagePattern(snap);
            setFill(pattern);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public GameTile clone(){

        GameTile gameTile = new GameTile(null,tileNames.clone() ,wildLifeNames.clone(), keyStoneTile);
        gameTile.rotation = rotation;
        gameTile.terrainType.clear();
        gameTile.terrainType.addAll(terrainType);
        gameTile.resetPicture();

        return gameTile;
    }


    public TileNames getTerrainType(Direction direction){
        switch (direction){
            case TOP_LEFT:
                return terrainType.get(5);
            case TOP_RIGHT:
                return terrainType.get(0);
            case RIGHT:
                return terrainType.get(1);
            case BOTTOM_RIGHT:
                return terrainType.get(2);
            case BOTTOM_LEFT:
                return terrainType.get(3);
            case LEFT:
                return terrainType.get(4);
        }

        return null;
    }

    public boolean hasTerrainType(TileNames tileNames){
        for (TileNames t:terrainType) {
            if(t==tileNames)
                return true;
        }

        return false;
    }
}
