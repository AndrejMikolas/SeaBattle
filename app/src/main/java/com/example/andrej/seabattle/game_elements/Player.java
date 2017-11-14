package com.example.andrej.seabattle.game_elements;

import com.example.andrej.seabattle.game_elements.Ship;
import com.example.andrej.seabattle.game_elements.Tile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andrej on 08.11.2017.
 */

public class Player implements Serializable{
    public String name;
    public Tile[][] defenseGround;
    public Tile[][] attackGround;
    public ArrayList<Ship> ships;
    public boolean onTurn;

    public Player(String name, boolean onTurn){
        this.name = name;
        this.onTurn = onTurn;
    }

}
