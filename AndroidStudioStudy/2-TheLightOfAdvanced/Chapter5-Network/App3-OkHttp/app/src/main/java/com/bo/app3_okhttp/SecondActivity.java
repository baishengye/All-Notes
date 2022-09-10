package com.bo.app3_okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bo.app3_okhttp.databinding.ActivitySecondBinding;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.Objects;

import okhttp3.Request;
import okhttp3.Response;

public class SecondActivity extends BaseActivity {
    private static final String TAG = "SecondActivity";
    ActivitySecondBinding activitySecondBinding;
    private Button btAsyncGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySecondBinding=ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(activitySecondBinding.getRoot());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btAsyncGet = activitySecondBinding.btAsyncGet;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btAsyncGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHHtpEngine.getInstance(getThisActivity())
                        .getAsyncHttp("http://10.0.2.2/get_data.json", new ResultCallback() {
                            @Override
                            public void onError(Request request, Exception e) {
                                Log.e(TAG,e.getMessage(),e);
                            }

                            @Override
                            public void onResponse(Response response) throws IOException{
                                String str = responseToString(response);
                                Log.d(TAG,str);
                                btAsyncGet.setText(str);
                            }
                        });
            }
        });
    }

    private String responseToString(Response response) throws IOException {
        InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String line="";
        StringBuilder str=new StringBuilder();
        while((line=bufferedReader.readLine())!=null){
            str.append("\n").append(line);
        }

        return str.toString();
    }
}