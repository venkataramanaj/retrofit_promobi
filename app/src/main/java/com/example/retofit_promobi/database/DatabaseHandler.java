package com.example.retofit_promobi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.retofit_promobi.pojo.Data;
import com.example.retofit_promobi.pojo.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramana on 5/21/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "promobi";
    private static final String TABLE_DATA = "data";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_IMAGE = "image";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_IMAGE + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public void insertData(List<Result> result) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=null;
        for (int i = 0; i < result.size(); i++) {
            values= new ContentValues();

            values.put(KEY_TITLE, result.get(i).getTitle()); // Contact Name
            if (result.get(i).getMultimedia().size() > 0)
                values.put(KEY_IMAGE, result.get(i).getMultimedia().get(4).getUrl()); // Contact Phone
            else
                values.put(KEY_IMAGE, "");

            db.insert(TABLE_DATA, null, values);
        }
        // Inserting Row


        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }


    // code to get all contacts in a list view
    public List<Data> getAllData() {
        List<Data> contactList = new ArrayList<Data>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Data contact = new Data();
//                contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setTitle(cursor.getString(1));
                contact.setImage(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }

}
