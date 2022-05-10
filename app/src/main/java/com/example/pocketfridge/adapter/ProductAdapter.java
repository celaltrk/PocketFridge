package com.example.pocketfridge.adapter;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.attributes.CreateList;
import com.example.pocketfridge.attributes.Expiration;
import com.example.pocketfridge.attributes.Vibration;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.ui.fridge.FridgeFragment;
import com.example.pocketfridge.ui.shoppinglist.ShoppingListFragment;
import java.util.ArrayList;



public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> products;
    private Fragment fr;
    private MainActivity act;
    private String tableName;
    private DBHelper helper;
    private Expiration expiration;
    private Vibration v;

    public ProductAdapter(Fragment fr, ArrayList<Product> products, String tableName, DBHelper helper) {
        this.products = products;
        this.fr = fr;
        this.tableName = tableName;
        this.helper = helper;
        this.act = (MainActivity) fr.getActivity();
        v = new Vibration(fr.getActivity(),100);
        expiration = new Expiration();
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
        // Set the objects
        final Product product = products.get(position);
        final TextView text = holder.textView;
        text.setText(product.toString());

        // Dynamic sizing
        ViewGroup.LayoutParams params = holder.relativeLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.relativeLayout.setLayoutParams(params);

        // Detect and mark expired products in the fridge
        if (fr instanceof FridgeFragment) {
            if (expiration.isExpired(product)) {
                text.setTextColor(Color.rgb(255, 15, 2));
            }
            else if (expiration.isCloseToExpire(product)) {
                text.setTextColor(Color.rgb(100, 15, 2));
                helper.markAsClose(product.getId());
            }
        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                v.vibrate();
                // Delete item in the SL and add it to the fridge if the setting is enabled
                if (fr instanceof ShoppingListFragment) {
                    if (act.isAutoAddSwitchOn())
                        act.autoAdd(product);
                    text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    text.setTextColor(Color.rgb(153, 15, 2));
                    helper.deleteProduct(product.getId(),tableName);
                    Toast.makeText(view.getContext(),"Product bought: " + product.getName(),Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                v.vibrate();
                // Delete the product on long click
                helper.deleteProduct(product.getId(),tableName);
                // Auto-add to the SL
                if (fr instanceof FridgeFragment) {
                    if(act.isAutoAddSwitchOn()) {
                        helper.addToList(product);
                    }
                }
                ((CreateList) fr).createList(); // To update the lists after removal
                Toast.makeText(view.getContext(),"Product deleted: " + product.getName(),Toast.LENGTH_SHORT).show();
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