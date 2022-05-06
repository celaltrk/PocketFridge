package com.example.pocketfridge.ui.allrecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.adapter.RecipeAdapter;
import com.example.pocketfridge.R;
import com.example.pocketfridge.data.RecipeHelper;
import com.example.pocketfridge.databinding.FragmentFridgeBinding;
import com.example.pocketfridge.fridgeItems.Recipe;

import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {
    private AllRecipesViewModel allRecipesViewModel;
    private FragmentFridgeBinding binding;
    RecyclerView AllRecipesRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AllRecipesViewModel allRecipesViewModel = new ViewModelProvider(this).get(AllRecipesViewModel.class);
        binding = FragmentFridgeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void onStart() {
        super.onStart();
        createAllRecipes();
    }

    public void createAllRecipes() {
        RecipeHelper recipeHelper = new RecipeHelper(getActivity());
        recipeHelper.createDB();
        ArrayList<Recipe> products = recipeHelper.getAllRecipes();
        AllRecipesRecyclerView = (RecyclerView) getView().findViewById((R.id.allRecipesList));
        RecipeAdapter adapter = new RecipeAdapter(this,products,"tbl_recipe",recipeHelper);
        AllRecipesRecyclerView.setHasFixedSize(true);
        AllRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        AllRecipesRecyclerView.setAdapter(adapter);
    }
}