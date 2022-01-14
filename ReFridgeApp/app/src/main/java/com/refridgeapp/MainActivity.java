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

public class MainActivity extends AppCompatActivity implements GroceryListAdapter.OnGroceryItemListener {
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
}