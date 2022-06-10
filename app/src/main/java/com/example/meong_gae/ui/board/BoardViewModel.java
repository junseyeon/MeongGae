package com.example.meong_gae.ui.board;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BoardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BoardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is board fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}