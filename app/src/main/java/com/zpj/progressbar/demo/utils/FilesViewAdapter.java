package com.zpj.progressbar.demo.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.zpj.progressbar.demo.LogUtils;
import com.zpj.progressbar.demo.R;
import com.zpj.progressbar.demo.vo.PeerResultVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilesViewAdapter extends RecyclerView.Adapter<FilesViewAdapter.ViewHolder> implements FileItemPosition {

    private static final String TAG = FilesViewAdapter.class.getSimpleName();
    private final Context mContext;
    private final FilesAdapterListener mListener;
    private final List<PeerResultVO.TransferFileVO> proxies = new ArrayList<>();

    @Nullable
    private SelectionTracker<String> mSelectionTracker;

    public void setSelectionTracker(SelectionTracker<String> selectionTracker) {
        this.mSelectionTracker = selectionTracker;
    }

    public FilesViewAdapter(@NonNull Context context, @NonNull FilesAdapterListener listener) {
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.file;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new ViewHolder(this, v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final PeerResultVO.TransferFileVO proxy = proxies.get(position);


        boolean isSelected = false;
        if (mSelectionTracker != null) {
            if (mSelectionTracker.isSelected(proxy.getFilePath())) {
                isSelected = true;
            }
        }

        holder.view.setOnClickListener((v) -> mListener.onClick(proxy));

        holder.bind(isSelected, proxy);
        try {
            if (isSelected) {
                int color = MaterialColors.getColor(mContext,
                        R.attr.colorControlHighlight, Color.GRAY);
                holder.view.setBackgroundColor(color);
            } else {
                holder.view.setBackgroundResource(android.R.color.transparent);
            }
            holder.name.setText(proxy.getFilePath());

        } catch (Throwable throwable) {
            LogUtils.error(TAG, throwable);
        }


    }


    @Override
    public int getItemCount() {
        return proxies.size();
    }


    public void updateData(@NonNull List<PeerResultVO.TransferFileVO> proxies) {

        final FileDiffCallback diffCallback = new FileDiffCallback(this.proxies, proxies);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.proxies.clear();
        this.proxies.addAll(proxies);
        diffResult.dispatchUpdatesTo(this);

    }


    String getIdx(int position) {
        return proxies.get(position).getFilePath();
    }

    @Override
    public int getPosition(String idx) {
        for (int i = 0; i < proxies.size(); i++) {
            if (proxies.get(i).getFilePath().equals(idx)) {
                return i;
            }
        }
        return 0;
    }

    public interface FilesAdapterListener {

        void invokeAction(@NonNull PeerResultVO.TransferFileVO proxy, @NonNull View view);

        void onClick(@NonNull PeerResultVO.TransferFileVO proxy);

        void invokePauseAction(@NonNull PeerResultVO.TransferFileVO proxy);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final View view;
        final TextView name;
        final TextView date;
        final TextView size;
        final FileItemDetails fileItemDetails;

        ViewHolder(FileItemPosition pos, View v) {
            super(v);
            v.setLongClickable(true);
            v.setClickable(true);
            v.setFocusable(false);
            view = v;
            name = v.findViewById(R.id.name);
            date = v.findViewById(R.id.date);
            size = v.findViewById(R.id.size);
            fileItemDetails = new FileItemDetails(pos);
        }

        void bind(boolean isSelected, PeerResultVO.TransferFileVO proxy) {
            fileItemDetails.idx = proxy.getFilePath();
            itemView.setActivated(true);
        }

        @NonNull
        ItemDetailsLookup.ItemDetails<String> getItemDetails() {
            return fileItemDetails;
        }

    }

}
