package com.example.pocketfridge.adapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.AddFoodActivity;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.attributes.ExpirationController;
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
    private ExpirationController expController;

    public ProductAdapter(Fragment fr, ArrayList<Product> products, String tableName, DBHelper helper) {
        this.products = products;
        this.fr = fr;
        this.tableName = tableName;
        this.helper = helper;
        expController = new ExpirationController();
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
        final Product product = products.get(position);
        holder.textView.setText(product.toString());
        ViewGroup.LayoutParams params = holder.relativeLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.relativeLayout.setLayoutParams(params);
        if (fr instanceof FridgeFragment) {
            if (product.getExpDateCalendar().before(Calendar.getInstance())) {
                holder.textView.setTextColor(Color.rgb(150, 15, 2));
            }
            if (expController.isCloseToExpire(product)) {
                if (!(product.getExpDateCalendar().before(Calendar.getInstance())))
                holder.textView.setTextColor(Color.rgb(100, 15, 2));
                helper.markAsClose(product.getId());
            }
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vibrator v = (Vibrator) fr.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.EFFECT_DOUBLE_CLICK));
                }
                if (fr instanceof  ShoppingListFragment) {
                    helper.addOne(product);
                    holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.textView.setTextColor(Color.rgb(153, 15, 2));
                    helper.deleteProduct(product.getId(),tableName);
                    Toast.makeText(view.getContext(),"Product bought: " + product.getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Vibrator v = (Vibrator) fr.getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                }
                Toast.makeText(view.getContext(),"Product deleted: " + product.getName(),Toast.LENGTH_SHORT).show();
                helper.deleteProduct(product.getId(),tableName);
                if (fr instanceof FridgeFragment) {
                    ((FridgeFragment) fr).createFridge();
                     helper.addToList(product);
                }
                if (fr instanceof ShoppingListFragment)
                    ((ShoppingListFragment) fr).createShoppingList();
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