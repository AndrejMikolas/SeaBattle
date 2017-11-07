package com.example.andrej.seabattle.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.andrej.seabattle.R;
import com.example.andrej.seabattle.game_elements.TileType;
import com.example.andrej.seabattle.game_elements.Ship;
import com.example.andrej.seabattle.game_elements.Tile;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class BattleGroundView extends View {
    private static final int TILES_IN_ROW = 10;
    private static final int BACKGROUND_COLOR = Color.WHITE;
    private static final int SHIP_DOCK_COLOR = Color.WHITE;

    private Rect backGroundRect;
    private Rect shipDockRect;
    private Paint backgroundPaint;
    private Paint shipDockPaint;

    private ArrayList<Tile> tiles;
    private Ship[] ships = new Ship[6];

    private int spacing;
    private int fieldSize;
    private int padding;

    private boolean postInitDone = false;

    public static HashMap<TileType, Bitmap> fieldBitmaps = new HashMap<>();
    public static HashMap<Integer, Bitmap> shipBitmaps = new HashMap<>();

    public int movingShipIndex;

    public BattleGroundView(Context context) {
        super(context);
        init(null);
    }

    public BattleGroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BattleGroundView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public BattleGroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        // Load attributes
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(BACKGROUND_COLOR);
        shipDockPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shipDockPaint.setColor(SHIP_DOCK_COLOR);
        backGroundRect = new Rect();
        shipDockRect = new Rect();
        tiles = new ArrayList<>();
        loadBitmaps();
/*
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                findClickedField(x, y);
                invalidate();
                return false;
            }
        });
*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInit(canvas);
        canvas.drawRect(backGroundRect, backgroundPaint);
        canvas.drawRect(shipDockRect, shipDockPaint);
        redrawFields(canvas);
    }

    private void loadBitmaps() {
        fieldBitmaps.put(TileType.Water, BitmapFactory.decodeResource(getResources(), R.drawable.water));
        fieldBitmaps.put(TileType.Attacked, BitmapFactory.decodeResource(getResources(), R.drawable.cross));
        shipBitmaps.put(2, BitmapFactory.decodeResource(getResources(), R.drawable.ship_2));
        shipBitmaps.put(3, BitmapFactory.decodeResource(getResources(), R.drawable.ship_3));
        shipBitmaps.put(4, BitmapFactory.decodeResource(getResources(), R.drawable.ship_4));
    }

    private void findClickedField(float x, float y) {
        for(Tile tile: tiles){
            if(tile.getTileRect().contains((int)x, (int)y)){
                Toast.makeText(getContext(),
                        "position: " + tile.getxCoor() + " " + tile.getyCoor(),
                        Toast.LENGTH_SHORT).show();
                tile.setType(TileType.Attacked);
            }
        }
    }

    @Override
    public boolean performClick(){
        return false;
    }

    private void postInit(Canvas canvas) {
        if(postInitDone){
            return;
        }
        calculateParameters(canvas);
        backGroundRect.set(padding,
                padding,
                canvas.getWidth()- padding,
                canvas.getWidth()- padding);
        shipDockRect.set(padding,
                backGroundRect.bottom+ padding,
                canvas.getWidth()- padding,
                canvas.getWidth()+(fieldSize*4));
        canvas.drawRect(backGroundRect, backgroundPaint);
        canvas.drawRect(shipDockRect, shipDockPaint);
        createFields();
        createShips();
        postInitDone = true;
    }

    private void calculateParameters(Canvas canvas) {
        spacing = canvas.getWidth()/300;
        padding = canvas.getWidth()/15;
        fieldSize = (canvas.getWidth() - padding - padding - spacing*(TILES_IN_ROW -1))/ TILES_IN_ROW;
    }

    private void redrawFields(Canvas canvas) {
        for(Tile tile: tiles){
            canvas.drawBitmap(tile.getTileBitmap(), null, tile.getTileRect(), null);
        }
        for(Ship ship: ships){
            canvas.drawBitmap(ship.getShipBitmap(), null ,ship.getShipRect(), null);
        }
    }

    private void createFields(){
        for(int x = 0; x < TILES_IN_ROW; x++){
            for(int y = 0; y < TILES_IN_ROW; y++){
                Tile newTile = new Tile(
                        padding + (spacing+fieldSize)*x,
                        padding + (spacing+fieldSize)*y,
                        x,
                        y,
                        fieldSize,
                        getContext());
                tiles.add(newTile);
            }
        }
    }

    private void createShips(){
        ships[0] = new Ship(padding, padding +(TILES_IN_ROW *fieldSize)+fieldSize, 4, fieldSize, getContext());
        ships[1] = new Ship(padding, padding +(TILES_IN_ROW *fieldSize)+fieldSize*2, 3, fieldSize, getContext());
        ships[2] = new Ship(padding + fieldSize*4, padding +(TILES_IN_ROW *fieldSize)+fieldSize*2, 3, fieldSize, getContext());
        ships[3] = new Ship(padding, padding +(TILES_IN_ROW *fieldSize)+fieldSize*3, 2, fieldSize, getContext());
        ships[4] = new Ship(padding + fieldSize*3, padding +(TILES_IN_ROW *fieldSize)+fieldSize*3, 2, fieldSize, getContext());
        ships[5] = new Ship(padding + fieldSize*6, padding +(TILES_IN_ROW *fieldSize)+fieldSize*3, 2, fieldSize, getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                final float x = motionEvent.getX();
                final float y = motionEvent.getY();
                this.movingShipIndex = getMovingShipIndex(x, y);
                break;
            case MotionEvent.ACTION_UP:

                float xUp = motionEvent.getX();
                float yUp = motionEvent.getY();
                /*
                findClickedField(xUp, yUp);
                invalidate();
                */
                if(movingShipIndex >= 0){
                    placeShip(xUp, yUp);
                    movingShipIndex = -1;
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingShipIndex >= 0) {
                    ships[movingShipIndex].moveToCenter(motionEvent.getX(), motionEvent.getY());
                    invalidate();
                }
                break;
        }
        return true;
    }

    public int getMovingShipIndex(float x, float y){
        int index = -1;
        for(int i = 0; i < ships.length; i++){
            if(ships[i].getShipRect().contains((int)x, (int)y)){
                index = i;
                break;
            }
        }
        return index;
    }

    private void placeShip(float definedX, float definedY){
        for(Tile tile: tiles){
            if(tile.getTileRect().contains((int)definedX, (int)definedY)){
                ships[movingShipIndex].moveToCenter(tile.getxPos(), tile.getyPos()+fieldSize/2);
                invalidate();
                break;
            }
            else {
                ships[movingShipIndex].moveToDefault();
                invalidate();
            }
        }
    }

    public void resetShips(){
        for(Ship ship: ships){
            ship.moveToDefault();
        }
    }

}
