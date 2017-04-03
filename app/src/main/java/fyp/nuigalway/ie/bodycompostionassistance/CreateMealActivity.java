package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract;

import static android.widget.Toast.makeText;

public class CreateMealActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SearchView.OnQueryTextListener {

    private static final int FOOD_LOADER = 0;
    ArrayList<Uri> foods;
    FoodAdapter cAdapter;

    String cursorFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);
        foods = new ArrayList<>();


        final ListView displayView = (ListView) findViewById(R.id.text_view_food_Meal);

        View emptyView = findViewById(R.id.empty_viewMeal);
        displayView.setEmptyView(emptyView);


        cAdapter = new FoodAdapter(this, null);
        displayView.setAdapter(cAdapter);



        displayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private SparseArray<Boolean> hasClicked = new SparseArray<Boolean>();
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int position, long id) {


                Uri currentUri = ContentUris.withAppendedId(FoodContract.FoodEntry.CONTENT_URI, id);
                if(hasClicked.get(position, false))
                {
                    //itemView.setBackgroundColor(Color.WHITE);
                    hasClicked.put(position, false);
                    foods.remove(currentUri);
                }
                else
                {
                    //itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    hasClicked.put(position, true);
                    foods.add(currentUri);
                }






            }
        });




        Button createMealButton = (Button) findViewById(R.id.bCreateMeal);
        createMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(foods.size()==0){
                   Toast.makeText(getApplicationContext(), "No food items have been selected", Toast.LENGTH_SHORT).show();
                }
                else if(foods.size()>3){
                    Toast.makeText(getApplicationContext(), "A maximum of three food items can be selected", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Intent intent = new Intent(CreateMealActivity.this, MealResultActivity.class);
                    intent.putExtra("foods", foods);
                    startActivity(intent);
                }
            }
        });


        getLoaderManager().initLoader(FOOD_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String selection;

        if(cursorFilter != null){
            selection =  "(" + FoodContract.FoodEntry.COLUMN_FOOD_NAME + " LIKE '%" + cursorFilter + "%')";
        }
        else
        {
            selection = null;
        }

        String[] results = {
                FoodContract.FoodEntry._ID,
                FoodContract.FoodEntry.COLUMN_FOOD_NAME,
                FoodContract.FoodEntry.COLUMN_FOOD_CAL
        };

        return new android.content.CursorLoader(this,
                FoodContract.FoodEntry.CONTENT_URI,
                results,
                selection,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cAdapter.swapCursor(null);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String arg0) {
        if(!TextUtils.isEmpty(arg0))
        {
            cursorFilter = arg0;
        }
        else {
            cursorFilter = null;
        }

        getLoaderManager().restartLoader(0,null, this);
        return true;
    }



    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.add("Search");
        item.setIcon(R.drawable.ic_tick);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        SearchView searchView = new SearchView(CreateMealActivity.this);
        searchView.setOnQueryTextListener(this);
        item.setActionView(searchView);

        return true;
    }
}
