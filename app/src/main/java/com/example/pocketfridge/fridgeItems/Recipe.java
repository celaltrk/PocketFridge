package com.example.pocketfridge.fridgeItems;

import java.util.ArrayList;

public class Recipe {
    String instructions;
    ArrayList<Product> ingredients;
    public Recipe(String instructions, ArrayList<Product> ingredients) {
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    public ArrayList<Product> getIngredients() {
        return ingredients;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setIngredients(ArrayList<Product> ingredients) {
        this.ingredients = ingredients;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
