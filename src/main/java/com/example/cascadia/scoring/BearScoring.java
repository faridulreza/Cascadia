package com.example.cascadia.scoring;

import com.example.cascadia.board.Map;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.Tile;

import java.util.LinkedList;

public class BearScoring {

    public static Score bearScoringA(Map m){

        Score sc = new Score();
        sc.name= "Bear";
        sc.description = "Number of pairs of bears that are not adjacent to any other bear";


        LinkedList<GameTile> tiles = m.getGameTiles();

        //filter the tiles that are bears
        LinkedList<GameTile> bears = new LinkedList<GameTile>();
        for(GameTile t : tiles){
            if(t.getWildLife()== WildLifeNames.BEAR){
                bears.add(t);
            }
        }

        //for each bear, get the adjacent tiles
        for(GameTile t : bears){
            LinkedList<Tile> adj = m.getNeighbours(t);

            //for each adjacent tile, check if it is a bear
            for(Tile a : adj){
                if(a instanceof GameTile && ((GameTile) a).getWildLife()==WildLifeNames.BEAR){
                    //now get the adjacent tiles of this bear
                    LinkedList<Tile> adj2 = m.getNeighbours(a);

                    //if there is no other bear in the adjacent tiles, then add 1 to the score
                    boolean found = false;
                    for(Tile a2 : adj2){
                        if(a2!=a && a2 instanceof GameTile && ((GameTile) a2).getWildLife()==WildLifeNames.BEAR){
                            found = true;
                            break;
                        }
                    }

                    if(!found){
                        sc.numberFound++;
                    }
                }
            }
        }

        //each pair of bears is counted twice, so divide the score by 2
        sc.numberFound = sc.numberFound/2;

        if(sc.numberFound==1)
            sc.points=4;
        else if(sc.numberFound==2)
            sc.points=11;
        else if(sc.numberFound==3)
            sc.points=19;
        else if(sc.numberFound==4)
            sc.points=27;

        return sc;

    }
}
