package me.root4.whereami;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by harish on 4/2/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context){
        super(context, "Locations", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBHANDLER", "Inside on create");
        String createScript = "CREATE TABLE Locations (Id INTEGER PRIMARY KEY, lat REAL, lng REAL , captureDate DATETIME, createDate DATETIME, synced NUMERIC )";
        db.execSQL(createScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBHANDLER", "Inside upgrade");
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
        values.put("createDate", new Date().getTime());
        values.put("synced", false);

        long Id = db.insert("Locations", null, values);
        db.close();

        Log.d("DBINSERT", "Inserted : " + Id);
    }


    public Location getLocation(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("Locations", new String[]{"id",
                        "lat", "lng", "captureDate", "createDate", "synced"}, "Id=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Log.d("DBQUERY", cursor.getString(1));
        /*
        Location loc = new Location(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return loc;
        */
        return null;
    }

    public List<Location> getAllLocations(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * from Locations", null);

        Location loc;
        List<Location> locations = new ArrayList<Location>();

        if (cursor.moveToFirst())
        {
            while (!(cursor.isAfterLast()))
            {
                loc = new Location();
                loc.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
                loc.setLng(cursor.getDouble(cursor.getColumnIndex("lng")));
                loc.setCaptureDate(cursor.getLong(cursor.getColumnIndex("captureDate")));
                loc.setCreateDate(cursor.getLong(cursor.getColumnIndex("createDate")));

                locations.add(loc);

                Log.d("SELECTALL", String.valueOf(loc.getLat()));
                cursor.moveToNext();
            }
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            db.close();
        }

        return locations;
    }

    private String formatedDate(Date date) {
        return (String) DateFormat.format("MM/dd/yyyy hh:mm:ss", date);
    }

}
