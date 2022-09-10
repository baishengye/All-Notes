package com.bo.app2_matrialdesignui.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bo.app2_matrialdesignui.R;
import com.bo.app2_matrialdesignui.databinding.ListFragmentBinding;

import org.jetbrains.annotations.NotNull;

public class ListFragment extends Fragment {
    ListFragmentBinding listFragmentBinding;
    private RecyclerView recyclerView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        listFragmentBinding=ListFragmentBinding.inflate(inflater,container,false);

        recyclerView = listFragmentBinding.recyclerView;
        return listFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        listFragmentBinding=null;
        recyclerView=null;
    }
}
