package com.example.pascalisnala.cleart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.UserReportAdapter;
import com.example.pascalisnala.cleart.adapter.UserReviewAdapter;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.UserReport;
import com.example.pascalisnala.cleart.models.UserReportResponse;
import com.example.pascalisnala.cleart.models.UserReview;
import com.example.pascalisnala.cleart.models.UserReviewResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class UserReportFragment extends Fragment {
    private RecyclerView rvUserReport, rvUserReview;

    private ProgressBar pgUserReview, pgUserReport;

    private UserReviewAdapter reviewAdapter;
    private UserReportAdapter reportAdapter;

    private List<UserReview> userReviews;
    private List<UserReport> userReports;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userreport_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvUserReport = view.findViewById(R.id.rvUserReport);
        rvUserReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUserReport.setHasFixedSize(true);

        rvUserReview = view.findViewById(R.id.rvUserReview);
        rvUserReview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUserReview.setHasFixedSize(true);

        pgUserReport = view.findViewById(R.id.pgUserReport);
        pgUserReview = view.findViewById(R.id.pgUserReview);

        final User user = SharedPrefManager
                .getInstance(getActivity())
                .getUser();


        Call<UserReviewResponse> callReview = retrofitClient
                .getInstance()
                .getApi()
                .loaduserReview(user.getUserid());

        callReview.enqueue(new Callback<UserReviewResponse>() {
            @Override
            public void onResponse(Call<UserReviewResponse> call, Response<UserReviewResponse> response) {
                pgUserReview.setVisibility(View.GONE);
                userReviews = response.body().getUserReviews();
                reviewAdapter = new UserReviewAdapter(getActivity(),userReviews);
                rvUserReview.setAdapter(reviewAdapter);
            }

            @Override
            public void onFailure(Call<UserReviewResponse> call, Throwable t) {
                pgUserReview.setVisibility(view.GONE);
            }
        });

        Call<UserReportResponse> callReport = retrofitClient
                .getInstance()
                .getApi()
                .loaduserReport(user.getUserid());

        callReport.enqueue(new Callback<UserReportResponse>() {
            @Override
            public void onResponse(Call<UserReportResponse> call, Response<UserReportResponse> response) {
                pgUserReport.setVisibility(View.GONE);
                userReports = response.body().getUserReports();
                reportAdapter = new UserReportAdapter(getActivity(),userReports);
                rvUserReport.setAdapter(reportAdapter);
                reportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<UserReportResponse> call, Throwable t) {

            }
        });
    }
}
