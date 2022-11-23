package com.zpj.progressbar.demo.fragments;


import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigationrail.NavigationRailView;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.JsonElement;
import com.zpj.progressbar.demo.LogUtils;
import com.zpj.progressbar.demo.MainActivity;
import com.zpj.progressbar.demo.R;
import com.zpj.progressbar.demo.Settings;
import com.zpj.progressbar.demo.dto.DownloadDTO;
import com.zpj.progressbar.demo.model.LiteViewModel;
import com.zpj.progressbar.demo.model.SelectionViewModel;
import com.zpj.progressbar.demo.utils.FileItemDetailsLookup;
import com.zpj.progressbar.demo.utils.FilesItemKeyProvider;
import com.zpj.progressbar.demo.utils.FilesViewAdapter;
import com.zpj.progressbar.demo.utils.GsonUtil;
import com.zpj.progressbar.demo.utils.HttpUrl;
import com.zpj.progressbar.demo.utils.OKHttp3Util;
import com.zpj.progressbar.demo.vo.PeerResultVO;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Request;


public class FilesFragment extends Fragment implements AccessFragment,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = FilesFragment.class.getSimpleName();
    private final ArrayDeque<Long> stack = new ArrayDeque<>();
    private boolean widthMode = false;
    private long lastClickTime = 0;
    private FilesViewAdapter filesViewAdapter;
    private RecyclerView recyclerView;
    private ActionMode actionMode;
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationRailView navigationRailView;
    private LiteViewModel liteViewModel;
    private SelectionTracker<String> selectionTracker;
    private FileItemDetailsLookup fileItemDetailsLookup;
    private SelectionViewModel selectionViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        releaseActionMode();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void findInPage() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.files_view, container, false);
    }

    private void switchDisplayModes() {
        if (!widthMode) {
            navigationRailView.setVisibility(View.GONE);
        } else {
            navigationRailView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        liteViewModel = new ViewModelProvider(requireActivity()).get(LiteViewModel.class);

        recyclerView = view.findViewById(R.id.recycler_view_message_list);


        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setItemAnimator(null);

        filesViewAdapter = new FilesViewAdapter(requireContext(), (MainActivity)requireActivity());
        recyclerView.setAdapter(filesViewAdapter);



        liteViewModel.getLiveDataFiles().observe(getViewLifecycleOwner(), (proxies) -> {
            if (proxies != null) {
                filesViewAdapter.updateData(proxies);
            }
        });

        fileItemDetailsLookup = new FileItemDetailsLookup(recyclerView);

        selectionTracker = new SelectionTracker.Builder<>(TAG, recyclerView,
                new FilesItemKeyProvider(filesViewAdapter),
                fileItemDetailsLookup,
                StorageStrategy.createStringStorage())
                .withSelectionPredicate(SelectionPredicates.createSelectSingleAnything())
                .build();



        selectionTracker.addObserver(new SelectionTracker.SelectionObserver<String>() {
            @Override
            public void onSelectionChanged() {
                if (selectionTracker.hasSelection()) {
                    selectionViewModel.setUri(selectionTracker.getSelection().iterator().next());
                }else{
                    selectionViewModel.setUri(null);
                }
                super.onSelectionChanged();
            }

            @Override
            public void onSelectionRestored() {
                if (selectionTracker.hasSelection()) {
                    selectionViewModel.setUri(selectionTracker.getSelection().iterator().next());
                }else{
                    selectionViewModel.setUri(null);
                }
                super.onSelectionRestored();
            }
        });

        filesViewAdapter.setSelectionTracker(selectionTracker);

        selectionViewModel = new ViewModelProvider(requireActivity()).get(SelectionViewModel.class);

    }









    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);



    }

    @Override
    public void releaseActionMode() {
        try {
            if (isResumed()) {
                if (actionMode != null) {
                    actionMode.finish();
                    actionMode = null;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.error(TAG, throwable);
        }
    }

}
