package com.refridgeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recipes);

        // Setup recyclerView
        recyclerView = findViewById(R.id.showItems);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.recipes);

//        // Add_button add clicklistener
//        next_Activity_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v)
//            {
//
//                // Intents are objects of the android.content.Intent type. Your code can send them
//                // to the Android system defining the components you are targeting.
//                // Intent to start an activity called SecondActivity with the following code:
//
//                Intent intent = new Intent(Oneactivity.this, SecondActivity.class);
//
//                // start the activity connect to the specified class
//                startActivity(intent);
//            }
//        });
    }
    // Implements the Activites by using switch case and making use of intents

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipes:
//                Intent intent1 = new Intent(RecipesActivity.this, RecipesActivity.class);
//                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.account:
                Intent intent2 = new Intent(RecipesActivity.this, account.class);
                startActivity(intent2);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.fridge:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }
}

