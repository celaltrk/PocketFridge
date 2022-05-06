package com.example.pocketfridge.adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.R;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.ui.fridge.FridgeFragment;
import com.example.pocketfridge.ui.shoppinglist.ShoppingListFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> products;
    private Fragment fr;
    private String tableName;
    private DBHelper helper;


    // RecyclerView recyclerView;
    public ProductAdapter(Fragment fr, ArrayList<Product> products, String tableName, DBHelper helper) {
        this.products = products;
        this.fr = fr;
        this.tableName = tableName;
        this.helper = helper;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product myListData = products.get(position);
        holder.textView.setText(myListData.toString());

        if (fr instanceof FridgeFragment) {
            if (myListData.getExpDateCalendar().before(Calendar.getInstance())) {
                holder.textView.setTextColor(Color.RED);
                //((FridgeFragment) fr).createFridge();
            }
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"Product deleted: " + myListData.getName(),Toast.LENGTH_SHORT).show();
                helper.deleteProduct(myListData.getId(),tableName);
                if (fr instanceof FridgeFragment) {
                    ((FridgeFragment) fr).createFridge();
                    // TODO add the product to the shopping list
                }
                if (fr instanceof ShoppingListFragment) {
                    ((ShoppingListFragment) fr).createShoppingList();
                }
              return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.item_in_fridge);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}