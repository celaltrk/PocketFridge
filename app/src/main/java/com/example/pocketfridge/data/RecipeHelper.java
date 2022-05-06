package com.example.pocketfridge.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.fridgeItems.Recipe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class RecipeHelper extends SQLiteOpenHelper {
    private static String DBPath;
    private static String DBName = "recipes.db";
    private Context mContext;
    private SQLiteDatabase myDataBase;

    public RecipeHelper(Context context){
        super(context, DBName,null, 1);
        DBPath = context.getApplicationInfo().dataDir+"/databases/";
        mContext = context;

    }


    private boolean checkDB(){
        SQLiteDatabase tempDB = null;
        try{
            String path = DBPath + DBName;
            tempDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (Exception e){

        }
        if(tempDB != null){
            tempDB.close();
        }
        return tempDB != null ? true: false;
    }
    private void copyDataBase() throws IOException {

        InputStream myInput = mContext.getAssets().open(DBName);

        String outFileName = DBPath + DBName;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        String myPath = DBPath + DBName;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    public void createDB(){
        boolean doesDBExist = checkDB();
        if(doesDBExist){

        }
        else{
            this.getReadableDatabase();
            try{
                copyDataBase();
            }
            catch (Exception e){

            }
        }
    }
    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipes = new ArrayList<>();
        String queryStr = "SELECT * FROM tbl_recipe ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryStr,null);

        if (cursor.moveToFirst()) {

            do {
                String name = cursor.getString(0);
                String recipeIngredients = cursor.getString(1) ;
                String instructions = cursor.getString(2);

                Recipe recipe;
                try{
                    recipe = new Recipe(name,instructions,recipeIngredients);
                }
                catch (NumberFormatException e) {
                    recipe = new Recipe("no data","no data", "no data");
                }
                recipes.add(recipe);

            }
            while(cursor.moveToNext());
        }
        else{
            //list empty or ended
        }
        cursor.close();
        db.close();
        return recipes;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
