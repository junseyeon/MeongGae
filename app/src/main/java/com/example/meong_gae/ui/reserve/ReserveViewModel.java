package com.example.meong_gae.ui.reserve;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReserveViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ReserveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}