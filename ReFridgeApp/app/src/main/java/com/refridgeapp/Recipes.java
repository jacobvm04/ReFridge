package com.refridgeapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.refridgeapp.databinding.ActivityRecipesBinding;

public class Recipes extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityRecipesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

    }
}