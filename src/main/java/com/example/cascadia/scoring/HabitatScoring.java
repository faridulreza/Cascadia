package com.example.cascadia.scoring;

import com.example.cascadia.board.Map;
import com.example.cascadia.constants.TileNames;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.Tile;

import java.util.HashSet;
import java.util.LinkedList;

import static com.example.cascadia.constants.TileNames.*;
import static com.example.cascadia.tiles.GameTile.Direction.*;

public class HabitatScoring {



    public static LinkedList<Score> getHabitatScore(Map m){

        LinkedList<Score> scores = new LinkedList<>();
        Score s = new Score();
        s.name = "Mountain";
        s.description = "Largest Mountain Corridor";
        s.points = largestHabitat(m, MOUNTAIN);
        scores.add(s);

        s = new Score();
        s.name = "Forest";
        s.description = "Largest Forest Corridor";
        s.points = largestHabitat(m, FOREST);
        scores.add(s);

        s = new Score();
        s.name = "Lake";
        s.description = "Largest Lake Corridor";
        s.points = largestHabitat(m, LAKE);
        scores.add(s);

        s = new Score();
        s.name = "Swamp";
        s.description = "Largest Swamp Corridor";
        s.points = largestHabitat(m, SWAMP);
        scores.add(s);

        s = new Score();
        s.name = "Desert";
        s.description = "Largest Desert Corridor";
        s.points = largestHabitat(m, DESERT);
        scores.add(s);



        return scores;
    }

    private static int largestHabitat(Map m, TileNames tileNames) {

        int highest = 0;
        LinkedList<GameTile> tiles = m.getGameTiles();

        HashSet<GameTile> visited = new HashSet<>();

        for(GameTile t : tiles){
            if(t.hasTerrainType(tileNames) && !visited.contains(t)){
                int sz = dfs(m, t, visited, tileNames);
                if(sz>highest)
                    highest = sz;
            }
        }

        return highest;
    }

    private static final int TOP_LEFT_NEIGHBOUR = 0;
    private static final int TOP_RIGHT_NEIGHBOUR = 1;
    private  static final int RIGHT_NEIGHBOUR = 3;
    private  static final int BOTTOM_RIGHT_NEIGHBOUR = 5;
    private  static final int BOTTOM_LEFT_NEIGHBOUR = 5;
    private  static final int LEFT_NEIGHBOUR = 2;

    private static int dfs(Map m, GameTile t, HashSet<GameTile> visited, TileNames tileType) {

        int sz = 1;
        visited.add(t);

        LinkedList< Tile> adj = m.getNeighbours(t);
        adj.sort((o1, o2) -> {
            if(o1.getRow()==o2.getRow())
                return o1.getCol()-o2.getCol();
            return o1.getRow()-o2.getRow();
        });

        Tile topLeft = adj.get(TOP_LEFT_NEIGHBOUR);
        Tile topRight = adj.get(TOP_RIGHT_NEIGHBOUR);
        Tile right = adj.get(RIGHT_NEIGHBOUR);
        Tile bottomRight = adj.get(BOTTOM_RIGHT_NEIGHBOUR);
        Tile bottomLeft = adj.get(BOTTOM_LEFT_NEIGHBOUR);
        Tile left = adj.get(LEFT_NEIGHBOUR);

        if(topLeft instanceof GameTile && t.getTerrainType(TOP_LEFT)==tileType){
            GameTile gt = (GameTile)topLeft;
            if(gt.getTerrainType(BOTTOM_RIGHT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        if(topRight instanceof GameTile && t.getTerrainType(TOP_RIGHT)==tileType){
            GameTile gt = (GameTile)topRight;
            if(gt.getTerrainType(BOTTOM_LEFT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        if(right instanceof GameTile && t.getTerrainType(RIGHT)==tileType){
            GameTile gt = (GameTile)right;
            if(gt.getTerrainType(LEFT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        if(bottomRight instanceof GameTile && t.getTerrainType(BOTTOM_RIGHT)==tileType){
            GameTile gt = (GameTile)bottomRight;
            if(gt.getTerrainType(TOP_LEFT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        if(bottomLeft instanceof GameTile && t.getTerrainType(BOTTOM_LEFT)==tileType){
            GameTile gt = (GameTile)bottomLeft;
            if(gt.getTerrainType(TOP_RIGHT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        if(left instanceof GameTile && t.getTerrainType(LEFT)==tileType){
            GameTile gt = (GameTile)left;
            if(gt.getTerrainType(RIGHT)==tileType && !visited.contains(gt))
                sz+=dfs(m, gt, visited, tileType);
        }

        return sz;
    }
}
