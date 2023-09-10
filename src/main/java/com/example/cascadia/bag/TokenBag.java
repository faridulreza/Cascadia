package com.example.cascadia.bag;

import com.example.cascadia.constants.WildLifeNames;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TokenBag {
    static LinkedList<WildLifeNames> tokenBag = new LinkedList<>();

    static {
        for (int i = 0; i <20 ; i++) {
            tokenBag.add(WildLifeNames.BEAR);
            tokenBag.add(WildLifeNames.HAWK);
            tokenBag.add(WildLifeNames.ELK);
            tokenBag.add(WildLifeNames.SALMON);
            tokenBag.add(WildLifeNames.FOX);
        }

        //shuffle the bag
        Collections.shuffle(tokenBag);
    }

    public static WildLifeNames drawToken() {
        //randomly draw a token from the bag
        int index = (int) (Math.random() * tokenBag.size());
        return tokenBag.remove(index);
    }

    public static void returnToken(WildLifeNames token) {
        tokenBag.add(token);
    }
}
