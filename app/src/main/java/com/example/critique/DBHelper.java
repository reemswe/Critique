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

    private static final String storesTable = "storesTable";//table for retailer's stores
    private static final String colRetailerID = "retailerID";//FK
    private static final String colStoresName = "storeName";
    //type?? cafe ..

    private static final String reviewsTable = "reviewsTable";//table for store's reviews
    private static final String colStoreID = "storeID";//FK
    private static final String colReviews = "review";
    private static final String colReviewsRating = "rating";//out of 5?

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
        sqLiteDatabase.execSQL("CREATE TABLE storesTable (SID INTEGER PRIMARY KEY AUTOINCREMENT, RETAILERID INTEGER, STORENAME TEXT, FOREIGN KEY (RETAILERID) REFERENCES USERS (ID));");
        sqLiteDatabase.execSQL("CREATE TABLE "+ reviewsTable + "(RID INTEGER PRIMARY KEY AUTOINCREMENT, STOREID INTEGER, REVIEW TEXT, RATING INTEGER, FOREIGN KEY (STOREID) REFERENCES STORETABLE(SID));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TableName);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + storesTable);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + reviewsTable);
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

    public boolean insertDataIntoStoresTable(int retielerID , String storeName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colRetailerID,retielerID);
        contentValues.put(colStoresName,storeName);
        long result = db.insert(storesTable,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataIntoReviewsTable(int storeID , String review , int rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(colStoreID,storeID);
        contentValues.put(colReviews,review);
        contentValues.put(colReviewsRating,rating);
        long result = db.insert(reviewsTable,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean validateInput(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] conditionArgs = {username, password};
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", conditionArgs);

        boolean valid = cursor.getCount() > 0;
        cursor.close();

        return valid;

    }

    //return retailer id or 0
    public int validateInput2(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] conditionArgs = {username, password};
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ? AND password = ?", conditionArgs);
        int id;
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            id = cursor.getInt(0);
        }
        else
            id = 0;
        cursor.close();

        return id;
    }


    public Cursor getUserName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] conditionArgs = {Integer.toString(id)};
        Cursor cursor = db.rawQuery("select username from users where id = ?",conditionArgs);
        return cursor;
    }

    public Cursor getStores (int retailerID){
        SQLiteDatabase db = this.getWritableDatabase();//close in ondestroy()//change xxx.this
        String[] conditionArgs = {Integer.toString(retailerID)};
        Cursor c = db.rawQuery("select * from "+storesTable+" where retailerid=?",conditionArgs);
        return c;
    }

    public Cursor getReviews(int storeID){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] conditionArgs = {Integer.toString(storeID)};
        Cursor c = db.rawQuery("select * from "+reviewsTable+" where STOREID=?",conditionArgs);
        return c;
    }
}
