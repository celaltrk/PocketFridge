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
import com.example.pocketfridge.attributes.Category;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFoodActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CreateList {
    private EditText nameInput;
    private Spinner categorySpinner;
    private Spinner typeSpinner;
    private ArrayList<String> categories;
    private ArrayList<String> types;
    private Category cat;
    private EditText expDateEditText;
    private String selectedType;
    private String selectedCategory;
    private Calendar cal;
    private static int id = 0;
    final static String dmy = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        // Initialize objects
        cat = new Category();
        cal = Calendar.getInstance();
        nameInput = (EditText) findViewById(R.id.nameInput);
        categorySpinner = (Spinner) findViewById(R.id.categoryInput);
        typeSpinner = (Spinner) findViewById(R.id.typeInput);
        getExtras();
        datePick();
    }
    @Override
    public void createList() {
        categories = cat.getCategories();
        categorySpinner.setOnItemSelectedListener(this);
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(ad);
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
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedCategory = categorySpinner.getSelectedItem().toString();
        if (getIntent().getStringExtra("category") == null) {
            createTypes(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // do nothing
    }
    // Picks the date
    private void datePick() {
        expDateEditText = (EditText) findViewById(R.id.expDateEditText);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH,month);
                cal.set(Calendar.DAY_OF_MONTH,day);
                SimpleDateFormat dateFormat = new SimpleDateFormat(dmy, Locale.UK);
                expDateEditText.setText(dateFormat.format(cal.getTime()));
            }
        };
        expDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(AddFoodActivity.this,date,cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    // Adds the product
    public void onClick(View view) {
        String selectedName = nameInput.getText().toString();
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

    // Checks whether the activity is opened from auto-add and gets extras is that case
    public void getExtras() {
        Intent i = getIntent();
        // If the activity is opened from fridge add button
        if (i.getStringExtra("category") == null) {
            createList();
        }
        // If the activity is opened by auto-add
        else {
            nameInput.setText(i.getStringExtra("name"));
            selectedCategory = i.getStringExtra("category");
            selectedType = i.getStringExtra("type");
            categories = new ArrayList<>();
            categories.add(selectedCategory);
            types = new ArrayList<>();
            types.add(selectedType);
            ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            categorySpinner.setAdapter(ad);
            ArrayAdapter ad2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, types);
            ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(ad2);
        }
    }

}