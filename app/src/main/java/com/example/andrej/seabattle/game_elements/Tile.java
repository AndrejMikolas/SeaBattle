package com.example.andrej.seabattle.game_elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.andrej.seabattle.GameEngine;
import com.example.andrej.seabattle.R;
import com.example.andrej.seabattle.views.BattleGroundView;

/**
 * Created by Andrej on 07.11.2017.
 */

public class Tile {
    private TileType type;
    private int xPos;
    private int yPos;
    private int xCoor;
    private int yCoor;
    private int size;

    private Rect tileRect;
    private Bitmap tileBitmap;
    private Context context;

    public Tile(){
        this(0, 0, 0, 0, 0, null);
    }

    public Tile(int xPos, int yPos, int xCoor, int yCoor, int size, Context context) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.size = size;
        this.context = context;
        this.tileRect = new Rect(xPos, yPos, xPos+size, yPos+size);
        this.type = TileType.Water;
        this.tileBitmap = BattleGroundView.fieldBitmaps.get(this.type);
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
        this.tileBitmap = BattleGroundView.fieldBitmaps.get(type);
        if(type == TileType.Attacked){
            GameEngine.playSound(R.raw.splash2);
        }
    }

    public Rect getTileRect() {
        return tileRect;
    }

    public void setTileRect(Rect tileRect) {
        this.tileRect = tileRect;
    }

    public Bitmap getTileBitmap() {
        return tileBitmap;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

}
