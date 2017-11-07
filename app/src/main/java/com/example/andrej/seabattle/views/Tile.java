package com.example.andrej.seabattle.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.andrej.seabattle.GameEngine;
import com.example.andrej.seabattle.R;
import com.example.andrej.seabattle.classes.FieldType;

/**
 * Created by Andrej on 07.11.2017.
 */

public class Tile {
    private FieldType type;
    private int xPos;
    private int yPos;
    private int xCoor;
    private int yCoor;
    private int size;

    private Rect tileRect;
    private Paint tilePaint;
    private Bitmap tileBitmap;
    private Context context;


    public Tile(int xPos, int yPos, int xCoor, int yCoor, int size, Context context) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.size = size;
        this.context = context;
        this.tileRect = new Rect(xPos, yPos, xPos+size, yPos+size);
        //this.tilePaint = new Paint();
        //tilePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.type = FieldType.Water;
        this.tileBitmap = BattleGroundView.bitmaps.get(this.type);
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
        this.tileBitmap = BattleGroundView.bitmaps.get(type);
        if(type == FieldType.Attacked){
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

}
