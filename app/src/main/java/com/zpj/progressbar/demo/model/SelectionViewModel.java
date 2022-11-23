package com.zpj.progressbar.demo.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectionViewModel extends ViewModel {
    @NonNull
    private final MutableLiveData<Boolean> showFab = new MutableLiveData<>(true);

    @NonNull
    private final MutableLiveData<String> uri = new MutableLiveData<>(null);

    @NonNull
    public MutableLiveData<String> getUri() {
        return uri;
    }

    public void setUri(String uri) {
        getUri().postValue(uri);
    }

    @NonNull
    public MutableLiveData<Boolean> getShowFab() {
        return showFab;
    }

    public void setShowFab(boolean show) {
        getShowFab().postValue(show);
    }

}
