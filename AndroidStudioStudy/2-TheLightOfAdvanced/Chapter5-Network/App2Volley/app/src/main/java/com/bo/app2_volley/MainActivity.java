package com.bo.app2_volley;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bo.app2_volley.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.nio.charset.CharsetEncoder;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding activityMainBinding;

    RequestQueue queue;
    String URL = "https://www.baid.com";
    String URLJson="http://ip.taobao.com/outGetIpInfo?ip=14.28.41.120&accessKey=alibaba-inc";
    String URLImage="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F813c8ea78d7215069433768a2a514cfd386f4729.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657604207&t=7e42c080301d9c7f1b8201e7279bdc64";
    private Button btGetString;
    private Button btPostJson;
    private Button btPostImageRequest;
    private ImageView iv;
    private Button btPostImageLoader;
    private Button btNetworkImageLoader;
    private NetworkImageView niv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
    }

    @Override
    protected void initDatum() {
        super.initDatum();

        queue = Volley.newRequestQueue(getThisActivity());
    }

    @Override
    protected void initViews() {
        super.initViews();

        btGetString = activityMainBinding.btGetString;
        btPostJson = activityMainBinding.btPostJson;
        btPostImageRequest = activityMainBinding.btPostImageRequest;
        btPostImageLoader = activityMainBinding.btPostImageLoader;
        btNetworkImageLoader = activityMainBinding.btNetworkImageLoader;
        niv = activityMainBinding.niv;
        iv = activityMainBinding.iv;
    }

    @Override
    protected void initListeners() {
        super.initListeners();

        btGetString.setOnClickListener(view->{
            StringRequest request = new StringRequest(Request.Method.GET, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("error", error.getMessage(), error);
                        }
                    });
            queue.add(request);
        });

        btPostJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, URLJson, new JSONObject(),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                IpModel ipModel = new Gson().fromJson(response.toString(), IpModel.class);
                                if(ipModel!=null&&ipModel.getData()!=null){
                                    String city = ipModel.getData().getCity();
                                    Log.d(TAG,city);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG,error.getMessage(),error);
                            }
                        });
                queue.add(jsonObjectRequest);

            }
        });

        btPostImageRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageRequest imageRequest = new ImageRequest(URLImage,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap response) {
                                iv.setImageBitmap(response);
                            }
                        },
                        0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.getMessage(), error);
                            }
                        });
                queue.add(imageRequest);
            }
        });

        btPostImageLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                    private LruCache<String, Bitmap> mCache;

                    {
                        int maxCacheSize = 1024 * 1024 * 10;
                        mCache = new LruCache<String, Bitmap>(maxCacheSize) {
                            @Override
                            protected int sizeOf(String key, Bitmap value) {
                                return value.getRowBytes() * value.getHeight();
                            }
                        };
                    }

                    @Override
                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }
                });
                ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(iv, R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground);
                imageLoader.get(URLImage,imageListener);
            }
        });

        btNetworkImageLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
                    private LruCache<String, Bitmap> mCache;

                    {
                        int maxCacheSize = 1024 * 1024 * 10;
                        mCache = new LruCache<String, Bitmap>(maxCacheSize) {
                            @Override
                            protected int sizeOf(String key, Bitmap value) {
                                return value.getRowBytes() * value.getHeight();
                            }
                        };
                    }

                    @Override
                    public Bitmap getBitmap(String url) {
                        return mCache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        mCache.put(url, bitmap);
                    }
                });

                niv.setDefaultImageResId(R.drawable.ic_launcher_background);
                niv.setErrorImageResId(R.drawable.ic_launcher_foreground);
                niv.setImageUrl(URLImage,imageLoader);
            }
        });
    }
}