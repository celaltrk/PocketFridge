package com.example.pocketfridge;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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

        //One time work to check expired products
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            worker();
        }

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


        //Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Notification","Channel", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        notification();
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
        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(OneTimeWorker.class)
                .setConstraints(constraints)
                .setInitialDelay(10,TimeUnit.SECONDS)
                .addTag("Periodic Work")
                .build();
        WorkManager.getInstance(this).enqueue(oneTimeWorkRequest);

    }

    public void notification(){
        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this,"Notification");
        notification.setContentTitle("Title");
        notification.setContentText("Works");
        notification.setSmallIcon(R.drawable.ic_launcher_background);
        notification.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1,notification.build());
    }

}