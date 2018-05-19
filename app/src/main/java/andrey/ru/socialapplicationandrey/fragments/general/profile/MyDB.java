package andrey.ru.socialapplicationandrey.fragments.general.profile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by User on 6/3/2017.
 */
public class MyDB {

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    public final static String THE_TABLE = "Persons"; // name of table

    public final static String _ID = "_id"; // id value for employee
    public final static String _NAME = "name";  // name of employee

    /**
     * @param context
     */
    public MyDB(Context context) {
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(String id, String name) {
        ContentValues values = new ContentValues();
        values.put(_ID, id);
        values.put(_NAME, name);
        return database.insert(THE_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[]{_ID, _NAME};
        Cursor mCursor = database.query(true, THE_TABLE, cols, null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }
}
