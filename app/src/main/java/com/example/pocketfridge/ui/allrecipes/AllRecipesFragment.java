package com.example.pocketfridge.ui.allrecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketfridge.adapter.RecipeAdapter;
import com.example.pocketfridge.R;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.data.RecipeHelper;
import com.example.pocketfridge.fridgeItems.Recipe;
import com.example.pocketfridge.databinding.FragmentAllrecipesBinding;
import java.util.ArrayList;
import java.util.Collections;

public class AllRecipesFragment extends Fragment implements CreateList {
    private FragmentAllrecipesBinding binding;
    private RecyclerView allRecipesRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAllrecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    // For first use, the app should start twice to create the database
    public void onStart() {
        super.onStart();
        try {
            createList();
        } catch (Exception e) {
            onStart();
        }
    }
    @Override
    public void createList() {
        // Creates the list
        RecipeHelper helper = new RecipeHelper(getActivity());
        helper.createDB();
        ArrayList<Recipe> recipes = helper.getAllRecipes();

        // Sorts by name
        Collections.sort(recipes);

        allRecipesRecyclerView = (RecyclerView) getView().findViewById((R.id.allrecipes_recyclerView));
        RecipeAdapter adapter = new RecipeAdapter(this, recipes, "tbl_recipe", helper);
        allRecipesRecyclerView.setHasFixedSize(false);
        allRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        allRecipesRecyclerView.setAdapter(adapter);
    }
}