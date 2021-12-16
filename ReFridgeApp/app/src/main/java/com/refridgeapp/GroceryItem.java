package com.refridgeapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "grocery_item_table")
public class GroceryItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int _id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "expiration_date")
    private Date expirationDate;

    public GroceryItem(String name, Date expirationDate) {
        this.name = name;
        this.expirationDate = expirationDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
