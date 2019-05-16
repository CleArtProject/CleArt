package com.coba.cleartemployee.API;

import com.coba.cleartemployee.models.AttrFixedResponse;
import com.coba.cleartemployee.models.AttrReportResponse;
import com.coba.cleartemployee.models.LoginResponse;
import com.coba.cleartemployee.models.UpdateResponse;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface API {

    @FormUrlEncoded
    @POST("employeelogin")
    Call<LoginResponse> employeeLogin(
            @Field("username") String username,
            @Field("password") String password
    );


   @FormUrlEncoded
    @POST("loadempattrreport")
    Call<AttrReportResponse> loadattrReport(
            @Field("attrid") Integer attrid
    );


    @FormUrlEncoded
    @POST("loadfixedreport")
    Call<AttrFixedResponse> loadFixedReport(
            @Field("attrid") int attrid
    );

    @FormUrlEncoded
    @POST("updatereport")
    Call<UpdateResponse> updateReport(
            @Field("reportid") int reportid,
            @Field("empid") int empid
    );

}
