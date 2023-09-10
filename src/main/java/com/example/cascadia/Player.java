package com.example.cascadia;

import com.example.cascadia.bag.TileBag;
import com.example.cascadia.board.Map;
import com.example.cascadia.board.World;
import com.example.cascadia.tiles.StartingTile;
import javafx.scene.control.TextInputDialog;

public class Player {

    private int natureTokens = 0;
    private Map map;
    private World world;

    private String name;
    private Player(String name, World world) {
        this.world = world;
        StartingTile startingTile = TileBag.getStartingTile();
        map = new Map(startingTile);

        this.name = name;
    }

    public void addNatureToken() {
        natureTokens++;
    }

    public void removeNatureToken() {
        natureTokens--;
    }

    public int getNatureTokens() {
        return natureTokens;
    }

    public Map getMap() {
        return map;
    }


    public static Player getPlayer(World world, String defaultName) {
        TextInputDialog dialog = new TextInputDialog(defaultName);
        dialog.setTitle("Player Name");
        dialog.setHeaderText("Enter your name");
        dialog.setContentText("Name:");
        String name = dialog.showAndWait().get();
        if (name == null) {
            return new Player("Unknown Player", world);
        } else {
            return new Player(name, world);
        }
    }

    public String getName() {
        return name;
    }
}
