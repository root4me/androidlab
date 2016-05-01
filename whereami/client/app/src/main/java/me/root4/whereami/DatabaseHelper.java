package me.root4.whereami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by harish on 4/2/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DATABASE_HELPER";

    public DatabaseHelper(Context context){
        super(context, "Locations", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Inside on create");
        String createScript = "CREATE TABLE Locations (Id INTEGER PRIMARY KEY, lat REAL, lng REAL , captureDate DATETIME, synced NUMERIC )";
        db.execSQL(createScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "Inside upgrade");
        db.execSQL("DROP TABLE IF EXISTS Locations");
        onCreate(db);
    }

    // Adding new contact
    public void addLocation(Location location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("lat", location.getLat());
        values.put("lng", location.getLng());
        values.put("captureDate", location.getCaptureDate());
        values.put("synced", false);

        long Id = db.insert("Locations", null, values);
        db.close();

        Log.d("DBINSERT", "Inserted : " + Id);
    }


    public Location getLocationById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Locations", new String[]{"id",
                        "lat", "lng", "captureDate", "synced"}, "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return null;
    }

    public int setSyncFlag(int id, boolean flag) {

        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues args = new ContentValues();
        args.put("synced", flag);

        int retval = db.update("Locations",args,"Id=?", new String[]{String.valueOf(id)});
        db.close();

        return retval;
    }

    public List<Location> getAllLocations(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Locations", null);

        List<Location> locations = new ArrayList<Location>();

        locations = locationList(cursor);

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            db.close();
        }

        return locations;
    }

    public List<Location> getToBeSynced(){

        Log.d("getToBeSynced", "inside getToBeSynced");

        SQLiteDatabase db = this.getReadableDatabase();

        // get there synced = false
        Cursor cursor = db.query("Locations", new String[]{"id",
                "lat", "lng", "captureDate", "synced"}, "synced=?", new String[]{"0"}, null, null, null, null);

        List<Location> locations = new ArrayList<Location>();

        locations = locationList(cursor);

        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            db.close();
        }

        return locations;
    }

    public void deleteAllLocations()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Locations",null,null);
    }

    public void deleteById(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Locations", "id=" + id, null);
    }

    private String formatedDate(Date date) {
        return (String) DateFormat.format("MM/dd/yyyy hh:mm:ss", date);
    }

    private List<Location> locationList(Cursor cursor){

        Location loc;
        List<Location> locations = new ArrayList<Location>();

        if (cursor.moveToFirst())
        {
            while (!(cursor.isAfterLast()))
            {
                loc = new Location();
                loc.setId(cursor.getInt(cursor.getColumnIndex("Id")));
                loc.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
                loc.setLng(cursor.getDouble(cursor.getColumnIndex("lng")));
                loc.setCaptureDate(cursor.getLong(cursor.getColumnIndex("captureDate")));
                loc.setSynced(cursor.getInt(cursor.getColumnIndex("synced")));

                locations.add(loc);

                cursor.moveToNext();

                Log.e(TAG, String.valueOf(loc.getId()));
                Log.e(TAG,String.valueOf(DateFormat.format("MM/dd/yyyy hh:mm:ss", loc.getCaptureDate())));

            }
        }

        return locations;
    }

}
