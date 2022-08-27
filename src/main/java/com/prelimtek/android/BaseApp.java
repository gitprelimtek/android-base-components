package com.prelimtek.android;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

public class BaseApp extends Application {
    public MutableLiveData<Boolean> isLoadingData = new MutableLiveData<Boolean>();
}
