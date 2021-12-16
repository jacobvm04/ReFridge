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
    private OnGroceryItemListener onGroceryItemListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView groceryNameView;
        private final TextView expirationDateView;
        private OnGroceryItemListener onGroceryItemListener;

        public ViewHolder(View view, OnGroceryItemListener groceryItemListener) {
            super(view);

            groceryNameView = (TextView) view.findViewById(R.id.groceryName);
            expirationDateView = (TextView) view.findViewById(R.id.experiationDate);
            onGroceryItemListener = groceryItemListener;

            view.findViewById(R.id.checkItemUsed).setOnClickListener(this);
         }

        public TextView getGroceryNameView() {
            return groceryNameView;
        }

        public TextView getExpirationDateView() {
            return expirationDateView;
        }

        @Override
        public void onClick(View view) {
            onGroceryItemListener.onGroceryItemClick(getAdapterPosition());
        }
    }

    public interface OnGroceryItemListener {
        void onGroceryItemClick(int position);
    }

    public GroceryListAdapter(ArrayList<GroceryItem> groceryData, OnGroceryItemListener groceryItemListener) {
        groceries = groceryData;
        onGroceryItemListener = groceryItemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grocery_item, viewGroup, false);

        return new ViewHolder(view, onGroceryItemListener);
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
