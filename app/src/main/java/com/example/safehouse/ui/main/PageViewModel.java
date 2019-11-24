package com.example.safehouse.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<Integer> mPropertyId = new MutableLiveData<>();
    private MutableLiveData<Integer> mRoomId = new MutableLiveData<>();

    public PageViewModel() {
        this.mPropertyId.setValue(1);
        this.mRoomId.setValue(1);
    }

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setmPropertyId(int index) {
        mPropertyId.setValue(index);
        mRoomId.setValue(1);
    }

    public void setmRoomId(int index) {
        mRoomId.setValue(index);
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public MutableLiveData<Integer> getmPropertyId() {
        return mPropertyId;
    }

    public MutableLiveData<Integer> getmRoomId() {
        return mRoomId;
    }

    public LiveData<String> getText() {
        return mText;
    }

}