package com.refridgeapp;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private ArrayList<GroceryItem> groceries;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView groceryNameView;
        private final TextView expirationDateView;

        public ViewHolder(View view) {
            super(view);

            groceryNameView = (TextView) view.findViewById(R.id.groceryName);
            expirationDateView = (TextView) view.findViewById(R.id.experiationDate);
         }

        public TextView getGroceryNameView() {
            return groceryNameView;
        }

        public TextView getExpirationDateView() {
            return expirationDateView;
        }
    }

    public GroceryListAdapter(ArrayList<GroceryItem> groceryData) {
        groceries = groceryData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getGroceryNameView().setText(groceries.get(position).getName());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0);
        Date currentDate = calendar.getTime();

        calendar.setTime(groceries.get(position).getExpirationDate());
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0,
                0,
                0);
        Date expirationDate = calendar.getTime();

        // TODO: Calculate this in a more rigorous manner and use the the proper android strings API with placeholders instead of just concentrating a string here
        viewHolder.getExpirationDateView().setText(Math.round(((expirationDate.getTime() - currentDate.getTime()) / 86400000f))
                + " days until expiration");
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }
}
