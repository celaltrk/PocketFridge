package com.example.pocketfridge.fridgeItems;

import java.util.ArrayList;

public class Recipe {
    String instructions;
    String ingredients;
    public Recipe(String instructions,String ingredients) {
        this.ingredients = ingredients;
        this.instructions = instructions;
    }
    public String getIngredients() {
        return ingredients;
    }
    public String getInstructions() {
        return instructions;
    }
    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
