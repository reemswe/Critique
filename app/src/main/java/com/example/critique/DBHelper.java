package com.example.critique;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instance = null;

    private static final String DatabaseName = "Users.db";
    private static final String TableName = "users";
//    private static final String col1 = "ID";
    private static final String col2 = "username";
    private static final String col3 = "password";
    private static final String col4 = "type";

    public static DBHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DBHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    private DBHelper(Context context) {
        super(context, DatabaseName, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TableName + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT, TYPE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String username, String password, String type){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(col2, username);
        cv.put(col3, password);
        cv.put(col4, type);
        long result = db.insert(TableName, null, cv);

        return (result != -1);
    }

    public boolean validateInput(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] conditionArgs = {username, password};
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", conditionArgs);

        boolean valid = cursor.getCount() > 0;
        cursor.close();

        return valid;

    }
}
