package com.xiaoyingbo.file;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        persistToFile();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this,SecondActivity.class));
    }

    //在MainActivity中的修改
    private void persistToFile() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(18,"章北海");
                File dir = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/");
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File cachedFile = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/"+"User.text");
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(
                            new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);
                    Log.d("fileDemo","persist user:" + user);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}