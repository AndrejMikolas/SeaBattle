package com.example.andrej.seabattle.game_elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.andrej.seabattle.views.BattleGroundView;

/**
 * Created by Andrej on 07.11.2017.
 */

public class Ship {
    private int defaultX;
    private int defaultY;
    private int xPos;
    private int yPos;
    private int xCoor;
    private int yCoor;
    private int fieldSize;
    private int length;

    private Rect shipRect;
    private Bitmap shipBitmap;
    private Context context;
    private ShipStatus shipStatus;
    private Paint shipPaint;

    public Ship(int xPos, int yPos, int length, int fieldSize, Context context) {
        this.defaultX = xPos;
        this.defaultY = yPos;
        this.xPos = defaultX;
        this.yPos = defaultY;
        this.length = length;
        this.fieldSize = fieldSize;
        this.context = context;
        this.shipRect = new Rect(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
        this.shipPaint = new Paint();
        shipPaint.setColor(Color.TRANSPARENT);
        shipPaint.setStyle(Paint.Style.FILL);

        this.shipStatus = ShipStatus.Untouched;
        this.shipBitmap = BattleGroundView.shipBitmaps.get(this.length);
    }

    public void moveToCenter(float x, float y){
        int centerPosX = (int) x;
        int centerPosY = (int) y;
        if(length%2==0){
            this.xPos = centerPosX-((length*fieldSize)/2);
        }
        else{
            this.xPos = centerPosX-(((length-1)*fieldSize)/2);
        }

        this.yPos = centerPosY-(fieldSize/2);
        this.shipRect.set(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
    }

    public void moveToDefault(){
        this.xPos = defaultX;
        this.yPos = defaultY;
        this.shipRect.set(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
    }

    public int getDefaultX() {
        return defaultX;
    }

    public int getDefaultY() {
        return defaultY;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getxCoor() {
        return xCoor;
    }

    public void setxCoor(int xCoor) {
        this.xCoor = xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    public void setyCoor(int yCoor) {
        this.yCoor = yCoor;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Rect getShipRect() {
        return shipRect;
    }

    public void setShipRect(Rect shipRect) {
        this.shipRect = shipRect;
    }

    public Bitmap getShipBitmap() {
        return shipBitmap;
    }

    public void setShipBitmap(Bitmap shipBitmap) {
        this.shipBitmap = shipBitmap;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ShipStatus getShipStatus() {
        return shipStatus;
    }

    public void setShipStatus(ShipStatus shipStatus) {
        this.shipStatus = shipStatus;
    }
}
