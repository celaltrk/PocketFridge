package com.example.pocketfridge.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketfridge.AddShoppingListActivity;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.fridgeItems.Product;

import java.util.ArrayList;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private ArrayList<Product> products;
    private Activity act;
    private String tableName;

    // RecyclerView recyclerView;
    public ProductAdapter(Activity act, ArrayList<Product> products, String tableName) {
        this.products = products;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        this.act = act;
        this.tableName = tableName;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product myListData = products.get(position);
        holder.textView.setText(products.get(position).toString());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Clicked on item: " + myListData.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.relativeLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(view.getContext(),"Deleted item: " + holder.getAdapterPosition() ,Toast.LENGTH_SHORT).show();
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