package fyp.nuigalway.ie.bodycompostionassistance.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;
/**
 * Created by fergalbroderick on 24/01/2017.
 */

public final class FoodContract {

    private FoodContract(){

    }


    public static final String CONTENT_AUTHORITY = "fyp.nuigalway.ie.bodycompostionassistance";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FOODS= "foods";


    public static final class FoodEntry implements BaseColumns
    {


        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_FOODS);


        public static final String LIST_CONTENT =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FOODS;
        public static final String ITEM_CONTENT =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FOODS;

        public final static String TABLE_NAME = "foods";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FOOD_NAME = "name";
        public final static String COLUMN_FOOD_CAL = "calories";
        public final static String COLUMN_FOOD_CARB = "carbohydrate";
        public final static String COLUMN_FOOD_FAT = "fat";
        public final static String COLUMN_FOOD_PROT = "protein";
        public final static String COLUMN_FOOD_DESC = "description";

    }
}
