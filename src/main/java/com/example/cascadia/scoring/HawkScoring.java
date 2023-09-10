package com.example.cascadia.scoring;

import com.example.cascadia.board.Map;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.Tile;

import java.util.LinkedList;

public class HawkScoring {

    public  static Score hawkScoringA(Map m){
        Score s = new Score();
        s.name = "Hawk";
        s.description = "Number of Hawks that are adjacent not adjacent to any other Hawk";
        s.points = 0;
        s.numberFound = 0;

        LinkedList<GameTile> tiles = m.getGameTiles();

        for(GameTile t:tiles){
            if(t.getWildLife()!= WildLifeNames.HAWK)
                continue;

            LinkedList<Tile> adj = m.getNeighbours(t);

            boolean found = false;
            for(Tile t1:adj){
                if(t1 instanceof GameTile && ((GameTile) t1).getWildLife()==WildLifeNames.HAWK){
                    found = true;
                    break;
                }
            }

            if(!found){
                s.numberFound++;
            }
        }


        if(s.numberFound>=1 && s.numberFound<=5)
            s.points = s.numberFound*2+s.numberFound-1;
        else if(s.numberFound==6)
            s.points = 18;
        else if(s.numberFound==7)
            s.points = 22;
        else if(s.numberFound>=8)
            s.points = 28;

        return s;
    }
}
