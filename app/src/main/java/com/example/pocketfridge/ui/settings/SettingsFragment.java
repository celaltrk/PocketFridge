package com.example.pocketfridge.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.pocketfridge.AddShoppingListActivity;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.databinding.FragmentSettingsBinding;
import com.example.pocketfridge.ui.settings.SettingsViewModel;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);

        binding = FragmentSettingsBinding.inflate(inflater, container, false);
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
        ((Switch)getActivity().findViewById(R.id.sw3)).setChecked(((MainActivity)getActivity()).isNotificationOn());
        ((Switch)getActivity().findViewById(R.id.sw2)).setChecked(((MainActivity)getActivity()).isVibrationOn());
        ((Switch)getActivity().findViewById(R.id.sw)).setChecked(((MainActivity)getActivity()).isAutoAddSwitchOn());
    }

}