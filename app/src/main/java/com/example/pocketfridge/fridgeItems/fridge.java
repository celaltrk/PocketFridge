package com.example.pocketfridge.fridgeItems;

import com.example.pocketfridge.fridgeItems.lists;

import java.util.ArrayList;

public class fridge extends lists {

    // hold indexes of close-to-expire and expired items amongst all of the items,
    // respectively
    ArrayList<Integer> closeToExpire; // for items that are close-to-expire, but not yet have expired
    ArrayList<Integer> expiredItems; // for items that have expired

    // finds and returns a list of indexes of expired items
    public ArrayList<Integer> findExpiredItems() {
        ArrayList<Integer> expired = new ArrayList<Integer>();
        for (int i = 0; i < this.allProducts.size(); i++) {
            if (this.allProducts.get(i).isExpired() == true) {// if the product we're looking at is expired
                expired.add(i);// add the current index
            }
        }
        return expired;
    }

    // finds and returns a list of indexes of close-to-expire items
    public ArrayList<Integer> findCloseToExpireItems() {
        ArrayList<Integer> closeToExpire = new ArrayList<Integer>();
        for (int i = 0; i < this.allProducts.size(); i++) {
            if (this.allProducts.get(i).isCloseToExpire() == true) {// if the product we're looking at is close-to-expire
                closeToExpire.add(i);// add the current index
            }
        }
        return closeToExpire;
    }

    public void updateExpiredItems() {
        this.expiredItems = findExpiredItems();
    }

    // removes all expired items
    public void removeExpiredItems() {

        ArrayList<Integer> expired = this.findExpiredItems();
        for (int i = 0; i < expired.size(); i++) {
            this.allProducts.remove(expired.get(i));
        }
        updateExpiredItems();
    }

}
