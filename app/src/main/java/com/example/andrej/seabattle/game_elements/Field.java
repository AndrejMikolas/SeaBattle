package com.example.andrej.seabattle.game_elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.example.andrej.seabattle.R;

/**
 * Created by Andrej on 26.10.2017.
 */

public class Field extends View {
    private int mFieldColor = Color.BLUE;
    private TileType type;
    private int xPos;
    private int yPos;
    private int xCoor;
    private int yCoor;
    private int size;

    private Rect mFieldRect;
    private Paint mFieldPaint;
    private ImageView image;

    private Bitmap bitmap;

    public Field(Context context, int xPos, int yPos, int xCoor, int yCoor, int size) {
        super(context);
        this.xPos = xPos;
        this.yPos = yPos;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.size = size;
        this.type = TileType.Water;

        this.setMinimumHeight(size);
        this.setMinimumWidth(size);
        this.setX(xPos);
        this.setY(yPos);
        //this.setBackgroundResource(R.drawable.water_tileset);
        init(null);
    }

    public Field(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Field(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Field(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    public Rect getmFieldRect() {
        return mFieldRect;
    }

    public int getxCoor() {
        return xCoor;
    }

    public int getyCoor() {
        return yCoor;
    }

    private void init(@Nullable AttributeSet attrs) {
        // Load attributes
        mFieldRect = new Rect(xPos, yPos, xPos + size, yPos + size);
        mFieldPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //mFieldPaint.setColor(mFieldColor);
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.water);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mFieldRect, mFieldPaint);
        canvas.drawBitmap(bitmap, null, mFieldRect, null);


    }

    public void changeColor(){
        mFieldPaint.setColor(Color.GRAY);
        this.invalidateOutline();
    }
}
