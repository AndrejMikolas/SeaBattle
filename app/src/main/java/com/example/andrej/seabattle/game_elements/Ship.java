package com.example.andrej.seabattle.game_elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.example.andrej.seabattle.views.BattleGroundView;

import java.util.ArrayList;

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
    private boolean inDock;

    private Rect shipRect;
    private Bitmap shipBitmap;
    private Context context;
    private ShipStatus shipStatus;
    private Paint shipPaint;
    private Orientation orientation;

    public Ship(int xPos, int yPos, int length, int fieldSize, Context context) {
        this.defaultX = xPos;
        this.defaultY = yPos;
        this.xPos = defaultX;
        this.yPos = defaultY;
        this.length = length;
        this.inDock = true;
        this.fieldSize = fieldSize;
        this.context = context;
        this.shipRect = new Rect(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
        this.shipPaint = new Paint();
        this.orientation = Orientation.Landscape;
        shipPaint.setColor(Color.TRANSPARENT);
        shipPaint.setStyle(Paint.Style.FILL);
        this.shipStatus = ShipStatus.Untouched;
        this.shipBitmap = BattleGroundView.shipBitmaps.get(this.length);
    }

    public void moveToCoors(int x, int y, Tile mainTile){
        this.xCoor = x;
        this.yCoor = y;
        //moveToCenter(mainTile.getxPos(), mainTile.getyPos());
        this.xPos = mainTile.getxPos();
        this.yPos = mainTile.getyPos();
        refreshPosition();

    }

    private void refreshPosition() {
        if(orientation == Orientation.Landscape){
            this.shipRect.set(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
        }
        else{
            this.shipRect.set(xPos, yPos, xPos+fieldSize, yPos+(length*fieldSize));
        }
    }

    public void moveToCenter(float x, float y){
        /*
        if(length%2==0){
            this.xPos = (int)x - ((length*fieldSize)/2);
        }
        else{
            this.xPos = (int)x - (((length-1)*fieldSize)/2);
        }
        */
        this.yPos = (int) y;//-(fieldSize/2);
        this.xPos = (int) x;
        refreshPosition();
    }

    public void moveToDefault(){
        this.xPos = defaultX;
        this.yPos = defaultY;
        if(this.orientation == Orientation.Portrait){
            this.setOrientation(Orientation.Landscape);
        }
        this.shipRect.set(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
    }

    public void rotate(){
        if(orientation == Orientation.Landscape){
            setOrientation(Orientation.Portrait);
        }
        else{
            setOrientation(Orientation.Landscape);
        }
    }

    private static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        if(this.orientation == Orientation.Landscape){
            this.shipRect.set(xPos, yPos, xPos+(length*fieldSize), yPos+fieldSize);
            this.shipBitmap = RotateBitmap(this.shipBitmap, 90);
        }
        else{
            this.shipRect.set(xPos, yPos, xPos+fieldSize, yPos+(length*fieldSize));
            this.shipBitmap = RotateBitmap(this.shipBitmap, -90);
        }
    }

    public Orientation getOrientation() {
        return orientation;
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

    public boolean isInDock() {
        return inDock;
    }

    public void setInDock(boolean inDock) {
        this.inDock = inDock;
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
