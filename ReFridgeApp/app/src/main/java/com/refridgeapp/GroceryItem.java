package com.refridgeapp;

import java.io.Serializable;
import java.util.Date;

public class GroceryItem implements Serializable {
    private String name;
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
