package com.refridgeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.w3c.dom.Text;

public class RecipesActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private RecyclerView recyclerView;
    private Button generateRecipes;
    private TextView txtView;
    String listofRecipes = "List of Recipes:\n 1. Potatoes au Gratin\n 2. Ham Sandwich\n 3. Smoothie\n 4. Broccolli Salad";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recipes);

        // Setup recyclerView
//        recyclerView = findViewById(R.id.showItems);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.recipes);

        txtView = findViewById(R.id.ListofRecipes);

        generateRecipes = findViewById(R.id.Generate_recipes_button);

        generateRecipes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                txtView.setText(listofRecipes);
            }
        });
    }

    // Implements the Activites by using switch case and making use of intent
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipes:
//                Intent intent1 = new Intent(RecipesActivity.this, RecipesActivity.class);
//                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.account:
                Intent intent1 = new Intent(RecipesActivity.this, account.class);
                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.fridge:
                Intent intent2 = new Intent(RecipesActivity.this, MainActivity.class);
                startActivity(intent2);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;

            case R.id.sustainability:
                Intent intent3 = new Intent(RecipesActivity.this, Sustainability.class);
                startActivity(intent3);

                return true;
        }
        return false;
    }
}

