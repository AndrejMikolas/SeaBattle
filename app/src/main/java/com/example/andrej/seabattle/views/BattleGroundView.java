package com.example.andrej.seabattle.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
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
    public ArrayList<Ship> ships;

    private int spacing;
    private int fieldSize;
    private int paddingSide;
    private int paddingTop;
    private boolean postInitDone = false;

    public static HashMap<TileType, Bitmap> fieldBitmaps = new HashMap<>();
    public static HashMap<Integer, Bitmap> shipBitmaps = new HashMap<>();

    public int movingShipIndex;

    public final Handler handler = new Handler();
    public Runnable longPressHandle;

    public BattleGroundView(Context context) {
        super(context, null);
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
        ships = new ArrayList<>();
        loadBitmaps();
        longPressHandle = new Runnable() {
            @Override
            public void run() {
                //TODO:
                //ships[movingShipIndex].rotate();
                ships.get(movingShipIndex).rotate();
                invalidate();
            }
        };
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInit(canvas);
        canvas.drawRect(backGroundRect, backgroundPaint);
        canvas.drawRect(shipDockRect, shipDockPaint);
        redrawFields(canvas);
    }

    /**
     * Načítanie súborov obrázkov do bitmapových typov a uloženie do HashMapy
     */
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

    /**
     * Vypočíta parametre, vykreslí pozadie herného poľa, doku a vytvorí políčka a lode.
     * @param canvas
     */
    private void postInit(Canvas canvas) {
        if(postInitDone){
            return;
        }
        calculateParameters(canvas);
        backGroundRect.set(paddingSide,
                paddingTop,
                canvas.getWidth() - paddingSide - spacing,
                paddingTop + (TILES_IN_ROW * (fieldSize + spacing)) - spacing);
        shipDockRect.set(paddingSide,
                backGroundRect.bottom + paddingSide,
                canvas.getWidth() - paddingSide,
                paddingTop + ((TILES_IN_ROW + 4) * (fieldSize + spacing)) + paddingSide);
        canvas.drawRect(backGroundRect, backgroundPaint);
        canvas.drawRect(shipDockRect, shipDockPaint);
        createFields();
        createShips();
        postInitDone = true;
    }

    /**
     * Vypočíta potrebné parametre pre vykresľovanie na základe rozmeru displeja. Vypočíta medzeru
     * medzi políčkami, odsadenie kracieho poľa od ľavej a pravej steny, odsadenie od hornej steny
     * a rozmer jedného políčka.
     * @param canvas    Canvas, na ktorý sa bude vykresľovať. Na základe jeho rozmerov vypočíta
     *                  parametre.
     */
    private void calculateParameters(Canvas canvas) {
        spacing = canvas.getWidth()/300;
        paddingSide = canvas.getWidth()/15;
        paddingTop = paddingSide*(4);
        fieldSize = (canvas.getWidth() - paddingSide - paddingSide - spacing*(TILES_IN_ROW -1))/ TILES_IN_ROW;
    }

    /**
     * Prekreslí všetky elementy na hracom poli.
     * @param canvas    Canvas, na ktorý jednotlivé elementy vykreslí.
     */
    private void redrawFields(Canvas canvas) {
        for(Tile tile: tiles){
            canvas.drawBitmap(tile.getTileBitmap(), null, tile.getTileRect(), null);
        }
        for(Ship ship: ships){
            canvas.drawBitmap(ship.getShipBitmap(), null ,ship.getShipRect(), null);
        }
    }

    /**
     * Vypočíta, vytvorí a do ArrayListu uloží inštancie jednotlivých políčok na hracom poli.
     */
    private void createFields(){
        for(int x = 0; x < TILES_IN_ROW; x++){
            for(int y = 0; y < TILES_IN_ROW; y++){
                Tile newTile = new Tile(
                        paddingSide + (spacing+fieldSize)*x,
                        paddingTop + (spacing+fieldSize)*y,
                        x,
                        y,
                        fieldSize,
                        getContext());
                tiles.add(newTile);
            }
        }
    }

    /**
     * Vytvorí lode, umiestené v "doku", teda v priestore pod herným poľom, odkiaľ sa budú brať a
     * ukladať na hracie pole.
     */
    private void createShips(){
        ships.add(new Ship(paddingSide, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize * 2, 4, fieldSize, getContext()));
        ships.add(new Ship(paddingSide, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize*3, 3, fieldSize, getContext()));
        ships.add(new Ship(paddingSide + fieldSize*4, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize*3, 3, fieldSize, getContext()));
        ships.add(new Ship(paddingSide, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize*4, 2, fieldSize, getContext()));
        ships.add(new Ship(paddingSide + fieldSize*3, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize*4, 2, fieldSize, getContext()));
        ships.add(new Ship(paddingSide + fieldSize*6, paddingTop +(TILES_IN_ROW *fieldSize)+fieldSize*4, 2, fieldSize, getContext()));
    }

    /**
     * Obstaráva dotyky a pohyby na obrazovke.
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                final float x = motionEvent.getX();
                final float y = motionEvent.getY();
                this.movingShipIndex = getTouchingShipIndex(x, y);
                handler.postDelayed(longPressHandle, 500);
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
                handler.removeCallbacks(longPressHandle);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                handler.removeCallbacks(longPressHandle);
                if(movingShipIndex >= 0) {

                    ships.get(movingShipIndex).moveToCenter(motionEvent.getX(), motionEvent.getY());
                    //ships[movingShipIndex].moveToCenter(motionEvent.getX(), motionEvent.getY());
                    invalidate();
                }
                break;

        }
        return true;
    }

    /**
     * Vráti index lode v poli, na ktorú bolo kliknuté.
     * @param x     X-súradnica zaregistrovaného dotyku
     * @param y     Y-súradnica zaregistrovaného dotyku
     * @return      index lode v poli, na ktorú bolo kliknuté
     */
    public int getTouchingShipIndex(float x, float y){
        int index = -1;
        for(int i = 0; i < ships.size(); i++){
            if(ships.get(i).getShipRect().contains((int)x, (int)y)){
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Umiestnenie lode do hracieho poľa a prichytenie do najbližšieho políčka.
     * @param definedX      X-súradnica, kde má byť loď umiestnená
     * @param definedY      Y-súradnica, kde má byť loď umiestnená
     */
    private void placeShip(float definedX, float definedY){
        boolean found = false;
        for(Tile tile: tiles){
            if(tile.getTileRect().contains((int)definedX, (int)definedY)){
                //ships.get(movingShipIndex).moveToCenter(tile.getxPos(), tile.getyPos()+fieldSize/2);
                ships.get(movingShipIndex).moveToCoors(tile.getxCoor(), tile.getyCoor(), this.tiles);
                invalidate();
                found = true;
                break;
            }
        }
        if(!found){
            ships.get(movingShipIndex).moveToDefault();
            invalidate();
        }
        return;
    }

    /**
     * Vrátenie všetkých lodí do "doku"
     */
    public void resetShips(){
        for(int i = 0; i < ships.size(); i++){
            this.ships.get(i).moveToDefault();
        }
        invalidate();
    }

    public void getBattleGround(){
        for(Ship ship: ships){
            for(Tile tile:tiles){
                if(tile.getxCoor() == ship.getxCoor() && tile.getyCoor() == ship.getyCoor()){

                    tile.setType(TileType.Ship);
                    break;
                }
            }
        }
    }

}
