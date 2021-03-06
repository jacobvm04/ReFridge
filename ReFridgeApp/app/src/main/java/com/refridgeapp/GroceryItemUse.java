package com.refridgeapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "grocery_item_use_table")
public class GroceryItemUse implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "item_name")
    private String itemName;

    @ColumnInfo(name = "date")
    private Date date;

    @ColumnInfo(name = "used")
    private boolean used;

    public GroceryItemUse(String itemName, Date date, boolean used) {
        this.itemName = itemName;
        this.date = date;
        this.used = used;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public static int getSustainabilityScore(AppDatabase db) {
        GroceryItemUseDao useDao = db.groceryItemUseDao();
        List<GroceryItemUse> uses = useDao.getAll();

        int score = 50;
        for (GroceryItemUse use: uses) {
            if (use.isUsed())
                score++;
            else
                score--;
        }

        return Math.min(100, Math.max(score, 0));
    }
}
