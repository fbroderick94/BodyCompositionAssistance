package fyp.nuigalway.ie.bodycompostionassistance;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MealResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_result);

        ArrayList<Uri> myMeal = (ArrayList<Uri>) getIntent().getSerializableExtra("foods");
    }
}
