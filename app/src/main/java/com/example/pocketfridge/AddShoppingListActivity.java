package com.example.pocketfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketfridge.attributes.Category;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;
import java.util.ArrayList;

public class AddShoppingListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreateList {
    private EditText nameInput;
    private Spinner categorySpinner;
    private Spinner typeSpinner;
    private ArrayList<String> categories;
    private ArrayList<String> types;
    private Category cat;
    private String selectedType, selectedCategory, selectedName;
    private static int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);
        // Initialize objects
        cat = new Category();
        nameInput = (EditText) findViewById(R.id.nameInputSL);
        categorySpinner = (Spinner) findViewById(R.id.categoryInputSL);
        typeSpinner = (Spinner) findViewById(R.id.typeInputSL);
        createList();
    }

    @Override
    // Creates the categories
    public void createList() {
        categories = cat.getCategories();
        categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(ad);
    }

    // Creates the types when the category is selected
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = categorySpinner.getSelectedItem().toString();
        createTypes(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
    private void createTypes(int index) {
        types = cat.getTypes(index);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = typeSpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(ad);
    }
    // Adds the product to the SL
    public void onClickAddFoodActivitySL(View view) {
        selectedName = nameInput.getText().toString();
        Toast.makeText(getApplicationContext(), selectedName + " added to the shopping list", Toast.LENGTH_SHORT).show();
        Product product;
        try{
            product = new Product(selectedName,selectedCategory,selectedType,null,id++);
        }
        catch (Exception e){
            product = new Product("", "", "", null,id++);
        }
        DBHelper dbhelper = new DBHelper(AddShoppingListActivity.this);
        dbhelper.addToList(product);
    }
}