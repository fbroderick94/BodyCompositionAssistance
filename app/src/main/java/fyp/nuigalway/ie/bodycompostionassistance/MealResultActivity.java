package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract;

public class MealResultActivity extends AppCompatActivity  {

    public int usersCals = 3266;
    public int usersCarbs = 163;
    public int usersFats = 145;
    public int usersProt = 327;

    private Uri currentUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_result);

        ArrayList<Uri> myMeal = (ArrayList<Uri>) getIntent().getSerializableExtra("foods");


        String[] results = {
                FoodContract.FoodEntry._ID,
                FoodContract.FoodEntry.COLUMN_FOOD_NAME,
                FoodContract.FoodEntry.COLUMN_FOOD_CAL,
                FoodContract.FoodEntry.COLUMN_FOOD_CARB,
                FoodContract.FoodEntry.COLUMN_FOOD_FAT,
                FoodContract.FoodEntry.COLUMN_FOOD_PROT,
                FoodContract.FoodEntry.COLUMN_FOOD_DESC
        };

        if(myMeal.size() == 1){
            currentUri = myMeal.get(0);

            Cursor cursor = getContentResolver().query(currentUri, results, null, null, null);

            String name = "Null";
            int cal=0;
            double carb=0;
            double fat=0;
            double prot=0;
            String desc;


            if(cursor.moveToFirst())
            {
                int nameColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_NAME);
                int calColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CAL);
                int carbColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CARB);
                int fatColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_FAT);
                int protColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_PROT);
                int descColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_DESC);


                name  = cursor.getString(nameColumnIndex);
                cal = cursor.getInt(calColumnIndex);
                carb = cursor.getDouble(carbColumnIndex);
                fat = cursor.getDouble(fatColumnIndex);
                prot = cursor.getDouble(protColumnIndex);
                desc = cursor.getString(descColumnIndex);

            }

            double Proteindivision = usersProt/prot;
            double weight = 100 * Proteindivision;
            round(weight, 2);
            System.out.println("Eat " + weight + " grams of " + name);

        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }



}



