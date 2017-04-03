package fyp.nuigalway.ie.bodycompostionassistance;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditMacroActivity extends AppCompatActivity {

    Long carbs;
    Long fats;
    Long prot;

    boolean success = false;



    Long calculation;
    TextView calvalue;
    EditText carbvalue, fatvalue, protvalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_macro);

        carbvalue = (EditText) findViewById(R.id.etCarbs);
        fatvalue = (EditText) findViewById(R.id.etFats);
        protvalue = (EditText) findViewById(R.id.etProt);
        calvalue = (TextView) findViewById(R.id.tvCalorieValue);

        carbs = getCarbsFromSharedPreferences();
        fats = getFatsFromSharedPreference();
        prot = getProteinFromSharedPreference();


        if(carbs!=null){
            carbvalue.setText(carbs.toString());
        }
        if(fats!=null){
            fatvalue.setText(fats.toString());
        }
        if(prot!=null){
            protvalue.setText(prot.toString());
        }

        calculation =  ((carbs*4)+(fats*9)+(prot*4));
        calvalue.setText(calculation.toString());

        Button saveButton = (Button) findViewById(R.id.bMacro);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    storeMacrosToSharedPreference();
                if(success)
                {
                    Intent homeIntent = new Intent(EditMacroActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }
            }
        });

    }

    private Long getCarbsFromSharedPreferences(){
        SharedPreferences carbprefs = getSharedPreferences("User Carb values", MODE_PRIVATE);

        Long returnedcarb = carbprefs.getLong("Carbs", 0);

        return returnedcarb;
    }

    private Long getFatsFromSharedPreference(){
        SharedPreferences prefs = getSharedPreferences("User Carb values", MODE_PRIVATE);
        Long extractedvalue = prefs.getLong("Fats", 1);
        return extractedvalue;
    }

    private Long getProteinFromSharedPreference()
    {
        SharedPreferences prefs = getSharedPreferences("User Carb values", MODE_PRIVATE);
        Long extractedvalue = prefs.getLong("Protein", 2);
        return extractedvalue;
    }


    private void storeMacrosToSharedPreference(){

        Long cCarb=12345678910L, aFat=12345678910L, aProt=12345678910L;
        boolean isCarbValid=false, isFatValid=false, isProtValid=false;


        //Assigning Carbohydrates value
        String carbString = carbvalue.getText().toString();
        if(carbString.contains("."))
        {
            carbString = carbString.substring(0, carbString.indexOf("."));
        }

        try
        {
             cCarb = Long.parseLong(carbString);

            if(cCarb<=500)
            {
                isCarbValid = true;
            }
            else {
                Toast.makeText(this, "Carb value must be 500g or less", Toast.LENGTH_SHORT).show();
            }

        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Carbs not a valid number", Toast.LENGTH_SHORT).show();
        }



        //Assigning Fats value
        String fatString = fatvalue.getText().toString();
        if(fatString.contains("."))
        {
            fatString = fatString.substring(0, fatString.indexOf("."));

        }
        try
        {
            aFat = Long.parseLong(fatString);
            if(aFat<=500)
            {
                isFatValid = true;
            }
            else {
                Toast.makeText(this, "Fat value must be 500g or less", Toast.LENGTH_SHORT).show();
            }
        }
        catch(NumberFormatException e)
        {
            Toast.makeText(this, "Fats not a valid number", Toast.LENGTH_SHORT).show();
        }


        //Assigning Protein value
        String protString = protvalue.getText().toString();
        if(protString.contains("."))
        {
            protString = protString.substring(0, protString.indexOf("."));
        }
        try
        {
            aProt = Long.parseLong(protString);
            if(aProt<=500)
            {
                isProtValid = true;

            }
            else
            {
                Toast.makeText(this, "Protein value must be 500g or less", Toast.LENGTH_SHORT).show();
            }

        }
        catch (NumberFormatException e)
        {
            Toast.makeText(this, "Protein not a valid number", Toast.LENGTH_SHORT).show();
        }

        if(isCarbValid && isFatValid && isProtValid)
        {
            SharedPreferences prefs = getSharedPreferences("User Carb values", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong("Carbs", cCarb);
            editor.putLong("Fats", aFat);
            editor.putLong("Protein", aProt);
            editor.commit();
            success = true;


        }


        System.out.println("Carb: " + isCarbValid);
        System.out.println("Fats: " + isFatValid);
        System.out.println("Protein: " + isProtValid);
        System.out.println("Success: " + success);
    }



    public boolean onCreateOptionsMenu(Menu m)
    {
        getMenuInflater().inflate(R.menu.menu_editmacros, m);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);


        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {

        if(item.getItemId() == android.R.id.home){


                NavUtils.navigateUpFromSameTask(this);
                return true;
        }


        return super.onOptionsItemSelected(item);

    }

    public void onBackPressed(){


            super.onBackPressed();
            return;
    }


}
