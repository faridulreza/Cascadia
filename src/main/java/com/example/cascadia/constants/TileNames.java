package com.example.cascadia.constants;

public enum TileNames {
    MOUNTAIN("mountain"),
    DESERT("desert"),
    FOREST("forest"),
    SWAMP("swamp"),
    LAKE ("lake");


    private String value;
    private TileNames(String str){
        value = str;
    }

    public  String toString(){
        return value;
    }
}
