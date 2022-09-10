package com.bo.app3_okhttp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.os.EnvironmentCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bo.app3_okhttp.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.RequestBody.create;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;

    private static final MediaType MEDIA_TYPE_MARKDOWN=MediaType.parse("text/x-markdown; charset=utf-8");
    private static final MediaType MEDIA_TYPE_JPG=MediaType.parse("image/jpg");

    private final String URLGet="https://blog.csdn.net/m0_46355846";
    private final String URL="https://ip.taobao.com/outGetIpInfo";
    private final String URLImage="https://img2.baidu.com/it/u=1267957884,4012739321&fm=253&fmt=auto&app=138&f=PNG?w=1126&h=500";
    private Button btSyncGet;
    private Button btSyncPost;
    private Button btAsyncPost;
    private Button btTextPost;
    private Button btAsyncGet;
    private Button btDownloadFile;

    private  final int REQUEST_EXTERNAL_STORAGE = 1;
    private  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Button btAsyncPostFile;
    private Button btTimeoutGet;
    private Button btCancelGet;


    private ScheduledExecutorService executor= Executors.newScheduledThreadPool(1);//只有一个线程的线程池

    /**
     * 申请访问内存的权限
     * @param activity
     */
    public  void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        verifyStoragePermissions(getThisActivity());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btAsyncGet = activityMainBinding.btAsyncGet;
        btSyncGet = activityMainBinding.btSyncGet;
        btSyncPost = activityMainBinding.btSyncPost;
        btAsyncPost = activityMainBinding.btAsyncPost;
        btTextPost = activityMainBinding.btTextPost;
        btDownloadFile = activityMainBinding.btDownloadFile;
        btAsyncPostFile = activityMainBinding.btAsyncPostFile;
        btTimeoutGet = activityMainBinding.btTimeoutGet;
        btCancelGet = activityMainBinding.btCancelGet;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        //同步GET
        btSyncGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Request request = new Request.Builder()
                                .url(URLGet)
                                .build();
                        OkHttpClient client = new OkHttpClient();
                        try {
                            Response response = client.newCall(request).execute();
                            Log.d(TAG, Objects.requireNonNull(response.body()).toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                },"同步GET").start();
            }
        });

        //异步GET
        btAsyncGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Thread: "+String.valueOf(Thread.currentThread().getId()));

                Request.Builder builder = new Request.Builder().url(URLGet);
                builder.method("GET",null);
                Request request = builder.build();

                OkHttpClient okHttpClient = new OkHttpClient();
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    //Callback并非是在主线程中运行
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String str = Objects.requireNonNull(response.body()).toString();
                        //Log.d(TAG,str);
                        //Log.d(TAG, "Thread: "+String.valueOf(Thread.currentThread().getId()));
                        btAsyncGet.setText(str);
                    }
                });
            }
        });

        //同步POST
        btSyncPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //构建FormBody
                        FormBody formBody = new FormBody.Builder()
                                .add("ip", "14.28.41.120")
                                .add("accessKey", "alibaba-inc")
                                .build();

                        Request request = new Request.Builder()
                                .url(URL)
                                .post(formBody)
                                .build();

                        OkHttpClient okHttpClient = new OkHttpClient();
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            Log.d(TAG, Objects.requireNonNull(response.body()).toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        //异步POST
        btAsyncPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //构建FormBody
                FormBody formBody = new FormBody.Builder()
                        .add("ip", "14.28.41.120")
                        .add("accessKey", "alibaba-inc")
                        .build();

                Request request = new Request.Builder()
                        .url(URL)
                        .post(formBody)
                        .build();

                OkHttpClient okHttpClient = new OkHttpClient();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String str = Objects.requireNonNull(response.body()).toString();
                        Log.d(TAG,str);
                    }
                });
            }
        });

        //同步上传文件
        btTextPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filePath="";
                if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
                }else{
                    return;
                }

                File file = new File(filePath,"fate.jpg");
                Request request = new Request.Builder()
                        .url("http://10.0.2.2/fate.jpg")
                        .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, file))
                        .build();

                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG, e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(TAG,"onResponse");
                        Log.d(TAG, Objects.requireNonNull(response.body()).toString());
                    }
                });
            }
        });

        //异步下载文件
        btDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder()
                        .url(URLImage)
                        .build();

                new OkHttpClient().newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
                        FileOutputStream fileOutputStream=null;
                        String filePath="";
                        try {
                            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                                filePath=Environment.getExternalStorageDirectory().getAbsolutePath();
                            }else {
                                filePath=getFilesDir().getAbsolutePath();
                            }

                            File file = new File(filePath, "fate.jpg");
                            if(file!=null){
                                fileOutputStream=new FileOutputStream(file);
                                byte[] bytes = new byte[2048];
                                int len=0;
                                while((len=inputStream.read(bytes))!=-1){
                                    fileOutputStream.write(bytes,0,len);
                                }
                                fileOutputStream.flush();
                            }
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        //异步上传文件
        btAsyncPostFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient okHttpClient = new OkHttpClient();
                MultipartBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("title", "fate")
                        .addFormDataPart("image", "fate.jpg",
                                create(MEDIA_TYPE_JPG, new File("/sdcard/fate.jpg")))
                        .build();
                Request request = new Request.Builder()
                        .header("Authorization", "Client-ID" + "...")
                        .url(URLImage)
                        .post(body)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        Log.d(TAG, Objects.requireNonNull(response.body()).toString());
                    }
                });
            }
        });

        //设置超时和缓存
        btTimeoutGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File sdCache=getExternalCacheDir();
                int cacheSize=10*1024*1024;
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)//连接时间
                        .writeTimeout(20, TimeUnit.SECONDS)//写入时间
                        .readTimeout(20, TimeUnit.SECONDS)//读取时间
                        .cache(new Cache(sdCache.getAbsoluteFile(), cacheSize))//缓存
                        .build();

                Request request = new Request.Builder()
                        .url("http://10.0.2.2/get_data.json")
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
                        InputStreamReader reader = new InputStreamReader(inputStream);
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        String line="";
                        StringBuilder str=new StringBuilder();
                        while((line=bufferedReader.readLine())!=null){
                            str.append("\n").append(line);
                        }

                        Log.d(TAG,str.toString());
                    }
                });
            }
        });


        btCancelGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Request request = new Request.Builder()
                        .url("https://www.baidu.com")
                        .get()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();

                Call call = new OkHttpClient().newCall(request);
                Call finalCall = call;

                //100ms后取消call
                executor.schedule(new Runnable() {
                    @Override
                    public void run() {
                        finalCall.cancel();
                    }
                },10000,TimeUnit.MILLISECONDS);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e(TAG,e.getMessage(),e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if(response.cacheResponse()!=null){
                            String str = Objects.requireNonNull(response.cacheResponse()).toString();
                            Log.d(TAG,str);
                        }else{
                            String str = Objects.requireNonNull(response.networkResponse()).toString();
                            Log.d(TAG,str);
                        }
                    }
                });
            }
        });
    }
}