package com.westerosatwar.got.database;

/**
 * Created by Arjun on 1/7/2017.
 */

public interface Constants {

    String NAME = "name";
    String HIGHEST_RATING = "highest_rating";
    String RATING = "rating";
    String BATTLES_WON = "battles_won";
    String BATTLES_LOST = "battles_lost";
    String STRENGTH = "strength";
    String BATTLE_TYPE = "battle_type";

    String TABLE_KING = "kings";
    String DATABASE_NAME = "got-kings";
    String CREATE_TABLE_KING = "CREATE TABLE "
            + TABLE_KING + "( _id INTEGER PRIMARY KEY," + NAME + " TEXT,"
            + HIGHEST_RATING + " INTEGER," + RATING + " INTEGER,"
            + BATTLES_WON + " INTEGER," + BATTLES_LOST + " INTEGER,"
            + STRENGTH+ " TEXT," + BATTLE_TYPE + " TEXT)";

    String AMBUSH = "ambush";
    String SIEGE = "siege";
    String PITCHED_BATTLE = "pitched battle";

    String WIN = "win";
    String LOSS = "loss";
}
