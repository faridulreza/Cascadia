package com.example.cascadia.scoring;

import com.example.cascadia.board.Map;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.Tile;

import java.util.HashSet;
import java.util.LinkedList;

public class FoxScoring {
    public static Score foxScoringA(Map m){
        Score s = new Score();
        s.name="Fox";
        s.description="Summation of number of unique wildlife tiles adjacent to each of the foxes";

        LinkedList<GameTile> tiles = m.getGameTiles();

        for(GameTile t:tiles){
            if(t.getWildLife()!=WildLifeNames.FOX)
                continue;

            LinkedList<Tile> adj = m.getNeighbours(t);

            HashSet<String> wildLifeNameSet = new HashSet<>();

            for(Tile t1:adj){
                if(t1 instanceof GameTile){
                    GameTile gt = (GameTile)t1;
                    if(gt.getWildLife()!=null)
                        wildLifeNameSet.add(gt.getWildLife().toString());
                }
            }

            s.numberFound++;
            s.points+=wildLifeNameSet.size();
        }




        return s;
    }
}
