package com.westerosatwar.got.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.westerosatwar.got.Model.King;

import java.util.ArrayList;

/**
 * Created by Arjun.
 */

public class DBHelper extends SQLiteOpenHelper implements Constants{


    private SQLiteDatabase database;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_KING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.delete(TABLE_KING, null, null);
        sqLiteDatabase.execSQL(CREATE_TABLE_KING);
    }

    public void insertKing(King king) {
        database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, king.getName());
        values.put(HIGHEST_RATING, king.getHigh_rating());
        values.put(RATING, king.getRating());
        values.put(BATTLES_WON, king.getBattlesWon());
        values.put(BATTLES_LOST, king.getBattlesLost());
        values.put(STRENGTH, king.getStrength());
        values.put(BATTLE_TYPE, king.getBattleType());
        long i = database.insert(TABLE_KING, null, values);
        Log.i("databseEvent inserted",i+"");
        database.close();
    }

    public void deleteKingsTable() {
        database = this.getWritableDatabase();
        database.delete(TABLE_KING, null, null);
        database.close();
    }

    public ArrayList<King> getKings() {
        database = this.getReadableDatabase();
        ArrayList<King> kings = new ArrayList<>();
        Cursor cursor = database.rawQuery("select * from " + TABLE_KING, null);
        cursor.moveToFirst();
        Log.i("databseEvent cursor",cursor.getCount()+"");
        for (int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            King king = new King();

            king.setName(cursor.getString(cursor.getColumnIndex(NAME)));
            king.setHigh_rating(cursor.getInt(cursor.getColumnIndex(HIGHEST_RATING)));
            king.setRating(cursor.getInt(cursor.getColumnIndex(RATING)));
            king.setBattlesWon(cursor.getInt(cursor.getColumnIndex(BATTLES_WON)));
            king.setBattlesLost(cursor.getInt(cursor.getColumnIndex(BATTLES_LOST)));
            king.setStrength(cursor.getString(cursor.getColumnIndex(STRENGTH)));
            king.setBattleType(cursor.getString(cursor.getColumnIndex(BATTLE_TYPE)));
            kings.add(king);
        }
        return kings;
    }



}
