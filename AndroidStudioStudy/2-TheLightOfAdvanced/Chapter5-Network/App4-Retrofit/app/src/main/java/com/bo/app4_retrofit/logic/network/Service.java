package com.bo.app4_retrofit.logic.network;

import com.bo.app4_retrofit.logic.model.IpModel;
import com.bo.app4_retrofit.logic.model.ResponseBase;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Service {
    @GET("{path}")
    Call<List<ResponseBase>> getResponses(@Path("path") String path);

    @GET("outGetIpInfo")
    Call<IpModel> getIP(@Query("ip") String ip, @Query("accessKey") String accessKey);

    @GET("outGetIpInfo")
    Call<IpModel> getIP(@QueryMap Map<String,String> options);

    @FormUrlEncoded
    @POST("outGetIpInfo")
    Call<IpModel> postIP(@Field("ip") String ip,@Field("accessKey") String accessKey);

    @POST("outGetIpInfo")
    Call<IpModel> postResponses(@Body RequestBody body);

    @Multipart
    @POST("")
    Call<Response> postResponses(@Part MultipartBody.Part photo, @Part("description") RequestBody description);
}
