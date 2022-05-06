package com.example.pocketfridge;

import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;
import java.util.Collections;

public class priorityOrganizer {

    public static ArrayList<Product> sortItems(ArrayList<Product> products) {
        Collections.sort(products);// this is temporary, will implement an optimized version of merge-sort
        return products;
    }

    // this method assumes the list given is already sorted
    public static int findIndexOf(ArrayList<Product> products, Product p) {
        // implement standard binary search
        int l = 0, r = products.size() - 1;
        int m = l + r / 2;
        while (l <= r) {
            if (products.get(m).equals(p))
                return m;

            if (products.get(m).compareTo(p)<0)
                r = m - 1;
            else
                l = m + 1;

            m = l + r / 2;
        }
        return -1;
    }

    // this array assumes the given list is unsorted
    public static int findIndexOfUnsorted(ArrayList<Product> products, Product p) {
        // standard linear search
        int i = 0;
        for (Product now : products) {
            if (now.equals(p)) return i;
            i++;
        }
        return -1;
    }

}
