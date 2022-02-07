package com.refridgeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Sustainability extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private TextView scoreContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sustainability);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.sustainability);

        scoreContent = findViewById(R.id.scoreContent);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        int score = GroceryItemUse.getSustainabilityScore(db);
        scoreContent.setText(String.valueOf(score));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.recipes:
                Intent intent1 = new Intent(Sustainability.this, RecipesActivity.class);
                startActivity(intent1);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            case R.id.account:
                Intent intent2 = new Intent(Sustainability.this, account.class);
                startActivity(intent2);
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.fridge:
                Intent intent3 = new Intent(Sustainability.this, MainActivity.class);
                startActivity(intent3);

                return true;

            case R.id.sustainability:

                return true;
        }
        return false;
    }
}