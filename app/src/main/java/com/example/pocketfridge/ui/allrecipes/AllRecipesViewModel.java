package com.example.pocketfridge.ui.allrecipes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AllRecipesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AllRecipesViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}