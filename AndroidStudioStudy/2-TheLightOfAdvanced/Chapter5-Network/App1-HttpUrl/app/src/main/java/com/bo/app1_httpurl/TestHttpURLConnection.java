package com.bo.app1_httpurl;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class TestHttpURLConnection {

    private static final String TAG = "TestHttpURLConnection";

    private void disableConnectionReuseIfNecessary(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.FROYO){
            System.setProperty("http.keepAlive","false");
        }
    }

    public static HttpURLConnection getHttpURLConnection(String urlStr){
        HttpURLConnection httpURLConnection=null;
        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            //设置持续时间
            httpURLConnection.setConnectTimeout(15000);

            //设置读取超时时间
            httpURLConnection.setReadTimeout(15000);

            //设置请求参数
            httpURLConnection.setRequestMethod("POST");

            //添加Header
            httpURLConnection.setRequestProperty("Connection","Keep-Alive");

            //接收输入流
            httpURLConnection.setDoInput(true);

            //传递参数时需要开启
            httpURLConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpURLConnection;
    }

    public static void postParams(OutputStream outputStream, List<NameValuePair> paramsList) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        for (NameValuePair pair:paramsList){
            if(!TextUtils.isEmpty(stringBuilder)){
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(pair.getName(),"UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(pair.getValue(),"UTF-8"));
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(stringBuilder.toString());
        writer.flush();
        writer.close();
    }

    public void useHttpUrlConnectionPost(String url){
        InputStream inputStream=null;
        HttpURLConnection httpURLConnection=TestHttpURLConnection.getHttpURLConnection(url);
        try {
            ArrayList<NameValuePair> postParams = new ArrayList<>();
            postParams.add(new BasicNameValuePair("ip","14.28.41.120"));
            postParams.add(new BasicNameValuePair("accessKey","alibaba-inc"));
            TestHttpURLConnection.postParams(httpURLConnection.getOutputStream(),postParams);

            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();

            int code = httpURLConnection.getResponseCode();
            String response = convertStreamToString(inputStream);

            Log.d(TAG,"请求状态码:"+code+"\n请求结果:"+response);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将请求的数据流转换成String类型
     * @param inputStream
     * @return
     */
    private static String convertStreamToString(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line=null;
        while ((line=reader.readLine())!=null){
            buffer.append(line).append("\n");
        }
        return buffer.toString();
    }
}
