package com.example.andrej.seabattle.game_elements;

/**
 * Created by Andrej on 14.11.2017.
 */

public class GameData {
    private static GameData instance = null;
    public Game game;

    protected GameData(){}

    public static synchronized GameData getInstance() {
        if(instance == null){
            instance = new GameData();
        }
        return instance;
    }

    public static void setInstance(GameData instance) {
        GameData.instance = instance;
    }

}
