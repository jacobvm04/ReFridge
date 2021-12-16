package com.refridgeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroceryItemDao {
    @Query("SELECT * FROM grocery_item_table ORDER BY expiration_date ASC")
    List<GroceryItem> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GroceryItem groceryItem);

    @Delete
    void delete(GroceryItem groceryItem);
}
