package com.example.pocketfridge.attributes;

import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;

public interface Lists {

    // add an element to the list
    public void addItem(Product p);

    // remove an element from the list
    public void removeItem(Product p) ;

    // empty the list
    public void reset() ;

}
