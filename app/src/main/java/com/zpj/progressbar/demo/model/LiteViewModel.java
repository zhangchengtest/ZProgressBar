package com.zpj.progressbar.demo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.zpj.progressbar.demo.vo.PeerResultVO;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LiteViewModel extends ViewModel {
    // Create a LiveData with a String
    private MutableLiveData<String> mCurrentName;
    // Create a LiveData with a String list
    private MutableLiveData<List<PeerResultVO.TransferFileVO>> mNameListData;

    public MutableLiveData<String> getCurrentName() {
        if (mCurrentName == null) {
            mCurrentName = new MutableLiveData<>();
        }
        return mCurrentName;
    }

    public MutableLiveData<List<PeerResultVO.TransferFileVO>> getLiveDataFiles(){
        if (mNameListData == null) {
            mNameListData = new MutableLiveData<>();
        }
        return mNameListData;
    }
}