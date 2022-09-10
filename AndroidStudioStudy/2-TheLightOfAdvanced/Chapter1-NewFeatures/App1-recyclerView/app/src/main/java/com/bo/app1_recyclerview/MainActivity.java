package com.bo.app1_recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bo.app1_recyclerview.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;
    private RecyclerView rv;
    private LinearLayoutManager linearLayoutManager;
    private LinkedList<String> homeList;
    private mHomeAdapter homeAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        Log.d(TAG,"onCreate");
    }

    @Override
    protected void initDatum() {
        super.initDatum();
        Log.d(TAG,"initDatum");


        homeList = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            homeList.add(String.valueOf(i));
        }

        homeAdapter = new mHomeAdapter(homeList, this);

        homeAdapter.setOnItemClickListener(new mHomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String str = homeList.get(position);
                Toast.makeText(getMainActivity(),String.format("点击%s",str),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                new AlertDialog.Builder(getMainActivity())
                        .setTitle("删除item")
                        .setPositiveButton("是的", (dialog, which) -> {
                            homeAdapter.removeData(position);
                            homeAdapter.notifyDataSetChanged();
                        })
                        .setNegativeButton("不要",null)
                        .show();
            }
        });

        rv.setAdapter(homeAdapter);

        rv.setItemAnimator(new DefaultItemAnimator());

        //默认分割线
        /*rv.addItemDecoration(new DividerItemDecoration(this,LinearLayout.VERTICAL));*/
        //自定义分割线
        //rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        /*rv.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        rv.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL));*/
    }

    @Override
    protected void initViews() {
        super.initViews();
        Log.d(TAG,"initViews");

        rv = activityMainBinding.rv;

        /*linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);*/
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(staggeredGridLayoutManager);
    }
}