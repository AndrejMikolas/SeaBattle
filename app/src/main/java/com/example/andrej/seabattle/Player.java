package com.example.andrej.seabattle;

import com.example.andrej.seabattle.game_elements.Ship;
import com.example.andrej.seabattle.game_elements.Tile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Andrej on 08.11.2017.
 */

public class Player implements Serializable{
    public String name;
    public ArrayList<Tile> defenseGround;
    public ArrayList<Tile> attackGround;
    public ArrayList<Ship> ships;

    public Player(String name){
        this.name = name;
    }

}
