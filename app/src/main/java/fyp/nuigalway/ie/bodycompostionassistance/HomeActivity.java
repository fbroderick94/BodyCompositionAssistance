package fyp.nuigalway.ie.bodycompostionassistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        LinearLayout Create_Meal = (LinearLayout) findViewById(R.id.create_meal);
        LinearLayout edit_food = (LinearLayout) findViewById(R.id.edit_food);
        LinearLayout edit_macros = (LinearLayout) findViewById(R.id.edit_macros);
        LinearLayout log_off = (LinearLayout) findViewById(R.id.log_off);


        Create_Meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CreateMealActivity.class);
                startActivity(intent);
            }
        });


        edit_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        edit_macros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, EditMacroActivity.class);
                startActivity(intent);
            }
        });


        log_off.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent loginintent=new Intent(HomeActivity.this,LoginActivity.class);
                loginintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(loginintent);

            }
        });


    }
}
