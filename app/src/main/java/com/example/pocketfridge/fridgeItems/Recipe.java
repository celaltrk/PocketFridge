package com.example.pocketfridge.fridgeItems;

public class Recipe implements Comparable<Recipe> {
    private String name;
    private String instructions;
    private String ingredients;
    public Recipe(String name,String instructions,String ingredients) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Recipe: " + name +
                "\n\nIngredients: \n\n" + ingredients +
                "\n\nInstructions: \n\n" + instructions;
    }
    @Override
    public int compareTo(Recipe recipe) {
        return this.name.compareTo(recipe.name);
    }
    public boolean equals(Recipe recipe) {
        return name.equals(recipe.name) && ingredients.equals(recipe.ingredients) && instructions.equals(recipe.instructions);
    }
}
