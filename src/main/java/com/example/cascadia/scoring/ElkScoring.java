package com.example.cascadia.scoring;
import com.example.cascadia.board.Map;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.GameTile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class ElkScoring {

    public static Score elkScoringA(Map m){

        Score s = new Score();
        s.name = "Elk";
        s.description = "Summation of number of Elk that are adjacent in a straight line";
        s.points = 0;
        s.numberFound = 0;

        ArrayList<GameTile> tiles = new ArrayList<>( m.getGameTiles());

        //sort the tiles by row then by column
        tiles.sort((o1, o2) -> {
            if(o1.getRow()==o2.getRow())
                return o1.getCol()-o2.getCol();
            return o1.getRow()-o2.getRow();
        });

        for(int i=0;i<tiles.size();i++){
            GameTile t = tiles.get(i);
            if(t.getWildLife()!=null && t.getWildLife()==WildLifeNames.ELK){
                s.numberFound++;

                int sz =0;
                while(i< tiles.size()){
                    GameTile t1 = tiles.get(i);
                    if(t1.getRow()==t.getRow() && t1.getWildLife()==WildLifeNames.ELK){
                        sz++;
                        i++;
                    }
                    else
                        break;

                }

                if(sz==1)
                    s.points+=2;
                else if(sz==2)
                    s.points+=5;
                else if(sz==3)
                    s.points+=9;
                else if(sz==4)
                    s.points+=13;
            }
        }





        return s;
    }
}
