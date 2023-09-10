package com.example.cascadia.constants;

import javafx.scene.image.Image;

public enum WildLifeNames {
    HAWK("hawk"),
    ELK("elk"),
    BEAR("bear"),
    SALMON("salmon"),
    FOX("fox");


    private String value;
    private Image image, activeImage;
    private WildLifeNames(String str){
        value = str;

        try {
            image = new Image(getClass().getResource("/com/example/cascadia/assets/tokens/" + value + ".png").toURI().toString());
            activeImage = new Image(getClass().getResource("/com/example/cascadia/assets/tokens/" + value + "Active.png").toURI().toString());

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public  String toString(){
        return value;
    }

    public Image getImage(){
        return image;
    }

    public Image getActiveImage(){
        return activeImage;
    }


}
