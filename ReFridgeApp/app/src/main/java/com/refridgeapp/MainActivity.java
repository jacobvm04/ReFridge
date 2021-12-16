package com.refridgeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_GROCERY_DATA = "com.refridgeapp.GROCERY_DATA";

    // Grocery items are currently represented as a pair of the grocery name and it's respective expiration date
    private ArrayList<GroceryItem> groceryItems;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Process new grocery if an intent exists
        Pair<String, Date> item = null;

        Intent intent = getIntent();
        groceryItems = (ArrayList<GroceryItem>) intent.getSerializableExtra(EXTRA_GROCERY_DATA);

        // Setup recyclerView
        recyclerView = findViewById(R.id.gorceryItemListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        // Mock data for now
        if (groceryItems == null || groceryItems.isEmpty()) {
            groceryItems = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            calendar.add(Calendar.DATE, 2);
            groceryItems.add(new GroceryItem("Eggs", calendar.getTime()));

            calendar.add(Calendar.DATE, 1);
            groceryItems.add(new GroceryItem("Meat", calendar.getTime()));
        }

        recyclerView.setAdapter(new GroceryListAdapter(groceryItems));
    }

    public void addGrocery(View view) {
        Intent intent = new Intent(this, AddGroceryActivity.class);
        intent.putExtra(EXTRA_GROCERY_DATA, groceryItems);
        startActivity(intent);
    }
}