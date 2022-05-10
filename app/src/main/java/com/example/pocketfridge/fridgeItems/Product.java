package com.example.pocketfridge.fridgeItems;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Product implements Comparable<Product> {
    private Calendar expDate;
    private String name;
    private String category;
    private int id;
    private String type;
    private SimpleDateFormat dateFormat;
    private final String dmy = "dd/MM/yyyy";
    public Product(String name, String category, String type, Calendar expDate,int id) {
        dateFormat = new SimpleDateFormat(dmy, Locale.UK);
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
    public String getType() {
        return type;
    }
    public String getCategory() {
        return category;
    }
}
