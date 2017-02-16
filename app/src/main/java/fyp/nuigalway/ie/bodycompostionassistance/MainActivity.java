package fyp.nuigalway.ie.bodycompostionassistance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodHelper;
import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.FoodEntry;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);


            }
        });


    }

    protected void onStart()
    {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {



        String[] results = {
                FoodEntry._ID,
                FoodEntry.COLUMN_FOOD_NAME,
                FoodEntry.COLUMN_FOOD_CAL,
                FoodEntry.COLUMN_FOOD_CARB,
                FoodEntry.COLUMN_FOOD_FAT,
                FoodEntry.COLUMN_FOOD_PROT

        };

       // Cursor cursor = db.query(FoodEntry.TABLE_NAME, results, null,null,null,null,null);
        Cursor cursor = getContentResolver().query(FoodEntry.CONTENT_URI , results, null, null, null);

        TextView displayView = (TextView) findViewById(R.id.text_view_food);
        try {


            displayView.setText("Number of rows in foods database table: " + cursor.getCount());
            //displayView.append("\n" + FoodEntry._ID + " - " + FoodEntry.COLUMN_FOOD_NAME + "\n");

            int idIndex = cursor.getColumnIndex(FoodEntry._ID);
            int nameIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME);



            while(cursor.moveToNext()){
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                displayView.append(("\n" + id + " - " + name));
            }

        } finally {

            cursor.close();
        }
    }

}
