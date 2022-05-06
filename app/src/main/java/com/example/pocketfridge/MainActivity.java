package com.example.pocketfridge;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.pocketfridge.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addManuallyButton;
    private ActivityMainBinding binding;

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
        addManuallyButton = (FloatingActionButton) findViewById(R.id.addProductSL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            worker();
        }
        //Creating Expiration object to check on fridge products everytime app is opened

    }
    public void addManuallyOnClick(View view) {
        Toast.makeText(getApplicationContext(), "Add Food Manually", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddFoodActivity.class);
        startActivity(intent);
    }
    public void addSLOnClick(View view) {
        Toast.makeText(getApplicationContext(), "Add Shopping List", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddShoppingListActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void worker(){
        Constraints constraints = new Constraints.Builder()
                .build();
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(SampleWorler.class)
                .setConstraints(constraints)
                .setInitialDelay(10,TimeUnit.SECONDS)
                .addTag("Periodic Work")
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);

    }

}