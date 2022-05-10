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
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "IsExpirable BOOL, " +
                "ExpDate TEXT, " +
                "Category TEXT, " +
                "Type TEXT , " +
                "IsClosetoExpire BOOL, " +
                "Quantity INTEGER) ";

        db.execSQL(createTable);

        String createSettingsTable = "CREATE TABLE SettingTable" +
                " (Notifications INTEGER, " +
                "Vibration INTEGER, " +
                "AutoAdd INTEGER) ";

        db.execSQL(createSettingsTable);

        String createListDB = "CREATE TABLE Shopping_List (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "Name TEXT, " +
                "Category TEXT, " +
                "Type TEXT, "+
                "addingDate TEXT, " +
                "isBought INTEGER) " ;

        db.execSQL(createListDB);

        String setSettings = "INSERT INTO SettingTable (Notifications,Vibration,AutoAdd) VALUES (1,1,1)";
        db.execSQL(setSettings);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS ShoppingList");
        db.execSQL("DROP TABLE IF EXISTS ProductTable");
        onCreate(db);
    }

    public boolean addOne(Product product) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Name", product.getName());
        cv.put("IsExpirable", product.isExpirable());
        cv.put("ExpDate", product.getExpDate());
        cv.put("Category", product.getCategory());
        cv.put("Type", product.getType());
        cv.put("IsClosetoExpire", 0);
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
            int productId = cursor.getInt(1);
            boolean productExpirable = cursor.getInt(2) == 1 ? true : false;
            String productExpDate = cursor.getString(3);
            String productCategory = cursor.getString(4);
            String productType = cursor.getString(5);
            boolean productLiquid = cursor.getInt(6) == 1 ? true : false;
            Product pro;
            try{
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, Integer.parseInt(productExpDate.substring(6,10)));
                cal.set(Calendar.MONTH,Integer.parseInt(productExpDate.substring(3,5))-1);
                cal.set(Calendar.DAY_OF_MONTH,Integer.parseInt(productExpDate.substring(0,2)));
                pro = new Product(productName, productCategory, productType,cal,productId);
            }
            catch (NumberFormatException e) {
                pro = new Product(productName, productCategory, productType,null,productId);
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
                int productId = cursor.getInt(0);
                String productName = cursor.getString(1);
                String productCategory = cursor.getString(2);
                String productType = cursor.getString(3);
                Product pro = new Product(productName, productCategory, productType,null,productId);
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

    public boolean getSetting(String settingName){
        String queryStr = "SELECT " + settingName +" FROM SettingTable";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr,null);
        int wanted = 0;

        if (cursor.moveToFirst()) {

            do {
            wanted = cursor.getInt(0);

            }
            while(cursor.moveToNext());
        }
        else{
            //list empty or ended
        }
        cursor.close();
        db.close();
        return wanted == 1;
    }
    public boolean deleteProduct(int id, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + tableName + " WHERE id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }
    public boolean markAsClose(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE ProductTable SET IsClosetoExpire = 1 WHERE " +
                "id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }

    public boolean editSetting(String settingName, boolean b){
        int i = (b == true) ? 1 : 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE SettingTable SET " + settingName+ " ="+ i ; // possible mistake
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) return true;
        else return false;
    }

    public ArrayList<Product> getClosetoExpire(){
        ArrayList<Product> products = new ArrayList<>();
        String queryStr = "SELECT * FROM ProductTable WHERE IsClosetoExpire = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr,null);

        if (cursor.moveToFirst()) {

            do {
                String productName = cursor.getString(0);
                int productId = cursor.getInt(1);
                String productExpDate = cursor.getString(3);
                String productCategory = cursor.getString(4);
                String productType = cursor.getString(5);
                Product pro;
                try {
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.YEAR, Integer.parseInt(productExpDate.substring(6, 10)));
                    cal.set(Calendar.MONTH, Integer.parseInt(productExpDate.substring(3, 5)) - 1);
                    cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(productExpDate.substring(0, 2)));
                    pro = new Product(productName, productCategory, productType, cal, productId);
                } catch (NumberFormatException e) {
                    pro = new Product(productName, productCategory, productType, null, productId);
                }
                products.add(pro);
            }
            while(cursor.moveToNext());
        }
        else{
            //list empty or ended
        }
        cursor.close();
        db.close();
        return products;
    }

}