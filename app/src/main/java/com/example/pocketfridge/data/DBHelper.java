package com.example.pocketfridge.data;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.pocketfridge.fridgeItems.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    Calendar cal;
    SimpleDateFormat dateFormat;
    public DBHelper(@Nullable Context context) {
        super(context, "products.db", null, 1);
        dateFormat= new SimpleDateFormat("dd/MM/yyyy", Locale.UK);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE ProductTable (Name TEXT, " +
                "IsExpirable BOOL, " +
                "ExpDate TEXT, " +
                "Category TEXT, " +
                "Type TEXT , " +
                "IsLiquid BOOL, " +
                "Quantity INTEGER) ";

        db.execSQL(createTable);

        String createListDB = "CREATE TABLE Shopping_List (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "Name TEXT, " +
                "Category TEXT, " +
                "Type TEXT, "+
                "addingDate TEXT, " +
                "isBought INTEGER) " ;

        db.execSQL(createListDB);
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
        cv.put("Category", product.getCategory());
        cv.put("Type", product.getType());
        cv.put("isLiquid", product.isLiquid());
        cv.put("Quantity", product.getQuantity());

        long insert = db.insert("ProductTable", null, cv);
        if(insert < 0) return false;
        else return true;
    }

    public boolean addToList(Product product){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", product.getName());
        cv.put("Category", product.getCategory());
        cv.put("Type", product.getType());
        String strDate= product.getExpDate();
        cv.put("addingDate", strDate);
        cv.put("isBought" , 0);

        long insert = db.insert("Shopping_List", null, cv);
        if(insert < 0) return false;
        else return true;
    }

    public ArrayList<Product> getAll_Fridge() {
    ArrayList<Product> items = new ArrayList<>();
    String queryStr = "SELECT * FROM ProductTable";
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery(queryStr,null);

    if (cursor.moveToFirst()) {

        do {
            String productName = cursor.getString(0);
            boolean productExpirable = cursor.getInt(1) == 1 ? true : false;
            String productExpDate = cursor.getString(2);
            String productCategory = cursor.getString(3);
            String productType = cursor.getString(4);
            boolean productLiquid = cursor.getInt(5) == 1 ? true : false;
            Product pro;
            try{
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(productExpDate.substring(6,10)));
                cal.set(Calendar.MONTH,Integer.parseInt(productExpDate.substring(3,5))-1);
                cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(productExpDate.substring(0,2)));
                pro = new Product(productName, productCategory, productType,cal);
            }
            catch (NumberFormatException e) {
                pro = new Product(productName, productCategory, productType,null);
            }
            items.add(pro);

        }
        while(cursor.moveToNext());
    }
    else{
        //list empty or ended
    }
    cursor.close();
    db.close();
    return items;
    }

    public ArrayList<Product> getAll_ShoppingList() {
        ArrayList<Product> shopList = new ArrayList<>();
        String queryStr = "SELECT * FROM Shopping_List";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr,null);

        if (cursor.moveToFirst()) {

            do {
                String productName = cursor.getString(0);
                String productCategory = cursor.getString(1);
                String productType = cursor.getString(2);


                Product pro = new Product(productName, productCategory, productType,null);

                shopList.add(pro);

            }
            while(cursor.moveToNext());
        }
        else{
            //list empty or ended
        }
        cursor.close();
        db.close();
        return shopList;
    }
    public void deleteProduct(int id, String tableName) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName+ " WHERE "+"id"+"='"+id+"'");
        db.close();
    }
}