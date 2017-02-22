package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.*;
import fyp.nuigalway.ie.bodycompostionassistance.data.FoodHelper;

public class EditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{


    private EditText EditName;
    private EditText EditCal;
    private EditText EditCarbs;
    private EditText EditFats;
    private EditText EditProt;
    private EditText EditDesc;

    private static final int FOOD_LOADER = 0;
    private Uri currentUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Intent intent = getIntent();
        currentUri = intent.getData();


        if(currentUri == null){
            setTitle(getString(R.string.edit_activity_title_new_food));
        }
        else {
            setTitle(getString(R.string.edit_activity_title_edit_food));

            getLoaderManager().initLoader(FOOD_LOADER, null, this);
        }

        EditName = (EditText) findViewById(R.id.edit_food_name);
        EditCal = (EditText) findViewById(R.id.edit_food_cal);
        EditCarbs = (EditText) findViewById(R.id.edit_food_carb);
        EditFats = (EditText) findViewById(R.id.edit_food_fat);
        EditProt = (EditText) findViewById(R.id.edit_food_prot);
        EditDesc = (EditText) findViewById(R.id.edit_food_desc);
    }



    private void saveFood() {
        String nameString = EditName.getText().toString().trim();
        String calString = EditCal.getText().toString().trim();
        String carbString = EditCarbs.getText().toString().trim();
        String fatString = EditFats.getText().toString().trim();
        String protString = EditProt.getText().toString().trim();
        String descString = EditDesc.getText().toString().trim();





        if(currentUri == null && TextUtils.isEmpty(nameString) && TextUtils.isEmpty(calString)
                && TextUtils.isEmpty(carbString) && TextUtils.isEmpty(fatString)
                && TextUtils.isEmpty(protString) && TextUtils.isEmpty(descString)){

            return;
        }

        int calories = 0;
        if(!TextUtils.isEmpty(calString))
        {
            calories = Integer.parseInt(calString);
        }


        int carbohydrates = 0;
        if(!TextUtils.isEmpty(carbString))
        {
            carbohydrates = Integer.parseInt(carbString);
        }

        int fats = 0;
        if(!TextUtils.isEmpty(fatString))
        {
            fats = Integer.parseInt(fatString);
        }

        int protein = 0;
        if(!TextUtils.isEmpty(protString)) {
            protein = Integer.parseInt(protString);
        }


        ContentValues cv = new ContentValues();
        cv.put(FoodEntry.COLUMN_FOOD_NAME, nameString);
        cv.put(FoodEntry.COLUMN_FOOD_CAL, calories);
        cv.put(FoodEntry.COLUMN_FOOD_CARB, carbohydrates);
        cv.put(FoodEntry.COLUMN_FOOD_FAT, fats);
        cv.put(FoodEntry.COLUMN_FOOD_PROT, protein);
        cv.put(FoodEntry.COLUMN_FOOD_DESC, descString);


        if(currentUri == null) {

            Uri nUri = getContentResolver().insert(FoodEntry.CONTENT_URI, cv);

            if (nUri == null) {
                Toast.makeText(this, getString(R.string.insert_food_failed), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.insert_food_successful), Toast.LENGTH_SHORT).show();
            }
        }else {

            int rowsAffected = getContentResolver().update(currentUri, cv, null, null);


            if(rowsAffected == 0)
            {
                Toast.makeText(this, getString(R.string.edit_food_failed), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getString(R.string.edit_food_successful), Toast.LENGTH_SHORT).show();
            }
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
            saveFood();
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


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] results = {
                FoodEntry._ID,
                FoodEntry.COLUMN_FOOD_NAME,
                FoodEntry.COLUMN_FOOD_CAL,
                FoodEntry.COLUMN_FOOD_CARB,
                FoodEntry.COLUMN_FOOD_FAT,
                FoodEntry.COLUMN_FOOD_PROT,
                FoodEntry.COLUMN_FOOD_DESC
        };

        return new CursorLoader(
                this,
                currentUri,
                results,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {


        if(cursor == null || cursor.getCount() <1)
        {
            return;
        }

        if(cursor.moveToFirst())
        {
            int nameColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME);
            int calColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_CAL);
            int carbColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_CARB);
            int fatColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_FAT);
            int protColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_PROT);
            int descColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_DESC);


            String name  = cursor.getString(nameColumnIndex);
            int cal = cursor.getInt(calColumnIndex);
            int carb = cursor.getInt(carbColumnIndex);
            int fat = cursor.getInt(fatColumnIndex);
            int prot = cursor.getInt(protColumnIndex);
            String desc = cursor.getString(descColumnIndex);

            EditName.setText(name);
            EditCal.setText(Integer.toString(cal));
            EditCarbs.setText(Integer.toString(carb));
            EditFats.setText(Integer.toString(fat));
            EditProt.setText(Integer.toString(prot));
            EditDesc.setText(desc);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
