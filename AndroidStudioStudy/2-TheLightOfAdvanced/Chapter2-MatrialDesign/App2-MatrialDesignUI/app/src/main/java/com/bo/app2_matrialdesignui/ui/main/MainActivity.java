package com.bo.app2_matrialdesignui.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bo.app2_matrialdesignui.R;
import com.bo.app2_matrialdesignui.databinding.ActivityMainBinding;
import com.bo.app2_matrialdesignui.logic.basic.BaseActivity;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding activityMainBinding;
    private Toolbar mainToolbar;
    private ViewPager mainVp;
    private TabLayout mainTabs;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CollapsingToolbarLayout mainCollapsingToolbarL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        //标题栏
        mainToolbar = activityMainBinding.mainContent.mainToolbar;
        setSupportActionBar(mainToolbar);

        //标题栏按钮
        ActionBar supportActionBar = getSupportActionBar();
        Objects.requireNonNull(supportActionBar).setHomeAsUpIndicator(R.drawable.ic_menu);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        //ViewPager
        mainVp = activityMainBinding.mainContent.mainVp;
        initViewPager();

        //抽屉布局
        drawerLayout = activityMainBinding.getRoot();

        //导航
        navigationView = activityMainBinding.nv;

        //标题栏布局
        mainCollapsingToolbarL = activityMainBinding.mainContent.mainCollapsingToolbarL;
        mainCollapsingToolbarL.setTitle("哆啦A梦");
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        if(navigationView!=null){
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                    item.setChecked(true);

                    String str = item.getTitle().toString();
                    Toast.makeText(getThisActivity(),str,Toast.LENGTH_SHORT).show();

                    //关闭导航
                    drawerLayout.closeDrawers();
                    return true;
                }
            });
        }
    }

    /**
     * 菜单点击
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }

        return true;
    }

    private void initViewPager() {
        mainTabs = activityMainBinding.mainContent.mainTabs;

        List<String> titles=new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            titles.add(String.valueOf(i));
            mainTabs.addTab(mainTabs.newTab().setText(titles.get(i)));
        }

        List<Fragment> fragments=new LinkedList<>();
        for (int i = 0; i < titles.size(); i++) {
            fragments.add(new ListFragment());
        }

        FragmentAdapter fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragments,titles);

        mainVp.setAdapter(fragmentAdapter);

        mainTabs.setupWithViewPager(mainVp);
    }

    public static void startMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }

    public void checkIn(View view) {
        Snackbar.make(view,"点击了浮动按钮",Snackbar.LENGTH_SHORT)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getThisActivity(),"退出SnackBar",Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}