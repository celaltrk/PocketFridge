package com.example.pocketfridge.fridgeItems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Product implements Comparable<Product> {
    Calendar expDate;
    String name;
    String category;
    int id;
    String type;
    SimpleDateFormat dateFormat;
    final String dmy = "dd/MM/yyyy";
    public Product(String name, String category, String type, Calendar expDate,int id) {
        dateFormat= new SimpleDateFormat(dmy, Locale.UK);
        this.id = id;
        this.expDate = expDate;
        this.name = name;
        this.type = type;
        this.category = category;
        if (expDate == null)
            this.expDate = Calendar.getInstance();
    }
    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getExpDate() {
        return dateFormat.format(expDate.getTime());
    }

    public Calendar getExpDateCalendar(){
        return expDate;
    }

    public void setExpDate(Calendar date) {
        expDate = date;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public int compareTo(Product product) {
        return this.expDate.compareTo(product.expDate);
    }
    @Override
    public String toString() {
        return name  + "\nCategory: " + getCategory() + "\nType: " + getType() + "\nDate: " + getExpDate();
    }
    public int getId() {
        return id;
    }

    public Boolean isExpirable() {
        return true;
    }

    public String getType() {
        return type;
    }

    public Boolean isLiquid() {
        return null;
    }

    public int getQuantity() {
        return 0;
    }

    public String getCategory() {
        return category;
    }
}
