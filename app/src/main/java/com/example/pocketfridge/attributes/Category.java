package com.example.pocketfridge.attributes;
import java.util.ArrayList;

public class Category {
    private ArrayList<String> categories;
    private ArrayList<String> types;
    final static String[][] table = {
            {"No category","No type"},
            {"Vegetable","Pumpkin","Pea","Tomato","Corn","Other"},
            {"Fruit","Apple","Orange","Grapes","Pineapple","Other"},
            {"Dairy product","Milk","Yogurt","Cheese","Other"},
            {"Meat","Chicken","Fish","Lamb","Beef","Other"},
            {"Bread","Bagel","Biscuit","Pizza","Croissant","Other"},
            {"Cereal","Wheat","Rice","Lentil","Oats","Other"},
            {"Snack","Chocolate","Chips","Cookies","Candy","Other"},
            {"Beverage","Fizzy drink","Tea","Coffee","Milkshake","Other"}
    };
    public Category() {
    }
    public ArrayList<String> getCategories() {
        categories = new ArrayList<>();
        for (int i = 0; i < table.length; i++)
            categories.add(table[i][0]);
        return categories;
    }

    public ArrayList<String> getTypes(int index) {
        types = new ArrayList<>();
        for (int j = 1; j < table[index].length; j++)
            types.add(table[index][j]);
        return types;
    }
}
