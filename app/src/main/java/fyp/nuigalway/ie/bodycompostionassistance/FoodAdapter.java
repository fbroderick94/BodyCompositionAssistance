package fyp.nuigalway.ie.bodycompostionassistance;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.CursorAdapter;
import android.widget.TextView;

import fyp.nuigalway.ie.bodycompostionassistance.data.FoodContract.FoodEntry;

/**
 * Created by fergalbroderick on 21/02/2017.
 */

public class FoodAdapter extends CursorAdapter {


   public FoodAdapter(Context context, Cursor cursor)
   {
       super(context, cursor, 0);
   }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_food, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView tvName = (TextView) view.findViewById(R.id.name);
        TextView tvCals = (TextView) view.findViewById(R.id.calories);


        //Extracting the required attributes from the cursor
        String name = cursor.getString(cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME));
        int cals = cursor.getInt(cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_CAL));


        //Populating the text views with the relevant info
        tvName.setText(name);
        tvCals.setText(Integer.toString(cals) + " cals per 100g");
    }
}
