package com.bo.app4_retrofit.ui.retrofitTest;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bo.app4_retrofit.BaseActivity;
import com.bo.app4_retrofit.databinding.ActivityRetrofitBinding;
import com.bo.app4_retrofit.logic.model.IpModel;
import com.bo.app4_retrofit.logic.model.ResponseBase;
import com.bo.app4_retrofit.logic.network.RetrofitCreator;
import com.bo.app4_retrofit.logic.network.Service;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitActivity extends BaseActivity {


    private static final String TAG = "RetrofitActivity";

    ActivityRetrofitBinding activityRetrofitBinding;
    private Retrofit retrofitData;
    private Retrofit retrofitIp;
    private Button btGetQueryMap;
    private Button btGetPath;
    private Button btGetQuery;
    private Button btPostField;
    private Button btPostBody;
    private Retrofit retrofitHttpBin;
    private Button btPostUploadOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRetrofitBinding=ActivityRetrofitBinding.inflate(getLayoutInflater());
        setContentView(activityRetrofitBinding.getRoot());

    }

    @Override
    protected void initViews() {
        super.initViews();
        btGetPath = activityRetrofitBinding.btGetPath;
        btGetQuery = activityRetrofitBinding.btGetQuery;
        btGetQueryMap = activityRetrofitBinding.btGetQueryMap;
        btPostField = activityRetrofitBinding.btPostField;
        btPostBody = activityRetrofitBinding.btPostBody;
        btPostUploadOne = activityRetrofitBinding.btPostUploadOne;
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        retrofitData = RetrofitCreator.getRetrofitData();
        retrofitIp = RetrofitCreator.getRetrofitIp();
        retrofitHttpBin = RetrofitCreator.getRetrofitHttpBin();
    }

    @Override
    protected void initListeners() {
        super.initListeners();


        btGetPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitData.create(Service.class)
                        .getResponses("get_data.json")
                        .enqueue(new Callback<List<ResponseBase>>() {
                            //这个框架下的异步请求网络的CallBack是在主线程中
                            @Override
                            public void onResponse(@NotNull Call<List<ResponseBase>> call, Response<List<ResponseBase>> response) {
                                assert response.body() != null;
                                for(ResponseBase responseBase:response.body()){
                                    Log.d(TAG,responseBase.toString());
                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<List<ResponseBase>> call, Throwable t) {
                                Log.e(TAG,call.toString(),t);
                            }
                        });

            }
        });

        btGetQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitIp.create(Service.class)
                        .getIP("14.28.41.120","alibaba-inc")
                        .enqueue(new Callback<IpModel>() {
                            @Override
                            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                                assert response.body() != null;
                                Log.d(TAG,response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<IpModel> call, Throwable t) {
                                Log.d(TAG,call.toString(),t);
                            }
                        });
            }
        });

        btGetQueryMap.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                Map<String,String> options=Map.of("ip","14.28.41.120","accessKey","alibaba-inc");
                retrofitIp.create(Service.class)
                        .getIP(options)
                        .enqueue(new Callback<IpModel>() {
                            @Override
                            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                                assert response.body() != null;
                                Log.d(TAG,response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<IpModel> call, Throwable t) {
                                Log.d(TAG,call.toString(),t);
                            }
                        });
            }
        });

        btPostField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitIp.create(Service.class)
                        .postIP("14.28.41.120","alibaba-inc")
                        .enqueue(new Callback<IpModel>() {
                            @Override
                            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                                assert response.body() != null;
                                Log.d(TAG,response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<IpModel> call, Throwable t) {
                                Log.d(TAG,call.toString(),t);
                            }
                        });
            }
        });

        btPostBody.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onClick(View v) {
                Map<String,String> options=Map.of("ip","14.28.41.120","accessKey","alibaba-inc");
                retrofitIp.create(Service.class)
                        .postResponses(getRequestBody(options))
                        .enqueue(new Callback<IpModel>() {
                            @Override
                            public void onResponse(Call<IpModel> call, Response<IpModel> response) {
                                assert response.body() != null;
                                Log.d(TAG,response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<IpModel> call, Throwable t) {
                                Log.d(TAG,call.toString(),t);
                            }
                        });

            }
        });

        btPostUploadOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(),"fate.jpg");
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("photos","fate.jpg",requestBody);

                retrofitData.create(Service.class)
                        .postResponses(photo,requestBody)
                        .enqueue(new Callback<okhttp3.Response>() {
                            @Override
                            public void onResponse(Call<okhttp3.Response> call, Response<okhttp3.Response> response) {
                                assert response.body() != null;
                                Log.d(TAG,response.body().toString());
                            }

                            @Override
                            public void onFailure(Call<okhttp3.Response> call, Throwable t) {
                                Log.d(TAG,call.toString(),t);
                            }
                        });

            }
        });

        //todo 上传文件

        //todo 批量上传
    }

    public RequestBody getRequestBody(Map<String, String> hashMap){
        StringBuffer data=new StringBuffer();
        if(hashMap!=null&&hashMap.size()>0){
            Iterator iter=hashMap.entrySet().iterator();
            while(iter.hasNext()){
                Map.Entry entry=(Map.Entry)iter.next();
                Object key=entry.getKey();
                Object val=entry.getValue();
                data.append(key).append("=").append(val).append("&");
            }
        }
        String jso=data.substring(0,data.length()-1);
        RequestBody requestBody=
                RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),jso);

        return requestBody;
    }
}