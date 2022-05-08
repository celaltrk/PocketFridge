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
import com.example.pocketfridge.fridgeItems.Recipe;
import com.example.pocketfridge.databinding.FragmentAllrecipesBinding;

import java.util.ArrayList;

public class AllRecipesFragment extends Fragment {
    private AllRecipesViewModel allRecipesViewModel;
    private FragmentAllrecipesBinding binding;
    RecyclerView allrecipesRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allRecipesViewModel = new ViewModelProvider(this).get(AllRecipesViewModel.class);
        binding = FragmentAllrecipesBinding.inflate(inflater, container, false);
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
        RecipeHelper dbhelper = new RecipeHelper(getActivity());
        dbhelper.createDB();
        ArrayList<Recipe> recipes = dbhelper.getAllRecipes();
        allrecipesRecyclerView = (RecyclerView) getView().findViewById((R.id.allrecipes_recyclerView));
        RecipeAdapter adapter = new RecipeAdapter(this, recipes, "tbl_recipe", dbhelper);
        allrecipesRecyclerView.setHasFixedSize(false);
        allrecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        allrecipesRecyclerView.setAdapter(adapter);
    }
}