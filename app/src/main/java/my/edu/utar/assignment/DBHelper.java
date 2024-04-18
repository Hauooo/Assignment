package my.edu.utar.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Login.db";
    public static final String TABLE_NAME = "users";
    //public static final String UID = "id";
    public static final String COL_1 = "username";
    public static final String COL_2 = "password";
    private static final String SCRIPT_CREATE_DATABASE = "create Table " + TABLE_NAME + " (" + COL_1 + " TEXT PRIMARY KEY, "+ COL_2 + " TEXT);";
    //---------------------------------------------------
    public static final String TABLE_NAME2 = "history";
    public static final String HID = "id";
    public static final String Before = "original";
    public static final String After = "translated";
    private static final String SCRIPT_CREATE_DATABASE2 = "create Table " + TABLE_NAME2 + " (" + HID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Before + " TEXT, " + After + " TEXT, " + "user_name INTEGER, FOREIGN KEY('user_name') REFERENCES " + TABLE_NAME + "(" + COL_1 + "));";
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_DATABASE);
        db.execSQL(SCRIPT_CREATE_DATABASE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + TABLE_NAME );
        db.execSQL("drop Table if exists " + TABLE_NAME2 );
        onCreate(db);
    }

    public Boolean insertUser(String username, String password){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1, username);
        contentValues.put(COL_2, password);
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean insertHistory(String original, String translated, String username){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Before, original);
        contentValues.put(After, translated);
        contentValues.put("user_name", username);
        long result = sqLiteDatabase.insert(TABLE_NAME2, null, contentValues);
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, COL_1 +" = ?", new String[]{username}, null, null, null);
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, COL_1 + " = ? AND " +COL_2 + " = ?", new String[]{username, password}, null, null, null);
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Cursor readAllData(){
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.query(TABLE_NAME2, null, null, null, null, null, null);
        }
        return cursor;
    }

    public Boolean updateData(String row_id, String original, String translated){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(Before, original);
        contentValues.put(After, translated);

        long result = sqLiteDatabase.update(TABLE_NAME2, contentValues, "id=?", new String[]{row_id});
        if(result == -1) return false;
        else
            return true;
    }

    public Boolean deleteOneRow(String row_id){
        sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME2, "id=?", new String[]{row_id});
        if(result == -1) return false;
        else
            return true;
    }

    public void deleteAllData(){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME2);
    }


    public void storeDataInArrays(ArrayList<String> id, ArrayList<String> original, ArrayList<String> translated){
        Cursor cursor = readAllData();

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()){
                id.add(cursor.getString(0));
                original.add(cursor.getString(1));
                translated.add(cursor.getString(2));
            }
            cursor.close();
        } else {
            Toast.makeText(context, "No data available", Toast.LENGTH_SHORT).show();
        }
    }
}