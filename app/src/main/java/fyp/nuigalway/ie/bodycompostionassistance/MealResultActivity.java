package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract;

public class MealResultActivity extends AppCompatActivity  {

    public int usersCals = 600;
    public double usersCarbs = 60;
    public double usersFats = 17.77;
    public double usersProt = 50;

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

        calculateMeal(myMeal, results);


    }


    private void calculateMeal(ArrayList<Uri> myMeal, String[] results)
    {

            currentUri = myMeal.get(0);

            Cursor cursor = getContentResolver().query(currentUri, results, null, null, null);

            String name = null, name2=null, name3=null;
            int cal=0, cal2=0, cal3=0;
            double carb=0, carb2=0, carb3=0;
            double fat=0, fat2=0, fat3=0;
            double prot=0, prot2=0, prot3=0;
            String desc, desc2, desc3;
            double weight;
            double calories;
            double carbs;
            double protein;
            double fats;



            int nameColumnIndex;
            int calColumnIndex;
            int carbColumnIndex;
            int fatColumnIndex;
            int protColumnIndex;
            int descColumnIndex;


            if(cursor.moveToFirst())
            {
                 nameColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_NAME);
                 calColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CAL);
                 carbColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CARB);
                 fatColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_FAT);
                 protColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_PROT);
                 descColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_DESC);


                name  = cursor.getString(nameColumnIndex);
                cal = cursor.getInt(calColumnIndex);
                carb = cursor.getDouble(carbColumnIndex);
                fat = cursor.getDouble(fatColumnIndex);
                prot = cursor.getDouble(protColumnIndex);
                desc = cursor.getString(descColumnIndex);

            }

            if(myMeal.size()==1) {

                double Proteindivision = usersProt / prot;
                weight = 100 * Proteindivision;
                calories = cal * Proteindivision;
                carbs = carb * Proteindivision;
                protein = prot * Proteindivision;
                fats = fat * Proteindivision;
                round(carbs, 1);
                round(protein, 1);
                round(fats, 1);
                round(calories, 0);
                round(weight, 0);


                TextView food1weight = (TextView) findViewById(R.id.food1_weight);
                food1weight.setText("Food 1: " + name + ": " + round(weight, 0) + " grams");
            }
            /*float m1 = (float) carbs;
            float m2 = (float) protein;
            float m3 = (float) fats;
            float values[]={m1,m2,m3};
            RelativeLayout linear=(RelativeLayout) findViewById(R.id.linear);

            values=calculateData(values);
            linear.addView(new PieChart(this,values));*/

        if(myMeal.size()>1)
        {
            currentUri = myMeal.get(1);

             cursor = getContentResolver().query(currentUri, results, null, null, null);

            if(cursor.moveToFirst()) {
                nameColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_NAME);
                calColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CAL);
                carbColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_CARB);
                fatColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_FAT);
                protColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_PROT);
                descColumnIndex = cursor.getColumnIndex(FoodContract.FoodEntry.COLUMN_FOOD_DESC);

                name2 = cursor.getString(nameColumnIndex);
                cal2     = cursor.getInt(calColumnIndex);
                carb2 = cursor.getDouble(carbColumnIndex);
                fat2  = cursor.getDouble(fatColumnIndex);
                prot2 = cursor.getDouble(protColumnIndex);
                desc2 = cursor.getString(descColumnIndex);
            }

            if(myMeal.size()==2)
            {
                int mealcal = cal + cal2;
                double mealcarb = carb + carb2;
                double mealfat = fat + fat2;
                double mealprot = prot + prot2;
                double division;
                if(mealcarb > mealfat && mealcarb> mealprot)
                {
                    division = usersCarbs/mealcarb;

                }
                else if(mealfat>mealcarb && mealfat>mealprot)
                {
                    division = usersFats/mealfat;
                }
                else
                {
                    division = usersProt/mealprot;
                }

                calories = cal*division + cal2*division;
                carbs = carb*division + carb2*division;
                fats = fat*division + fat2*division;
                protein = prot*division + prot2*division;

                TextView food1weight = (TextView) findViewById(R.id.food1_weight);
                //food1weight.setText("Food 1: " + name + ": " + round(weight, 0) + " grams");
            }
        }

        if(myMeal.size() == 3)
        {

        }
    }

    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private float[] calculateData(float[] data) {

        float total=0;
        for(int i=0;i<data.length;i++)
        {
            total+=data[i];
        }
        for(int i=0;i<data.length;i++)
        {
            data[i]=360*(data[i]/total);
        }

        return data;

    }


}

class PieChart extends View
{
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float[] value_degree;
    int[] colours = {Color.BLUE, Color.GREEN, Color.GRAY};
    RectF rectf = new RectF(30,30,600,600);
    float temp = 0;

    public PieChart(Context context, float[] vals)
    {
        super(context);
        value_degree = new float[vals.length];
        for(int i=0; i<vals.length; i++)
        {
            value_degree[i] = vals[i];
        }

    }

    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        temp = 0;

        for(int i=0; i<value_degree.length;i++)
        {

            if(i==0){
                paint.setColor(colours[i]);
                canvas.drawArc(rectf, 0, value_degree[i], true, paint);

            }
            else
            {

                temp += value_degree[i-1];
                paint.setColor(colours[i]);
                canvas.drawArc(rectf, temp, value_degree[i], true, paint);
            }

        }
    }
}


