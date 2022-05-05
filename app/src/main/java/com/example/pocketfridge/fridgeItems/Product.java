package com.example.pocketfridge.fridgeItems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Product implements Comparable<Product> {
    Calendar expDate;
    String name;
    String category;
    String type;
    SimpleDateFormat dateFormat;
    final String dmy = "dd/MM/yyyy";
    boolean expirable = true;
    boolean isLiquid = false;
    int quantity = 1;
    public Product(String name, String category, String type, Calendar expDate) {
        dateFormat= new SimpleDateFormat(dmy, Locale.UK);
        this.expDate = expDate;
        this.name = name;
        this.type = type;
        this.category = category;
        if (expDate == null)
            this.expDate = Calendar.getInstance();
    }
    @Override
    public int compareTo(Product product) {
        return this.expDate.compareTo(product.expDate);
    }
    @Override
    public String toString() {
        return name + "\nExpiration Date: " + getExpDate();
    }

    public String getExpDate() {
        return dateFormat.format(expDate.getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isExpirable() {
        return expirable;
    }

    public void setExpirable(boolean expirable) {
        this.expirable = expirable;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public void setLiquid(boolean liquid) {
        isLiquid = liquid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Calendar getExpDateCalendar(){
        return expDate;
    }




}
