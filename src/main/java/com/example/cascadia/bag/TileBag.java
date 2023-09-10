package com.example.cascadia.bag;

import com.example.cascadia.constants.TileNames;
import com.example.cascadia.constants.WildLifeNames;
import com.example.cascadia.tiles.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static com.example.cascadia.constants.TileNames.*;
import static com.example.cascadia.constants.WildLifeNames.*;


public class TileBag {

    public static ArrayList<GameTile> tileBag = new ArrayList<>();
    public static ArrayList<StartingTile> startingTileBag = new ArrayList<>();




    public static boolean isEmpty(){
        return tileBag.isEmpty();
    }

    //randomly select a tile from the tile bag
    public static GameTile getTile(){
        int index = (int) (Math.random() * tileBag.size());
        return tileBag.remove(index);
    }

    public static StartingTile getStartingTile(){
        int index = (int) (Math.random() * startingTileBag.size());
        return startingTileBag.remove(index);
    }




    //initialize the tile bag
    static {

        //first tile set
        StartingTile st = new StartingTile();
        st.startingTile1 = new GameTile(null,
                                new TileNames[]{FOREST, LAKE},
                                new WildLifeNames[]{HAWK, SALMON,ELK});
        st.startingTile1.rotateLeft();

        st.startingTile2 = new GameTile(null,
                                new TileNames[]{SWAMP},
                                new WildLifeNames[]{HAWK}, true);

        st.startingTile3 = new GameTile(null,
                                new TileNames[]{MOUNTAIN, DESERT},
                                new WildLifeNames[]{BEAR, FOX});


        startingTileBag.add(st);

        tileBag.add(new GameTile(null,
                                new TileNames[]{DESERT},
                                new WildLifeNames[]{ELK},true));

        tileBag.add(new GameTile(null,
                                new TileNames[]{MOUNTAIN,SWAMP},
                                new WildLifeNames[]{BEAR,FOX,HAWK}));


        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT},
                new WildLifeNames[]{ELK},  true));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT},
                new WildLifeNames[]{FOX},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT},
                new WildLifeNames[]{SALMON}, true));

        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{HAWK},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT},
                new WildLifeNames[]{SALMON},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{SALMON,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{FOX,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{ELK,SALMON}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{ELK,FOX}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{SALMON,HAWK,FOX}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,SWAMP},
                new WildLifeNames[]{SALMON,ELK,FOX}));

        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{HAWK},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{SALMON},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{SALMON},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{FOX},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{SWAMP},
                new WildLifeNames[]{FOX},true));



        //second tile set
        StartingTile st2 = new StartingTile();
        st2.startingTile1 = new GameTile(null,
                new TileNames[]{SWAMP, LAKE},
                new WildLifeNames[]{HAWK, SALMON,FOX});
        st2.startingTile1.rotateLeft();

        st2.startingTile2 = new GameTile(null,
                new TileNames[]{DESERT},
                new WildLifeNames[]{FOX}, true);

        st2.startingTile3 = new GameTile(null,
                new TileNames[]{MOUNTAIN, FOREST},
                new WildLifeNames[]{BEAR, ELK});
        startingTileBag.add(st2);

        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, MOUNTAIN},
                new WildLifeNames[]{ELK,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, MOUNTAIN},
                new WildLifeNames[]{SALMON,BEAR,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, MOUNTAIN},
                new WildLifeNames[]{ELK,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, MOUNTAIN},
                new WildLifeNames[]{HAWK,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, MOUNTAIN},
                new WildLifeNames[]{SALMON,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, MOUNTAIN},
                new WildLifeNames[]{SALMON,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, SWAMP},
                new WildLifeNames[]{BEAR,HAWK}));


        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE,SWAMP},
                new WildLifeNames[]{SALMON,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE,SWAMP},
                new WildLifeNames[]{SALMON,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, SWAMP},
                new WildLifeNames[]{FOX,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, SWAMP},
                new WildLifeNames[]{SALMON,FOX}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE, SWAMP},
                new WildLifeNames[]{SALMON,HAWK,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{BEAR},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{BEAR},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{HAWK},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{HAWK},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{SALMON},true));



        //third tile set
        StartingTile st3 = new StartingTile();
        st3.startingTile1 = new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{BEAR, SALMON,ELK});
        st3.startingTile1.rotateLeft();

        st3.startingTile2 = new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{SALMON}, true);

        st3.startingTile3 = new GameTile(null,
                new TileNames[]{MOUNTAIN, SWAMP},
                new WildLifeNames[]{HAWK, FOX});
        startingTileBag.add(st3);

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{FOX,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{FOX,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{ELK,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{ELK,SALMON}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,FOREST},
                new WildLifeNames[]{ELK,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{LAKE},
                new WildLifeNames[]{SALMON}));

        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{FOX,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{ELK,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{ELK,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{FOX,SALMON}));

        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{SALMON,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{FOX,BEAR,SALMON}));
        tileBag.add(new GameTile(null,
                new TileNames[]{DESERT,LAKE},
                new WildLifeNames[]{ELK,HAWK,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,LAKE},
                new WildLifeNames[]{ELK,HAWK,FOX}));

        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{SALMON,BEAR,FOX}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{ELK,BEAR,FOX}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{SALMON,ELK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,FOREST},
                new WildLifeNames[]{ELK,BEAR,HAWK}));


        //fourth tile set
        StartingTile st4 = new StartingTile();
        st4.startingTile1 = new GameTile(null,
                new TileNames[]{MOUNTAIN, LAKE},
                new WildLifeNames[]{BEAR, HAWK,ELK});
        st4.startingTile1.rotateLeft();

        st4.startingTile2 = new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{ELK}, true);

        st4.startingTile3 = new GameTile(null,
                new TileNames[]{DESERT, SWAMP},
                new WildLifeNames[]{FOX, SALMON});
        startingTileBag.add(st4);

        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,FOREST},
                new WildLifeNames[]{FOX,ELK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,FOREST},
                new WildLifeNames[]{FOX,ELK,BEAR}));

        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,FOREST},
                new WildLifeNames[]{FOX,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{ELK,BEAR,SALMON}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{BEAR,HAWK}));


        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{BEAR,SALMON}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{ELK,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{ELK,FOX}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{BEAR,SALMON}));

        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN,SWAMP},
                new WildLifeNames[]{SALMON,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{HAWK,FOX}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN, DESERT},
                new WildLifeNames[]{ELK,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{ELK},true));

        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{ELK},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{BEAR},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{HAWK},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{HAWK},true));


        //fifth tile set
        StartingTile st5 = new StartingTile();
        st5.startingTile1 = new GameTile(null,
                new TileNames[]{FOREST, SWAMP},
                new WildLifeNames[]{ELK, FOX,HAWK});
        st5.startingTile1.rotateLeft();

        st5.startingTile2 = new GameTile(null,
                new TileNames[]{MOUNTAIN},
                new WildLifeNames[]{BEAR}, true);

        st5.startingTile3 = new GameTile(null,
                new TileNames[]{DESERT, LAKE},
                new WildLifeNames[]{SALMON, BEAR});
        startingTileBag.add(st5);


        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{BEAR},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,SWAMP},
                new WildLifeNames[]{HAWK,ELK,SALMON}));

        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{BEAR},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{ELK},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{FOX},true));


        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST},
                new WildLifeNames[]{FOX},true));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST,SWAMP},
                new WildLifeNames[]{FOX,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{FOX,HAWK}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{ELK,HAWK}));

        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{ELK,SALMON}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{ELK,FOX}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{FOX,BEAR}));
        tileBag.add(new GameTile(null,
                new TileNames[]{FOREST, DESERT},
                new WildLifeNames[]{ELK,BEAR}));


        //shuffling the tile bag
        Collections.shuffle(tileBag);
        Collections.shuffle(startingTileBag);

        //2 players game. remove 42 random tiles from the tile bag
        Random random = new Random();
        for (int i = 0; i < 42; i++) {
            tileBag.remove(random.nextInt(tileBag.size()));
        }


    }





}
