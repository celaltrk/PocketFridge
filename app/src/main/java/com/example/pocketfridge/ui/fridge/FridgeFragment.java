package com.example.pocketfridge.ui.fridge;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.R;
import com.example.pocketfridge.adapter.ProductAdapter;
import com.example.pocketfridge.databinding.FragmentFridgeBinding;
import com.example.pocketfridge.attributes.Notification;
import com.example.pocketfridge.fridgeItems.Product;
import java.util.ArrayList;
import java.util.Collections;

public class FridgeFragment extends Fragment implements CreateList {
    private FragmentFridgeBinding binding;
    private RecyclerView fridgeRecyclerView;
    private TextView first;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
        first = getView().findViewById(R.id.firstFR);
        createList();
    }
    @Override
    public void createList() {
        DBHelper dbhelper = new DBHelper(getActivity());
        ArrayList<Product> closeToExpire = dbhelper.getClosetoExpire();
        // Send notification for the items that are close to expire
        if (closeToExpire.size() > 0 && ((MainActivity) getActivity()).isNotificationOn()) {
            Notification notification = new Notification(getContext());
            notification.createNotification(closeToExpire);
        }
        ArrayList<Product> products = dbhelper.getAll_Fridge();

        // Shows a text for empty page
        if (products.size() == 0)
            first.setVisibility(View.VISIBLE);
        else
            first.setVisibility(View.INVISIBLE);

        // Sorts by expiration dates
        Collections.sort(products);

        fridgeRecyclerView = (RecyclerView) getView().findViewById((R.id.fridge_recyclerView));
        ProductAdapter adapter = new ProductAdapter(this, products, "ProductTable", dbhelper);
        fridgeRecyclerView.setHasFixedSize(true);
        fridgeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        fridgeRecyclerView.setAdapter(adapter);
    }
}