package com.example.pocketfridge.ui.fridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.AddFoodActivity;
import com.example.pocketfridge.DBHelper;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ItemAdapter;
import com.example.pocketfridge.databinding.FragmentFridgeBinding;
import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;
import java.util.Calendar;

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
        ArrayList<Product> products = new ArrayList<>();
        // TODO FETCH PRODUCTS FROM ADDFOODACTIVITY
        DBHelper dbhelper = new DBHelper(getActivity());
        ArrayList<Product> items = dbhelper.getAll();
        products.add(new Product("sample1","sample","sample", Calendar.getInstance()));
        products.add(new Product("sample2","sample","sample", Calendar.getInstance()));
        fridgeRecyclerView = (RecyclerView) getView().findViewById((R.id.fridge_recyclerView));
        ItemAdapter adapter = new ItemAdapter(products);
        fridgeRecyclerView.setHasFixedSize(true);
        fridgeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        fridgeRecyclerView.setAdapter(adapter);
    }

}