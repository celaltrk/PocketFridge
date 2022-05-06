package com.example.pocketfridge.fridgeItems;

import java.util.ArrayList;

public class Recipe implements Comparable<Recipe> {
    String name;
    String instructions;
    String ingredients;
    public Recipe(String name,String instructions,String ingredients) {
        this.name = name;
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

    @Override
    public String toString() {
        return
                "instructions='" + instructions + '\'' +
                ", ingredients='" + ingredients ;
    }

    @Override
    public int compareTo(Recipe recipe) {
        return this.name.compareTo(recipe.name);
    }
}
