package com.refridgeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GroceryItemUseDao {
    @Query("SELECT * FROM grocery_item_use_table ORDER BY date ASC")
    List<GroceryItemUse> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(GroceryItemUse groceryItemuse);

    @Delete
    void delete(GroceryItemUse groceryItemUse);
}
