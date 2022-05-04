package com.example.pocketfridge.ui.fridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ItemAdapter;
import com.example.pocketfridge.data.RecipeHelper;
import com.example.pocketfridge.databinding.FragmentFridgeBinding;
import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.fridgeItems.Recipe;

import java.util.ArrayList;
import java.util.Collections;

public class FridgeFragment extends Fragment {
    private FridgeViewModel fridgeViewModel;
    private FragmentFridgeBinding binding;
    RecyclerView fridgeRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fridgeViewModel = new ViewModelProvider(this).get(FridgeViewModel.class);
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
        createFridge();
    }
    public void createFridge() {
       // DBHelper dbhelper = new DBHelper(getActivity());
      //  ArrayList<Product> products = dbhelper.getAll_Fridge();
    //    Collections.sort(products);

        RecipeHelper DBRecipe = new RecipeHelper(getActivity());
        ArrayList<Recipe> recipes;
        DBRecipe.createDB();
        recipes = DBRecipe.getAllRecipes();
        fridgeRecyclerView = (RecyclerView) getView().findViewById((R.id.fridge_recyclerView));
        ItemAdapter adapter = new ItemAdapter(recipes);
        fridgeRecyclerView.setHasFixedSize(true);
        fridgeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        fridgeRecyclerView.setAdapter(adapter);
    }

}