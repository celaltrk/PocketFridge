package com.example.pocketfridge.ui.zerowastage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Locale;

public class ZeroWastageFragment extends Fragment {
    private ZeroWastageViewModel zeroWastageViewModel;
    private FragmentZerowastageBinding binding;
    RecyclerView zwRecyclerView;
    TextView first;
    RecipeHelper recipeHelper;
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
        first = getView().findViewById(R.id.firstZW);
        recipeHelper = new RecipeHelper(getActivity());
        createZW();
    }
    public void createZW() {
        ArrayList<Recipe> suggestedRecipes = suggestRecipes();
        if (suggestedRecipes.size() == 0)
            first.setVisibility(View.VISIBLE);
        else
            first.setVisibility(View.INVISIBLE);
        zwRecyclerView = (RecyclerView) getView().findViewById((R.id.zerowastage_recyclerView));
        RecipeAdapter adapter = new RecipeAdapter(this, suggestedRecipes, null, recipeHelper);
        zwRecyclerView.setHasFixedSize(false);
        zwRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        zwRecyclerView.setAdapter(adapter);
    }
    public ArrayList<Recipe> suggestRecipes() {
        DBHelper dbHelper = new DBHelper(getActivity());
        ArrayList<Product> closeToExpire = dbHelper.getClosetoExpire();
        for(Product p : closeToExpire){
            System.out.println(p);
        }
        ArrayList<Recipe> suggested = new ArrayList<>();
        ArrayList<Recipe> temp = new ArrayList<>();
        String name;
        boolean exists;
        for (int i = 0; i < closeToExpire.size(); i++){
            name = closeToExpire.get(i).getName();
            temp = recipeHelper.suggestRecipe(name);
            if(!(name.equals(""))){
                for(int j = 0; j < temp.size(); j++){
                    exists = false;
                    for (int k = 0; k < suggested.size(); k++){
                        if(suggested.get(k).equals(temp.get(j))) exists = true;
                    }
                    if(!exists) suggested.add(temp.get(j));
                }
            }
        }
        return suggested;
    }

}