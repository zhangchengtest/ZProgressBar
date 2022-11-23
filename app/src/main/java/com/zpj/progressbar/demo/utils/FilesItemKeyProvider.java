package com.zpj.progressbar.demo.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;

public class FilesItemKeyProvider extends ItemKeyProvider<String> {

    private final FilesViewAdapter mFilesViewAdapter;


    public FilesItemKeyProvider(@NonNull FilesViewAdapter adapter) {
        super(SCOPE_CACHED);
        mFilesViewAdapter = adapter;
    }

    @Nullable
    @Override
    public String getKey(int position) {
        return mFilesViewAdapter.getIdx(position);
    }

    @Override
    public int getPosition(@NonNull String key) {
        return mFilesViewAdapter.getPosition(key);
    }

}