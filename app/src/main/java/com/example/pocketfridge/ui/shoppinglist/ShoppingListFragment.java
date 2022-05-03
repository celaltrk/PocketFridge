package com.example.pocketfridge.ui.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ItemAdapter;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.databinding.FragmentShoppinglistBinding;
import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingListFragment extends Fragment {

    private ShoppingListViewModel shoppingListViewModel;
    private FragmentShoppinglistBinding binding;
    RecyclerView shoppingListRecyclerView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shoppingListViewModel =
                new ViewModelProvider(this).get(ShoppingListViewModel.class);
        binding = FragmentShoppinglistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void createShoppingList() {
        DBHelper dbhelper = new DBHelper(getActivity());
        ArrayList<Product> items = dbhelper.getAll_ShoppingList();
        Collections.sort(items);
        shoppingListRecyclerView = (RecyclerView) getView().findViewById((R.id.SL_recyclerView));
        ItemAdapter adapter = new ItemAdapter(items);
        shoppingListRecyclerView.setHasFixedSize(true);
        shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        shoppingListRecyclerView.setAdapter(adapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        createShoppingList();
    }
}