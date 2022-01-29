package com.refridgeapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements GroceryListAdapter.OnGroceryItemListener, BottomNavigationView.OnNavigationItemSelectedListener {
    private ArrayList<GroceryItem> groceryItems;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load items from DB
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        GroceryItemDao dao = db.groceryItemDao();
        groceryItems = (ArrayList<GroceryItem>) dao.getAll();

        // Setup recyclerView
        recyclerView = findViewById(R.id.groceryItemListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Mock data for now
        if (groceryItems == null || groceryItems.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.add(Calendar.DATE, 2);
            dao.insert(new GroceryItem("Eggs", calendar.getTime()));

            calendar.add(Calendar.DATE, 1);
            dao.insert(new GroceryItem("Meat", calendar.getTime()));

            groceryItems = (ArrayList<GroceryItem>) dao.getAll();
        }

        recyclerView.setAdapter(new GroceryListAdapter(groceryItems, this));
    }

    public void addGrocery(View view) {
        Intent intent = new Intent(this, AddGroceryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onGroceryItemClick(int position) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        GroceryItemDao dao = db.groceryItemDao();
        dao.delete(groceryItems.get(position));

        groceryItems = (ArrayList<GroceryItem>) dao.getAll();
        recyclerView.setAdapter(new GroceryListAdapter(groceryItems, this));
    }

    // Implements the fragments by making objects
    BottomNavigationView bottomNavigationView;


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.recipes:
                Intent intent1 = new Intent(MainActivity.this, RecipesActivity.class);
                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.account:
                Intent intent2 = new Intent(MainActivity.this, account.class);
                startActivity(intent2);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.fridge:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }

    public static class Notifications extends AppCompatActivity {


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            addNotification();

            }

        private void addNotification(){

        String textTitle = "Refridge Item Expiration";
        String textContent = "Your grocery" + "will expire in" + " ";

            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //notification message will get at NotificationView
            notificationIntent.putExtra("message", "This is a notification message");

            PendingIntent Intent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"My notification");
                builder.setSmallIcon(R.drawable.ic_launcher_recipes);
                builder.setContentTitle(textTitle);
                builder.setContentText(textContent);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                // Set the intent that will fire when the user taps the notification
                builder.setContentIntent(Intent);
                builder.setAutoCancel(true);

            builder.setContentIntent(Intent);

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel("My notification", "My notify", NotificationManager.IMPORTANCE_DEFAULT);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
    }
    }
}