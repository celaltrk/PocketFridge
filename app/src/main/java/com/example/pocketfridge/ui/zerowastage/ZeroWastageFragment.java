package com.example.pocketfridge.ui.zerowastage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ProductAdapter;
import com.example.pocketfridge.adapter.RecipeAdapter;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.data.RecipeHelper;
import com.example.pocketfridge.databinding.FragmentZerowastageBinding;
import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.fridgeItems.Recipe;

import java.util.ArrayList;
import java.util.Collections;

public class ZeroWastageFragment extends Fragment {
    private ZeroWastageViewModel zeroWastageViewModel;
    private FragmentZerowastageBinding binding;
    RecyclerView zwRecyclerView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        zeroWastageViewModel = new ViewModelProvider(this).get(ZeroWastageViewModel.class);
        binding = FragmentZerowastageBinding.inflate(inflater, container, false);
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
        createZW();
    }
    public void createZW() {
        RecipeHelper dbhelper = new RecipeHelper(getActivity());
        ArrayList<Recipe> recipes = new ArrayList<>();//dbhelper.suggestRecipe("süt");
        recipes.add(new Recipe("yey","Sütü kaynatttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt\n\n\n\n\n\n\n\nyes","süt"));
        zwRecyclerView = (RecyclerView) getView().findViewById((R.id.zerowastage_recyclerView));
        RecipeAdapter adapter = new RecipeAdapter(this, recipes, "ProductTable", dbhelper);
        zwRecyclerView.setHasFixedSize(false);
        zwRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        zwRecyclerView.setAdapter(adapter);
    }

}