package com.example.safehouse.ui.MyProperties;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPropertiesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyPropertiesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is MyProperties fragment. This is where the other properties of the user will be displayed");
    }

    public LiveData<String> getText() {
        return mText;
    }
}