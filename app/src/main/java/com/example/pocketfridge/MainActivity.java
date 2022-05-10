package com.example.pocketfridge;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pocketfridge.attributes.Vibration;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.pocketfridge.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addManuallyButton;
    private ActivityMainBinding binding;
    private DBHelper dbhelper;
    private boolean autoAddSwitchOn;
    private boolean vibrationOn;
    private boolean notificationOn;
    private Vibration v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_fridge, R.id.navigation_zerowastage, R.id.navigation_shoppinglist, R.id.navigation_settings, R.id.navigation_allrecipes)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Initialize objects
        dbhelper = new DBHelper(MainActivity.this);
        v = new Vibration(this,500);
        addManuallyButton = (FloatingActionButton) findViewById(R.id.addProductSL);
        autoAddSwitchOn = dbhelper.getSetting("AutoAdd");
        vibrationOn = dbhelper.getSetting("Vibration");
        notificationOn = dbhelper.getSetting("Notifications");
        setContentView(binding.getRoot());
    }
    public void addFROnClick(View view) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }
    public void addSLOnClick(View view) {
        Intent intent = new Intent(this, AddShoppingListActivity.class);
        startActivity(intent);
    }
    public void resetFridge(View view) {
        for (Product pro : dbhelper.getAll_Fridge()) {
            dbhelper.deleteProduct(pro.getId(),"ProductTable");
        }
        Toast.makeText(getApplicationContext(), "Fridge has been emptied", Toast.LENGTH_SHORT).show();
        v.vibrate();
    }
    public void resetSL(View view) {
        for (Product pro : dbhelper.getAll_ShoppingList()) {
            dbhelper.deleteProduct(pro.getId(),"Shopping_List");
        }
        Toast.makeText(getApplicationContext(), "Shopping list has been emptied", Toast.LENGTH_SHORT).show();
        v.vibrate();
    }

    public boolean isAutoAddSwitchOn() {
        return autoAddSwitchOn;
    }
    public boolean isVibrationOn() {return vibrationOn;}
    public boolean isNotificationOn() {return notificationOn;}

    public void onAutoAddChanged(View view) {
        autoAddSwitchOn = ((Switch)findViewById(R.id.sw)).isChecked();
        dbhelper.editSetting("AutoAdd", autoAddSwitchOn);
    }
    public void onVibrationChanged(View view) {
        vibrationOn = ((Switch)findViewById(R.id.sw2)).isChecked();
        dbhelper.editSetting("Vibration", vibrationOn);
    }
    public void onNotificationChanged(View view) {
        notificationOn = ((Switch)findViewById(R.id.sw3)).isChecked();
        dbhelper.editSetting("Notifications", notificationOn);
    }
    public void autoAdd(Product p) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        intent.putExtra("name",p.getName());
        intent.putExtra("category",p.getCategory());
        intent.putExtra("type",p.getType());
        startActivity(intent);
    }

}