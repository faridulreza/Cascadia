package com.example.cascadia.tiles;

import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.constants.TileNames;
import javafx.scene.paint.Color;

public class ActiveTile extends Tile{

    public ActiveTile(Tile toReplace) {
        super(toReplace);

        setStrokeWidth(1);
        setStroke(Color.BLACK);

        setFill(Color.GREEN);
        setOpacity(.2);

        this.setOnMouseEntered(e->{
            setOpacity(.5);
        });

        this.setOnMouseExited(e->{
            setOpacity(.2);
        });

    }

    public boolean canConvertToActiveTile(){
        return false;
    }

}
