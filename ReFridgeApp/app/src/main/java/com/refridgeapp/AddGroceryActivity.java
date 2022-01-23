package com.refridgeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;

// TODO: Do input validation
// TODO: Add data persistence with sqlite
// TODO: Schedule grocery expiration notifications using JobScheduler
// TODO: Fix the bug from upward navigation that resets the groceryItems state, should be fixed just by properly implementing persistence though
// TODO: Come up with a better date picking UI, the current one isn't the most intuitive (You have to double click the date picking box) and causes bugs

public class AddGroceryActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private Calendar selectedDate;
    private ArrayList<GroceryItem> groceryItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            selectedDate.set(year, month, day, 0, 0, 0);

            // Compensate for month index starting at 0
            month += 1;
            EditText dateView = findViewById(R.id.groceryTextDate);
            // TODO: Use string resources with placeholders instead of concentrating
            dateView.setText(month + "/" + day + "/" + year);
        };

        selectedDate = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this,
                AlertDialog.THEME_HOLO_LIGHT,
                dateSetListener,
                selectedDate.get(Calendar.YEAR),
                selectedDate.get(Calendar.MONTH),
                selectedDate.get(Calendar.DAY_OF_MONTH));
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void addGroceryItem(View view) {
        EditText nameEditText = findViewById(R.id.groceryNameText);
        String groceryName = nameEditText.getText().toString();
        if (!groceryName.isEmpty()) {
            AppDatabase db = AppDatabase.getInstance(getApplicationContext());
            GroceryItemDao dao = db.groceryItemDao();
            dao.insert(new GroceryItem(groceryName, selectedDate.getTime()));
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}