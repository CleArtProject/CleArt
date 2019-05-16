package com.example.pascalisnala.cleart.API;

import com.example.pascalisnala.cleart.models.Report;
import com.example.pascalisnala.cleart.models.Review;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.responses.AttrReportResponse;
import com.example.pascalisnala.cleart.responses.AttrReviewResponse;
import com.example.pascalisnala.cleart.models.Attraction;
import com.example.pascalisnala.cleart.responses.HomeResponse;
import com.example.pascalisnala.cleart.responses.UserReportResponse;
import com.example.pascalisnala.cleart.responses.UserReviewResponse;
import com.example.pascalisnala.cleart.responses.defaultResponse;
import com.example.pascalisnala.cleart.responses.loginResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {

    //user
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

    //attraction
    @GET("attractions")
    Call<ArrayList<Attraction>> getAttrData();

    @GET("attractions/toprated")
    Call<ArrayList<Attraction>> getTopRatedAttr(
            @Query("limit")Integer limit
    );

    @GET("attractions/{id}")
    Call<Attraction> getAttr(
            @Path("id") int attrId
    );

    @GET("attractions/search")
    Call<ArrayList<Attraction>> attrSearch(
            @Query("query") String query
    );


    //review
//    @FormUrlEncoded
//    @POST("newreview")
//    Call<defaultResponse> newReview(
//            @Field("userid") Integer userid,
//            @Field("rating") Integer rating,
//            @Field("review") String review,
//            @Field("attrid") Integer attrid
//    );

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

    @Multipart
    @POST("report/new")
    Call<defaultResponse> newReport(
                    @Part MultipartBody.Part photo,
                    @PartMap Map<String, RequestBody> text);


    @Multipart
    @POST("review/new")
    Call<defaultResponse> newReview(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text);

    @GET("report")
    Call<ArrayList<Report>> getReport(
            @Query("attrid") Integer attrid,
            @Query("userid") Integer userid

    );

    @GET("review")
    Call<ArrayList<Review>> getReview(
            @Query("attrid") Integer attrid,
            @Query("userid") Integer userid

    );

    @GET("user/{id}")
    Call<User> getUser(
            @Path("id") int id
    );

    @GET("report/{id}")
    Call<Report> getReport(
            @Path("id") int id
    );

    @Multipart
    @POST("user/picture/upload")
    Call<defaultResponse> updateProfilePicture(
            @Part MultipartBody.Part photo,
            @PartMap Map<String, RequestBody> text);

    @FormUrlEncoded
    @POST("user/picture/delete")
    Call<defaultResponse> deleteProfilePicture(
            @Field("id") int id
    );

}
