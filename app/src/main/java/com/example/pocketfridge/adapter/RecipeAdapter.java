package com.example.pocketfridge.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocketfridge.R;
import com.example.pocketfridge.data.DBHelper;
import com.example.pocketfridge.data.RecipeHelper;
import com.example.pocketfridge.fridgeItems.Product;
import com.example.pocketfridge.fridgeItems.Recipe;
import com.example.pocketfridge.ui.zerowastage.ZeroWastageFragment;

import java.util.ArrayList;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{
    private ArrayList<Recipe> recipes;
    private Fragment fr;
    private String tableName;
    private RecipeHelper helper;

    public RecipeAdapter(Fragment fr, ArrayList<Recipe> recipes, String tableName, RecipeHelper helper) {
        this.recipes = recipes;
        this.fr = fr;
        this.tableName = tableName;
        this.helper = helper;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.list_recipe, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.textView.setText(recipe.toString());
        ViewGroup.LayoutParams params = holder.relativeLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.relativeLayout.setLayoutParams(params);
        if (fr instanceof ZeroWastageFragment) {

        }
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Clicked on item: " + recipe.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.item_in_recipes);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.relativeLayout);
        }
    }
}