package com.zpj.progressbar.demo.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.zpj.progressbar.demo.vo.PeerResultVO;

import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FileDiffCallback extends DiffUtil.Callback {
    private final List<PeerResultVO.TransferFileVO> mOldList;
    private final List<PeerResultVO.TransferFileVO> mNewList;

    public FileDiffCallback(List<PeerResultVO.TransferFileVO> messages, List<PeerResultVO.TransferFileVO> proxies) {
        this.mOldList = messages;
        this.mNewList = proxies;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return false;
    }
}
