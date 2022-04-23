package com.example.pocketfridge.ui.allrecipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pocketfridge.databinding.FragmentAllrecipesBinding;

public class AllRecipesFragment extends Fragment {

    private AllRecipesViewModel allRecipesViewModel;
    private FragmentAllrecipesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        allRecipesViewModel =
                new ViewModelProvider(this).get(AllRecipesViewModel.class);

        binding = FragmentAllrecipesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textAllrecipes;
        allRecipesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}