package fyp.nuigalway.ie.bodycompostionassistance.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.FoodEntry;

/**
 * Created by fergalbroderick on 24/01/2017.
 */

public class FoodHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "bodycomposition.db";
    private static final int DATABASE_VERSION = 1;


    public FoodHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String CREATE_FOOD_TABLE = "CREATE TABLE " + FoodEntry.TABLE_NAME + " ("
                + FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, "
                + FoodEntry.COLUMN_FOOD_CAL + " INTEGER NOT NULL DEFAULT 0, "
                + FoodEntry.COLUMN_FOOD_CARB + " REAL NOT NULL DEFAULT 0.0, "
                + FoodEntry.COLUMN_FOOD_FAT + " REAL NOT NULL DEFAULT 0.0, "
                + FoodEntry.COLUMN_FOOD_PROT + " REAL NOT NULL DEFAULT 0.0, "
                + FoodEntry.COLUMN_FOOD_DESC + " TEXT);";

        sqLiteDatabase.execSQL(CREATE_FOOD_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
