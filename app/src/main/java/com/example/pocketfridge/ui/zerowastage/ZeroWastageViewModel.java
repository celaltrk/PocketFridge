package com.example.pocketfridge.ui.zerowastage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ZeroWastageViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ZeroWastageViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}