package com.example.pocketfridge.fridgeItems;

import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;

public class lists {
    ArrayList<Product> allProducts;

    // add an element to the list
    public void addItem(Product p) { allProducts.add(p); }

    // remove an element from the list
    public void removeItem(Product p) {
        allProducts.remove(p);
    }

    // empty the list
    public void reset() {
        this.allProducts = new ArrayList<Product>();
    }

    // add a collection of items to the list
    public void addItems(ArrayList<Product> items) {
        this.allProducts.addAll(items);
    }

    // returns the number of items
    public int getItemCount() {
        return this.allProducts.size();
    }
}
