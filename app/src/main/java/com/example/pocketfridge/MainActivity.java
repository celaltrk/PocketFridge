package com.example.pocketfridge;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Notification;
import com.example.pocketfridge.fridgeItems.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhelper = new DBHelper(MainActivity.this);

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
        addManuallyButton = (FloatingActionButton) findViewById(R.id.addProductSL);
        autoAddSwitchOn = dbhelper.getSetting("AutoAdd");
        vibrationOn = dbhelper.getSetting("Vibration");
        notificationOn = dbhelper.getSetting("Notifications");
        setContentView(binding.getRoot());
    }
    public void addManuallyOnClick(View view) {
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }
    public void addSLOnClick(View view) {
        Intent intent = new Intent(this, AddShoppingListActivity.class);
        startActivity(intent);
    }
    public void resetFridge(View view) {
        DBHelper helper = new DBHelper(this);
        for (Product pro : helper.getAll_Fridge()) {
            helper.deleteProduct(pro.getId(),"ProductTable");
        }
        Toast.makeText(getApplicationContext(), "Fridge has been emptied", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && vibrationOn) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.EFFECT_HEAVY_CLICK));
        }


    }public void resetSL(View view) {
        DBHelper helper = new DBHelper(this);
        for (Product pro : helper.getAll_ShoppingList()) {
            helper.deleteProduct(pro.getId(),"Shopping_List");
        }
        Toast.makeText(getApplicationContext(), "Shopping list has been emptied", Toast.LENGTH_SHORT).show();
        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && vibrationOn) {
            v.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.EFFECT_HEAVY_CLICK));
        }
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