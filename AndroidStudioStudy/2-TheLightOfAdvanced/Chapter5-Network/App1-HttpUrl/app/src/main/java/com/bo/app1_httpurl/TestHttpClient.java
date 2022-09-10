package com.bo.app1_httpurl;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestHttpClient {
    private static final String TAG = "TestHttpClient";

    //创建HttpClient
    public static HttpClient createHttpClient(){
        //new一个HttpClient连接需要的参数
        BasicHttpParams basicHttpParams = new BasicHttpParams();

        //设置连接超时:15秒
        HttpConnectionParams.setConnectionTimeout(basicHttpParams,15000);

        //设置请求超时:15秒
        HttpConnectionParams.setSoTimeout(basicHttpParams,15000);

        //确定是否使用Nagle的算法。Nagle的算法试图通过减少发送的段的数量来节省带宽。
        // 当应用程序希望减少网络延迟并提高性能时，它们可以禁用Nagle的算法(即启用TCP_NODELAY)。数据会更早发送，代价是带宽消耗增加。
        //true:禁用Nagle的算法
        HttpConnectionParams.setTcpNoDelay(basicHttpParams,true);

        //设置Http协议的版本：1.1
        HttpProtocolParams.setVersion(basicHttpParams, HttpVersion.HTTP_1_1);

        //http组件的编码
        HttpProtocolParams.setContentCharset(basicHttpParams, HTTP.UTF_8);

        //持续握手
        HttpProtocolParams.setUseExpectContinue(basicHttpParams,true);


        //new 一个http客户端
        return new DefaultHttpClient(basicHttpParams);
    }

    //POST操作
    public static void useHttpClientPost(String url){
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Connection","Keep-Alive");

        try {
            HttpClient httpClient = createHttpClient();
            List<NameValuePair> postParams=new ArrayList<>();

            postParams.add(new BasicNameValuePair("ip","14.28.41.120"));
            postParams.add(new BasicNameValuePair("accessKey","alibaba-inc"));
            httpPost.setEntity(new UrlEncodedFormEntity(postParams));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if(httpEntity!=null){
                InputStream inputStream = httpEntity.getContent();
                String response = convertStreamToString(inputStream);
                Log.d(TAG,"请求状态码:"+statusCode+"\n请求结果:"+response);
                inputStream.close();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //GET操作
    public static void useHttpClientGet(String url){
        //创建GET请求
        HttpGet httpGet = new HttpGet(url);

        //添加连接头
        httpGet.addHeader("Connection","Keep-Alive");

        try {
            HttpClient httpClient = createHttpClient();

            //客户端发送GET请求获取响应
            HttpResponse httpResponse = httpClient.execute(httpGet);

            //获取响应的实体
            HttpEntity httpEntity = httpResponse.getEntity();

            //获取请求状态码
            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if(httpEntity!=null){
                InputStream inputStream = httpEntity.getContent();
                String response= convertStreamToString(inputStream);
                Log.d(TAG,"请求状态码:"+statusCode+"\n请求结果:"+response);
                inputStream.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
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
