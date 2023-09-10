package com.example.cascadia.scoring;

import com.example.cascadia.board.Map;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;
import com.example.cascadia.tiles.Tile;

import java.util.HashSet;
import java.util.LinkedList;

public class SalmonScoring {



    public static Score salmonScoringA(Map m) {
        Score sc = new Score();
        sc.name = "Salmon";
        sc.description = "Summation of number of runs of salmon";
        sc.points = 0;
        sc.numberFound = 0;

        LinkedList<GameTile> tiles = m.getGameTiles();

        HashSet<GameTile> validSalmon = new HashSet<>();
        for(GameTile t : tiles){
            if(t.getWildLife()== WildLifeNames.SALMON){

                //if a salmon has more that 2 adjacent salmon, then it is part of a run by itself
                //so remove it from the set
                LinkedList<Tile> adj = m.getNeighbours(t);
                int count = 0;
                for(Tile t1 : adj){
                    if(t1 instanceof GameTile && ((GameTile) t1).getWildLife()==WildLifeNames.SALMON){
                        count++;
                    }
                }

                if(count<=2){
                    validSalmon.add(t);
                }
            }
        }



        //run a DFS from each salmon tile
        HashSet<GameTile> visited = new HashSet<>();

        for(GameTile t : validSalmon){
            if(!visited.contains(t)){
                sc.numberFound++;
                int sz = dfs(m,t, visited, validSalmon);

                if(sz==1)
                    sc.points+=2;
                else if(sz==2)
                    sc.points+=4;
                else if(sz==3)
                    sc.points+=7;
                else if(sz==4)
                    sc.points+=11;
                else if(sz==5)
                    sc.points+=15;
                else if(sz==6)
                    sc.points+=20;
                else if(sz>=7)
                    sc.points+=26;
            }
        }

        return sc;
    }

    private static int dfs(Map m, GameTile t, HashSet<GameTile> visited, HashSet<GameTile> validSalmon) {
        visited.add(t);
        int sz = 1;
        LinkedList<Tile> adj = m.getNeighbours(t);
        for(Tile t1 : adj){
            if(t1 instanceof  GameTile){
                GameTile gt = (GameTile)t1;
                if(validSalmon.contains(gt) && !visited.contains(gt))
                    sz+=dfs(m,gt,visited,validSalmon);
            }
        }
        return sz;
    }
}
