package com.bo.app4_toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.palette.graphics.Palette;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bo.app4_toolbar.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    ActivityMainBinding activityMainBinding;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        tb = activityMainBinding.mtb.tb;
        tb.setTitle(getTitle());
        setSupportActionBar(tb);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setLogo(R.drawable.ic_launcher_foreground);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bitmap);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable @org.jetbrains.annotations.Nullable Palette palette) {
                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(vibrantSwatch.getRgb()));
            }
        });
    }

    @Override
    protected void initListeners() {
        super.initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.tb_search:
                Toast.makeText(getMainActivity(),"搜索",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb_share:
                Toast.makeText(getMainActivity(),"分享",Toast.LENGTH_SHORT).show();
                break;
            case R.id.tb_setting:
                Toast.makeText(getMainActivity(),"设置",Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}