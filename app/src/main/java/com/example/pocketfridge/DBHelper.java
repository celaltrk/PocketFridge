package com.example.pocketfridge;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.pocketfridge.fridgeItems.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(@Nullable Context context) {
        super(context, "products.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE ProductTable (Name TEXT, " +
                "IsExpirable BOOL, " +
                "ExpDate TEXT, " +
                "Type TEXT , " +
                "IsLiquid BOOL, " +
                "Quantity INTEGER)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean addOne(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", product.getName());
        cv.put("IsExpirable", product.isExpirable());
        cv.put("ExpDate", product.getExpDate());
        cv.put("Type", product.getType());
        cv.put("isLiquid", product.isLiquid());
        cv.put("Quantity", product.getQuantity());

       long insert = db.insert("ProductTable", null, cv);
        if(insert < 0) return false;
        else return true;
    }

    public List<Product> getAll(){

    List<Product> items = new ArrayList<>();

    String queryStr = "SELECT * FROM ProductTable";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(queryStr,null);

    if (cursor.moveToFirst()){

        do{

            String productName = cursor.getString(0);
            boolean productExpirable = cursor.getInt(1) == 1 ? true : false;

        }while(cursor.moveToFirst());
    }
    else{

    }
    return items;
    }
}