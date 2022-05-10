package com.example.pocketfridge.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.pocketfridge.MainActivity;
import com.example.pocketfridge.R;
import com.example.pocketfridge.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {
    private FragmentSettingsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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