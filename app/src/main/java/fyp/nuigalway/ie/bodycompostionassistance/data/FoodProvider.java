package fyp.nuigalway.ie.bodycompostionassistance.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.*;

/**
 * Created by fergalbroderick on 31/01/2017.
 */

public class FoodProvider extends ContentProvider {

    public static final String LOG_TAG = FoodProvider.class.getSimpleName();



    public FoodHelper dbHelper;

    private static final int FOODS = 100;
    private static final int FOODS_ID = 101;

    private static final UriMatcher UM = new UriMatcher(UriMatcher.NO_MATCH);


    static
    {
        UM.addURI(FoodContract.CONTENT_AUTHORITY, "foods", FOODS);
        UM.addURI(FoodContract.CONTENT_AUTHORITY, "foods/#", FOODS_ID);
    }
    @Override
    public boolean onCreate() {

        dbHelper = new FoodHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String selection, String[] strings1, String order) {


        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor;

        int match = UM.match(uri);

        if(match == FOODS)
        {
            cursor = db.query(FoodEntry.TABLE_NAME, strings, selection, strings1, null, null, order);
        }
        else if(match == FOODS_ID)
        {
            selection = FoodEntry._ID + "=?";
            strings1 = new String[] {String.valueOf(ContentUris.parseId(uri))};

            cursor = db.query(FoodEntry.TABLE_NAME, strings, selection, strings1, null, null, order);
        }
        else
        {
            throw new IllegalArgumentException("Unknown query URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        final int match = UM.match(uri);

        if(match == FOODS)
        {
            return FoodEntry.LIST_CONTENT;
        }
        else if (match == FOODS_ID)
        {
            return FoodEntry.ITEM_CONTENT;
        }
        else
            throw new IllegalStateException("Unknown URI" + uri);

    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        int match = UM.match(uri);


        if(match == FOODS)
        {
            return insertFood(uri, contentValues);
        }
        else
        {
            throw new IllegalArgumentException("Cannot insert for this Uri: " + uri);
        }

    }

    private Uri insertFood(Uri uri, ContentValues cv)
    {
        String name = cv.getAsString(FoodEntry.COLUMN_FOOD_NAME);
        if(name == null)
        {
            throw new IllegalArgumentException("Name must be entered");
        }

        Integer cals = cv.getAsInteger(FoodEntry.COLUMN_FOOD_CAL);
        if(cals == null || cals < 0)
        {
            throw new IllegalArgumentException("Invalid Carlories entry");
        }

        Double carbs = cv.getAsDouble(FoodEntry.COLUMN_FOOD_CARB);
        if (carbs == null || carbs < 0)
        {
            throw new IllegalArgumentException("Invalid Carbohydrates entry");
        }

        Double fats = cv.getAsDouble(FoodEntry.COLUMN_FOOD_FAT);
        if (fats == null || fats < 0)
        {
            throw new IllegalArgumentException("Invalid Fats entry");
        }

        Double prot = cv.getAsDouble(FoodEntry.COLUMN_FOOD_PROT);
        if (prot == null || prot < 0)
        {
            throw new IllegalArgumentException("Invalid Protein entry");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long id = db.insert(FoodEntry.TABLE_NAME, null, cv);

        if(id==-1){
            Log.e(LOG_TAG, "Failed to insert row " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();


        int rowsDeleted;
        final int match = UM.match(uri);

        if(match == FOODS)
        {
            rowsDeleted = db.delete(FoodEntry.TABLE_NAME, s, strings);

        }
        else if(match == FOODS_ID){

            s = FoodEntry._ID + "=?";
            strings = new String[] {String.valueOf(ContentUris.parseId(uri))};

            rowsDeleted = db.delete(FoodEntry.TABLE_NAME, s, strings);
        }
        else
        {

            throw new IllegalArgumentException("Cannot delete " + uri);
        }

        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;

    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int match = UM.match(uri);


        if(match == FOODS)
        {
            return updateFood(uri, contentValues, s, strings);
        }
        else if(match == FOODS_ID)
        {
            s = FoodEntry._ID + "=?";
            strings = new String[] { String.valueOf(ContentUris.parseId(uri))};
            return updateFood(uri, contentValues, s, strings);

        }

        else
        {
            throw new IllegalArgumentException("Cannot insert for this Uri: " + uri);
        }

    }

    private int updateFood(Uri uri, ContentValues values, String selection, String[] selectionArgs) {


        if (values.containsKey(FoodEntry.COLUMN_FOOD_NAME))
        {
            String name = values.getAsString(FoodEntry.COLUMN_FOOD_NAME);
            if(name == null)
            {
                throw new IllegalArgumentException("Food requires a name");
            }
        }

        if (values.containsKey(FoodEntry.COLUMN_FOOD_CAL))
        {

            Integer cals = values.getAsInteger(FoodEntry.COLUMN_FOOD_CAL);
            if(cals == null || cals < 0)
            {
                throw new IllegalArgumentException("Food requires a Calorie value");
            }
        }

        if (values.containsKey(FoodEntry.COLUMN_FOOD_CARB))
        {

            Double carbs = values.getAsDouble((FoodEntry.COLUMN_FOOD_CARB));
            if(carbs == null || carbs < 0)
            {
                throw new IllegalArgumentException("Food requires a Carbohydrate value");
            }
        }

        if (values.containsKey(FoodEntry.COLUMN_FOOD_FAT))
        {

            Double fats = values.getAsDouble((FoodEntry.COLUMN_FOOD_FAT));
            if(fats == null || fats < 0)
            {
                throw new IllegalArgumentException("Food requires a Fat value");
            }
        }

        if (values.containsKey(FoodEntry.COLUMN_FOOD_PROT))
        {

            Double prot = values.getAsDouble((FoodEntry.COLUMN_FOOD_PROT));
            if(prot == null || prot < 0)
            {
                throw new IllegalArgumentException("Food requires a Protein value");
            }
        }


        //If there is nothing to update, no point updating the db
        if(values.size() == 0)
        {
            return 0;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();


        int updatedRows = db.update(FoodEntry.TABLE_NAME, values, selection, selectionArgs);

        if(updatedRows !=0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return updatedRows;

    }
}
