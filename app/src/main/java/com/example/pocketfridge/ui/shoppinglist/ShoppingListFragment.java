package com.example.pocketfridge.ui.shoppinglist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ProductAdapter;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.databinding.FragmentShoppinglistBinding;
import com.example.pocketfridge.fridgeItems.Product;
import java.util.ArrayList;
public class ShoppingListFragment extends Fragment implements CreateList {
    private FragmentShoppinglistBinding binding;
    private RecyclerView shoppingListRecyclerView;
    private DBHelper dbhelper;
    private TextView first;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentShoppinglistBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        first = getView().findViewById(R.id.firstSL);
        createList();
    }
    @Override
    public void createList() {
        dbhelper = new DBHelper(getActivity());
        ArrayList<Product> items = dbhelper.getAll_ShoppingList();
        if (items.size() == 0) {
            first.setVisibility(View.VISIBLE);
        }
        else {
            first.setVisibility(View.INVISIBLE);
        }
        shoppingListRecyclerView = (RecyclerView) getView().findViewById((R.id.SL_recyclerView));
        ProductAdapter adapter = new ProductAdapter(this,items,"Shopping_List", dbhelper);
        shoppingListRecyclerView.setHasFixedSize(true);
        shoppingListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        shoppingListRecyclerView.setAdapter(adapter);
    }
}