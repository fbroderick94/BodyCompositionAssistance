package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
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
import android.view.MotionEvent;
import android.view.View;
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


    private boolean hasFoodChanged = false;



    private View.OnTouchListener tl = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            hasFoodChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        Intent intent = getIntent();
        currentUri = intent.getData();


        if(currentUri == null){
            setTitle(getString(R.string.edit_activity_title_new_food));

            invalidateOptionsMenu();
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


        EditName.setOnTouchListener(tl);
        EditCal.setOnTouchListener(tl);
        EditCarbs.setOnTouchListener(tl);
        EditFats.setOnTouchListener(tl);
        EditProt.setOnTouchListener(tl);
        EditDesc.setOnTouchListener(tl);
    }



    private void saveFood() {
        String nameString = EditName.getText().toString().trim();
        String calString = EditCal.getText().toString().trim();
        String carbString = EditCarbs.getText().toString().trim();
        String fatString = EditFats.getText().toString().trim();
        String protString = EditProt.getText().toString().trim();
        String descString = EditDesc.getText().toString().trim();

        System.out.println("String: " + carbString);



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


        double carbohydrates = 0;
        if(!TextUtils.isEmpty(carbString))
        {
            carbohydrates = Double.parseDouble(carbString);
            System.out.println("Double: " + carbohydrates);
        }

        double fats = 0;
        if(!TextUtils.isEmpty(fatString))
        {
            fats = Double.parseDouble(fatString);
        }

        double protein = 0;
        if(!TextUtils.isEmpty(protString)) {
            protein = Double.parseDouble(protString);
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

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);

        if(currentUri == null){
            MenuItem item = menu.findItem(R.id.action_delete);
            item.setVisible(false);
        }
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
            showDeleteConfirmationDialog();
            return true;
        }
        else if(item.getItemId() == android.R.id.home){

            if(!hasFoodChanged)
            {
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

            DialogInterface.OnClickListener discardButtonClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //The discard button was clicked so navigate to parent
                    NavUtils.navigateUpFromSameTask(EditActivity.this);
                }
            };

            showUnsavedChangesDialog(discardButtonClickListener);
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
            double carb = cursor.getDouble(carbColumnIndex);
            double fat = cursor.getDouble(fatColumnIndex);
            double prot = cursor.getDouble(protColumnIndex);
            String desc = cursor.getString(descColumnIndex);

            EditName.setText(name);
            EditCal.setText(Integer.toString(cal));
            EditCarbs.setText(Double.toString(carb));
            EditFats.setText(Double.toString(fat));
            EditProt.setText(Double.toString(prot));
            EditDesc.setText(desc);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }


    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonListener){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_dialog_message);
        builder.setPositiveButton(R.string.discard, discardButtonListener);
        builder.setNegativeButton(R.string.continue_editing, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(dialogInterface != null){
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onBackPressed(){


        //If the user has not altered the food, handle the back button press normally
        if(!hasFoodChanged){
            super.onBackPressed();
            return;
        }


        //If the user has edited the food and made changes, create a dialog to warn them
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        //Display dialog that are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    private void showDeleteConfirmationDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {

                deleteFood();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(dialogInterface != null)
                {
                    dialogInterface.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void deleteFood(){

        if(currentUri != null){

            int rowsDeleted = getContentResolver().delete(currentUri, null, null);

            if(rowsDeleted == 0){
                Toast.makeText(this, getString(R.string.edit_delete_food_failed), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getString(R.string.edit_activity_food_successful), Toast.LENGTH_SHORT).show();
            }

            finish();

        }
    }
}
