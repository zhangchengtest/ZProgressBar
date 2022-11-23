package com.zpj.progressbar.demo.utils;

import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;

@SuppressWarnings("WeakerAccess")
public class FileItemDetails extends ItemDetailsLookup.ItemDetails<String> {
    private final FileItemPosition fileItemPosition;
    public String idx;

    FileItemDetails(@NonNull FileItemPosition fileItemPosition) {
        this.fileItemPosition = fileItemPosition;
    }

    @Override
    public int getPosition() {
        return fileItemPosition.getPosition(idx);
    }

    @Nullable
    @Override
    public String getSelectionKey() {
        return idx;
    }

    @Override
    public boolean inSelectionHotspot(@NonNull MotionEvent e) {
        return false;//don't consider taps as selections => Similar to google photos.
        // if true then consider click as selection
    }

    @Override
    public boolean inDragRegion(@NonNull MotionEvent e) {
        return true;
    }
}
