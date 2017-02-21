package fyp.nuigalway.ie.bodycompostionassistance;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.*;
import fyp.nuigalway.ie.bodycompostionassistance.data.FoodHelper;

public class EditActivity extends AppCompatActivity {


    private EditText EditName;
    private EditText EditCal;
    private EditText EditCarbs;
    private EditText EditFats;
    private EditText EditProt;
    private EditText EditDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Intent intent = getIntent();
        Uri currentUri = intent.getData();


        if(currentUri == null){
            setTitle("Add a Food");
        }
        else
            setTitle(getString(R.string.edit_activity_title_edit_food));

        EditName = (EditText) findViewById(R.id.edit_food_name);
        EditCal = (EditText) findViewById(R.id.edit_food_cal);
        EditCarbs = (EditText) findViewById(R.id.edit_food_carb);
        EditFats = (EditText) findViewById(R.id.edit_food_fat);
        EditProt = (EditText) findViewById(R.id.edit_food_prot);
        EditDesc = (EditText) findViewById(R.id.edit_food_desc);
    }



    private void InsertFood() {
        String nameString = EditName.getText().toString().trim();
        String calString = EditCal.getText().toString().trim();
        String carbString = EditCarbs.getText().toString().trim();
        String fatString = EditFats.getText().toString().trim();
        String protString = EditProt.getText().toString().trim();
        String descString = EditDesc.getText().toString().trim();

        int calories = Integer.parseInt(calString);
        int carbohydrates = Integer.parseInt(carbString);
        int fats = Integer.parseInt(fatString);
        int protein = Integer.parseInt(protString);


        ContentValues cv = new ContentValues();
        cv.put(FoodEntry.COLUMN_FOOD_NAME, nameString);
        cv.put(FoodEntry.COLUMN_FOOD_CAL, calories);
        cv.put(FoodEntry.COLUMN_FOOD_CARB, carbohydrates);
        cv.put(FoodEntry.COLUMN_FOOD_FAT, fats);
        cv.put(FoodEntry.COLUMN_FOOD_PROT, protein);
        cv.put(FoodEntry.COLUMN_FOOD_DESC, descString);

        Uri nUri = getContentResolver().insert(FoodEntry.CONTENT_URI, cv);

        if(nUri == null)
        {
            Toast.makeText(this, getString(R.string.insert_food_failed), Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, getString(R.string.insert_food_successful), Toast.LENGTH_SHORT).show();
        }



    }





    public boolean onCreateOptionsMenu(Menu m)
    {
            getMenuInflater().inflate(R.menu.menu_edit, m);
            return true;
    }



    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.action_save){
            InsertFood();
            finish();
            return true;
        }
        else if(item.getItemId() == R.id.action_delete)
        {
            return true;
        }
        else if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }






}
