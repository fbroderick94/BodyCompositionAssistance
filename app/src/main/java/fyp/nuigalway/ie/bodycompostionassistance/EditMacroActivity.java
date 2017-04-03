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

public class EditMacroActivity extends AppCompatActivity {

    Long carbs;
    Long fats;
    Long prot;



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
                Intent homeIntent = new Intent(EditMacroActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

    }

    private Long getCarbsFromSharedPreferences(){
        SharedPreferences carbprefs = getSharedPreferences("User Carb values", MODE_PRIVATE);
       // SharedPreferences fatprefs = getSharedPreferences("User Fat values", MODE_PRIVATE);
        //SharedPreferences protprefs = getSharedPreferences("User Protein values", MODE_PRIVATE);
        Long returnedcarb = carbprefs.getLong("Carbs", 0);
        //Long returnedfat = fatprefs.getLong("Fats", 0);
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


        String carbString = carbvalue.getText().toString();
        if(carbString.contains("."))
        {
            carbString = carbString.substring(0, carbString.indexOf("."));
        }
        Long cCarb = Long.parseLong(carbString);
        String fatString = fatvalue.getText().toString();
        if(fatString.contains("."))
        {
            fatString = fatString.substring(0, fatString.indexOf("."));
        }
        Long aFat = Long.parseLong(fatString);
        String protString = protvalue.getText().toString();
        if(protString.contains("."))
        {
            protString = protString.substring(0, protString.indexOf("."));
        }
        Long aProt = Long.parseLong(protString);
        SharedPreferences prefs = getSharedPreferences("User Carb values", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong("Carbs",cCarb);
        editor.putLong("Fats", aFat);
        editor.putLong("Protein", aProt);
        editor.commit();
    }

    public Long getProt() {
        return prot;
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

    public Long getCarbs() {
        return carbs;
    }

    public Long getFats() {
        return fats;
    }

    public Long getCalories(){
        return calculation;
    }
}
