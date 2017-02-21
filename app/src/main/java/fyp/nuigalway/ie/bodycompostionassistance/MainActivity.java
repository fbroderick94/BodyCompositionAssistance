package fyp.nuigalway.ie.bodycompostionassistance;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodHelper;
import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.FoodEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int FOOD_LOADER = 0;

    FoodAdapter cAdapter;


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


        ListView displayView = (ListView) findViewById(R.id.text_view_food);

        View emptyView = findViewById(R.id.empty_view);
        displayView.setEmptyView(emptyView);


        cAdapter = new FoodAdapter(this, null);
        displayView.setAdapter(cAdapter);


        displayView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View itemView, int positon, long id) {

                Intent intent = new Intent(MainActivity.this, EditActivity.class);


                Uri currentUri = ContentUris.withAppendedId(FoodEntry.CONTENT_URI, id);
                intent.setData(currentUri);
                startActivity(intent);

            }
        });

        getLoaderManager().initLoader(FOOD_LOADER, null, this);


    }

    protected void onStart()
    {
        super.onStart();

    }



    @Override
    public android.content.Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] results = {
                FoodEntry._ID,
                FoodEntry.COLUMN_FOOD_NAME,
                FoodEntry.COLUMN_FOOD_CAL
        };

        return new android.content.CursorLoader(this,
                FoodEntry.CONTENT_URI,
                results,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {

        cAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {

        cAdapter.swapCursor(null);
    }
}
