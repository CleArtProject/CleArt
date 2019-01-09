package com.example.pascalisnala.cleart.API;

import com.example.pascalisnala.cleart.models.AttrReportResponse;
import com.example.pascalisnala.cleart.models.AttrReviewResponse;
import com.example.pascalisnala.cleart.models.Attraction;
import com.example.pascalisnala.cleart.models.HomeResponse;
import com.example.pascalisnala.cleart.models.UserReportResponse;
import com.example.pascalisnala.cleart.models.UserReviewResponse;
import com.example.pascalisnala.cleart.models.defaultResponse;
import com.example.pascalisnala.cleart.models.loginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("createuser")
    Call<defaultResponse> createUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phonenum") String phonenum,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<loginResponse> userLogin(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("loaddata/detail")
    Call<HomeResponse> getAttrData();

    @FormUrlEncoded
    @POST("newreport")
    Call<defaultResponse> newReport(
            @Field("userid") Integer userid,
            @Field("specific") String specific,
            @Field("issues") String issues,
            @Field("comment") String comment,
            @Field("attrid") Integer attrid
    );

    @FormUrlEncoded
    @POST("newreview")
    Call<defaultResponse> newReview(
            @Field("userid") Integer userid,
            @Field("rating") Integer rating,
            @Field("review") String review,
            @Field("attrid") Integer attrid
    );

    @FormUrlEncoded
    @POST("loadattrreview")
    Call<AttrReviewResponse> loadattrReview(
            @Field("attrid") Integer attrid
    );

    @FormUrlEncoded
    @POST("loadattrreport")
    Call<AttrReportResponse> loadattrReport(
            @Field("attrid") Integer attrid
    );

    @POST("attrsearch")
    Call <List<Attraction>> attrSearch(@Query("key")String keyword);

    @FormUrlEncoded
    @POST("loaduserreview")
    Call<UserReviewResponse> loaduserReview(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("loaduserreport")
    Call<UserReportResponse> loaduserReport(
            @Field("userid") int userid
    );

    @FormUrlEncoded
    @POST("updateuser")
    Call<loginResponse> updateUser(
            @Field("name") String name,
            @Field("username") String username,
            @Field("email") String email,
            @Field("phonenum") String phonenum,
            @Field("userid") int userid
    );


}
