package com.example.andrej.seabattle.game_elements;

import java.io.Serializable;

/**
 * Created by Andrej on 08.11.2017.
 */

public class Game implements Serializable{
    public GameType gameType;
    public String gameName;
    public Player player1;
    public Player player2;

    public boolean onTurn;

}
