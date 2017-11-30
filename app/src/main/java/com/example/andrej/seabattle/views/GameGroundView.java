package com.example.andrej.seabattle.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.andrej.seabattle.R;
import com.example.andrej.seabattle.game_elements.Game;
import com.example.andrej.seabattle.game_elements.GameData;
import com.example.andrej.seabattle.game_elements.Ship;
import com.example.andrej.seabattle.game_elements.Tile;
import com.example.andrej.seabattle.game_elements.TileType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class GameGroundView extends View {
    private static final int TILES_IN_ROW = 10;
    private static final int BACKGROUND_COLOR = Color.WHITE;

    private Rect backGroundRect;
    private Paint backgroundPaint;

    private Tile[][] defenseTiles;
    private Tile[][] attackTiles;

    private int spacing;
    private int fieldSize;
    private int paddingSide;
    private int paddingTop;
    private boolean postInitDone = false;
    public int canvasWidth;

    public static HashMap<TileType, Bitmap> fieldBitmaps = new HashMap<>();

    public GameGroundView(Context context) {
        super(context);
        init(null);
    }

    public GameGroundView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GameGroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public GameGroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attrs) {
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(BACKGROUND_COLOR);
        backGroundRect = new Rect();
        loadBitmaps();
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
        backGroundRect.set(
                paddingSide,
                paddingTop,
                canvas.getWidth() - paddingSide - spacing,
                paddingTop + (TILES_IN_ROW * (fieldSize + spacing)) - spacing);
        this.attackTiles = createFields();
        postInitDone = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        postInit(canvas);
        canvas.drawRect(backGroundRect, backgroundPaint);
        redrawFields(canvas);
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
        paddingSide = canvas.getWidth()/20;
        paddingTop = paddingSide/4;
        fieldSize = (canvas.getWidth() - paddingSide - paddingSide - spacing*(TILES_IN_ROW -1))/ TILES_IN_ROW;
        canvasWidth = canvas.getWidth();
    }

    /**
     * Prekreslí všetky elementy na hracom poli.
     * @param canvas    Canvas, na ktorý jednotlivé elementy vykreslí.
     */
    private void redrawFields(Canvas canvas) {
        for(int x = 0; x < this.attackTiles.length; x++){
            for(int y = 0; y < this.attackTiles[x].length; y++) {
                try{
                    canvas.drawBitmap(this.attackTiles[x][y].getTileBitmap(),
                            null,
                            this.attackTiles[x][y].getTileRect(),
                            null);
                }
                catch (Exception e){ }
            }
        }
    }

    /**
     * Načítanie súborov obrázkov do bitmapových typov a uloženie do HashMapy
     */
    private void loadBitmaps() {
        fieldBitmaps.put(TileType.Water, BitmapFactory.decodeResource(getResources(), R.drawable.water));
        fieldBitmaps.put(TileType.ShipHit, BitmapFactory.decodeResource(getResources(), R.drawable.cross));
        fieldBitmaps.put(TileType.Attacked, BitmapFactory.decodeResource(getResources(), R.drawable.dot));
    }

    public boolean makeAttackToField(float xPos, float yPos) {
        for(int x = 0; x < TILES_IN_ROW; x++){
            for(int y = 0; y < TILES_IN_ROW; y++) {
                if (this.attackTiles[x][y].getTileRect().contains((int) xPos, (int) yPos)) {
                    TileType type = this.defenseTiles[x][y].getType();
                    switch(type){
                        case Water:
                            if(this.attackTiles[x][y].getType() != TileType.Attacked){
                                this.attackTiles[x][y].setType(TileType.Attacked);
                                invalidate();
                                return false;
                            }
                            return true;
                            //break;
                        case Ship:
                            if(this.attackTiles[x][y].getType() != TileType.ShipHit){
                                this.attackTiles[x][y].setType(TileType.ShipHit);
                                if(GameData.getInstance().game.player1.onTurn){
                                    GameData.getInstance().game.player2.shipTilesRemaining -=1;
                                }
                                else {
                                    GameData.getInstance().game.player1.shipTilesRemaining -=1;
                                }
                                invalidate();
                            }
                            return true;
                            //break;
                        default:
                            break;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean performClick(){
        return false;
    }

    /**
     * Vypočíta, vytvorí a do ArrayListu uloží inštancie jednotlivých políčok na hracom poli.
     */
    public Tile[][] createFields(){
        Tile[][] attackGround = new Tile[TILES_IN_ROW][TILES_IN_ROW];
        for(int x = 0; x < TILES_IN_ROW; x++){
            for(int y = 0; y < TILES_IN_ROW; y++){
                Tile newTile = new Tile(
                        paddingSide + (spacing+fieldSize)*x,
                        paddingTop + (spacing+fieldSize)*y,
                        x,
                        y,
                        fieldSize,
                        getContext());
                attackGround[x][y] = newTile;
            }
        }
        return attackGround;
    }

    public Tile[][] getDefenseTiles() {
        return defenseTiles;
    }

    public void setDefenseTiles(Tile[][] defenseTiles) {
        this.defenseTiles = defenseTiles;
    }

    public Tile[][] getAttackTiles() {
        return attackTiles;
    }

    public void setAttackTiles(Tile[][] attackTiles) {
        this.attackTiles = attackTiles;
        invalidate();
    }

    public Rect getBackGroundRect() {
        return backGroundRect;
    }
}
