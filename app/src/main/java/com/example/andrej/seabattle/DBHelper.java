package com.example.andrej.seabattle;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Andrej on 29.11.2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AppDatabase.db";
    public static final String CONTACTS_TABLE_NAME = "game_history";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_PLAYER1 = "player_1";
    public static final String CONTACTS_COLUMN_PLAYER2 = "player_2";
    public static final String CONTACTS_COLUMN_WINNER = "winner";
    public static final String CONTACTS_COLUMN_DATE = "game_date";
    public static final String CONTACTS_COLUMN_TIME = "game_time";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE "+CONTACTS_TABLE_NAME+" ("+CONTACTS_COLUMN_ID+" INTEGER PRIMARY KEY, "+
                CONTACTS_COLUMN_PLAYER1+" TEXT, "+CONTACTS_COLUMN_PLAYER2+" TEXT, "+CONTACTS_COLUMN_WINNER+" TEXT, "+
                CONTACTS_COLUMN_DATE+" TEXT, "+CONTACTS_COLUMN_TIME+" TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ CONTACTS_TABLE_NAME);
        onCreate(db);
    }


    public boolean insert(String player1, String player2, String winner, Date date, String time)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACTS_COLUMN_PLAYER1, player1);
        values.put(CONTACTS_COLUMN_PLAYER2, player2);
        values.put(CONTACTS_COLUMN_WINNER, winner);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        values.put(CONTACTS_COLUMN_DATE, df.format(date));
        values.put(CONTACTS_COLUMN_TIME, time);
        try{
            db.insert(CONTACTS_TABLE_NAME, null, values);
        }
        catch(Exception e){
            return false;
        }
        return true;
    }


    public int delete(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(CONTACTS_TABLE_NAME, "id = ?", new String[]{Integer.toString(id)});
    }


    public ArrayList<DataModelGameHistory> getAllRows(){
        ArrayList<DataModelGameHistory> result = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + CONTACTS_TABLE_NAME, null);
        res.moveToFirst();

        while(res.isAfterLast() == false){
            DataModelGameHistory model = new DataModelGameHistory();
            model.setId(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
            model.setPlayer1(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PLAYER1)));
            model.setPlayer2(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PLAYER2)));
            model.setWinner(res.getString(res.getColumnIndex(CONTACTS_COLUMN_WINNER)));
            model.setDate(res.getString(res.getColumnIndex(CONTACTS_COLUMN_DATE)));
            model.setTime(res.getString(res.getColumnIndex(CONTACTS_COLUMN_TIME)));
            result.add(model);
            res.moveToNext();
        }

        return result;
    }
}
