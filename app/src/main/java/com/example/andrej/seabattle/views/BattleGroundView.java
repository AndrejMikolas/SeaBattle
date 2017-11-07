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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.andrej.seabattle.GameEngine;
import com.example.andrej.seabattle.R;
import com.example.andrej.seabattle.classes.FieldType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TODO: document your custom view class.
 */
public class BattleGroundView extends View {
    private static final int COUNT_IN_ROW = 10;
    private static final int MBACKGROUND_COLOR = Color.WHITE;

    private Rect mBackGroundRect;
    private Paint mBackgroundPaint;
    private int mPadding;

    private ArrayList<Tile> tiles;
    private int spacing;
    private int fieldSize;

    private boolean tilesDrawn = false;

    public static HashMap<FieldType, Bitmap> bitmaps = new HashMap<>();

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
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(MBACKGROUND_COLOR);
        mBackGroundRect = new Rect();
        tiles = new ArrayList<>();
        loadBitmaps();
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
    }

    private void loadBitmaps() {
        bitmaps.put(FieldType.Water, BitmapFactory.decodeResource(getResources(), R.drawable.water));
        bitmaps.put(FieldType.Attacked, BitmapFactory.decodeResource(getResources(), R.drawable.cross));
    }

    private void findClickedField(float x, float y) {
        for(Tile tile: tiles){
            if(tile.getTileRect().contains((int)x, (int)y)){
                Toast.makeText(getContext(),
                        "position: " + tile.getxCoor() + " " + tile.getyCoor(),
                        Toast.LENGTH_SHORT).show();
                tile.setType(FieldType.Attacked);
            }
        }
    }

    @Override
    public boolean performClick(){
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        calculateParameters(canvas);
        mBackGroundRect.left = mPadding;
        mBackGroundRect.top = mPadding;
        mBackGroundRect.bottom = canvas.getWidth()-mPadding;
        mBackGroundRect.right = canvas.getWidth()-mPadding;
        canvas.drawRect(mBackGroundRect, mBackgroundPaint);
        createFields();
        redrawFields(canvas);
    }

    private void redrawFields(Canvas canvas) {
        for(Tile tile: tiles){
            Log.i("tile type", tile.getType().toString());
            Log.i("tile bitmap", GameEngine.bitmaps.get(tile.getType()).toString());
            canvas.drawBitmap(tile.getTileBitmap(), null, tile.getTileRect(), null);
        }
    }

    private void calculateParameters(Canvas canvas) {
        spacing = canvas.getWidth() / 100;
        mPadding = canvas.getWidth()/20;
        fieldSize = (canvas.getWidth() - mPadding - mPadding - spacing*(COUNT_IN_ROW-1))/COUNT_IN_ROW;
    }

    private void createFields(){
        if(tilesDrawn){
            return;
        }
        for(int x = 0; x < COUNT_IN_ROW; x++){
            for(int y = 0; y < COUNT_IN_ROW; y++){
                Tile newTile = new Tile(
                        mPadding + (spacing+fieldSize)*x,
                        mPadding + (spacing+fieldSize)*y,
                        x,
                        y,
                        fieldSize,
                        getContext());
                //newTile.draw(canvas);
                tiles.add(newTile);
            }
        }
    }


}
