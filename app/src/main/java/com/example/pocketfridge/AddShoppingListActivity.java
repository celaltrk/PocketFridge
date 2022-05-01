package com.example.pocketfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;
import java.util.Calendar;

public class AddShoppingListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nameInput;
    Spinner categorySpinner;
    Spinner typeSpinner;
    ArrayList<String> categories;
    ArrayList<String> types;
    String selectedType, selectedCategory, selectedName;
    String dateStr;
    Calendar cal = Calendar.getInstance();
    final static String dmy = AddFoodActivity.dmy;
    final static String[][] table = AddFoodActivity.table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shopping_list);
        nameInput = (EditText) findViewById(R.id.nameInputSL);
        categorySpinner = (Spinner) findViewById(R.id.categoryInputSL);
        typeSpinner = (Spinner) findViewById(R.id.typeInputSL);
        createCategories();
    }
    private void createCategories() {
        categories = new ArrayList<String>();
        for (int i = 0; i < table.length; i++)
            categories.add(table[i][0]);
        categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(ad);
    }

    private void createTypes(int index) {
        types = new ArrayList<String>();
        for (int j = 1; j < table[index].length; j++)
            types.add(table[index][j]);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedType = typeSpinner.getSelectedItem().toString();
                Toast.makeText(getApplicationContext(), selectedType, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(ad);
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = categorySpinner.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(), selectedCategory, Toast.LENGTH_SHORT).show();
        createTypes(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void onClickAddFoodActivitySL(View view) {
        selectedName = nameInput.getText().toString();
        Toast.makeText(getApplicationContext(), selectedName + " added", Toast.LENGTH_SHORT).show();
    }
}