package com.example.pocketfridge;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText nameInput;
    Spinner categorySpinner;
    Spinner typeSpinner;
    ArrayList<String> categories;
    ArrayList<String> types;
    EditText expDateEditText;
    String selectedType, selectedCategory, selectedName;
    String dateStr;
    Calendar cal = Calendar.getInstance();
    static int id = 0;
    final static String dmy = "dd/MM/yyyy";
    final static String[][] table = {
            {"No category","No type"},
            {"1","1a","1b","1c","1d"},
            {"2","2a","2b","2c","2d"},
            {"3","3a","3b","3c","3d"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        nameInput = (EditText) findViewById(R.id.nameInput);
        categorySpinner = (Spinner) findViewById(R.id.categoryInput);
        typeSpinner = (Spinner) findViewById(R.id.typeInput);
        createCategories();
        datePick();
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
        createTypes(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    private void datePick() {
        expDateEditText= (EditText) findViewById(R.id.expDateEditText);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH,month);
                cal.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        expDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddFoodActivity.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        SimpleDateFormat dateFormat=new SimpleDateFormat(dmy, Locale.UK);
        expDateEditText.setText(dateFormat.format(cal.getTime()));
        dateStr = dateFormat.format(cal.getTime());
    }
    public void onClick(View view) {
        selectedName = nameInput.getText().toString();
        Toast.makeText(getApplicationContext(), "Product added: " + selectedName, Toast.LENGTH_SHORT).show();
        Product product;
        try{
            product = new Product(selectedName,selectedCategory,selectedType,cal,id++);
        }
        catch (Exception e){
            product = new Product("", "", "", null,id++);
        }
        DBHelper dbhelper = new DBHelper(AddFoodActivity.this);
        dbhelper.addOne(product);
    }

}