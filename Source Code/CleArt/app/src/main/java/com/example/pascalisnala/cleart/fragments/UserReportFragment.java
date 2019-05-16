package com.example.pascalisnala.cleart.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.UserReportAdapter;
import com.example.pascalisnala.cleart.adapter.UserReviewAdapter;
import com.example.pascalisnala.cleart.models.Report;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.UserReport;
import com.example.pascalisnala.cleart.responses.UserReportResponse;
import com.example.pascalisnala.cleart.models.UserReview;
import com.example.pascalisnala.cleart.responses.UserReviewResponse;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReportFragment extends Fragment {
    private RecyclerView rvUserReport;

    private ProgressBar pgUserReport;

    private UserReportAdapter reportAdapter;

    private ArrayList<Report> userReports;

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
        rvUserReport.setNestedScrollingEnabled(false);
        rvUserReport.setHasFixedSize(true);

        pgUserReport = view.findViewById(R.id.pgUserReport);

        final User user = SharedPrefManager
                .getInstance(getActivity())
                .getUser();


        Call<ArrayList<Report>> callReport = retrofitClient
                .getInstance()
                .getApi()
                .getReport(null,user.getUserid());

        callReport.enqueue(new Callback<ArrayList<Report>>() {
            @Override
            public void onResponse(Call<ArrayList<Report>> call, Response<ArrayList<Report>> response) {
                pgUserReport.setVisibility(View.GONE);
                userReports = response.body();
                reportAdapter = new UserReportAdapter(getActivity(),userReports);
                rvUserReport.setAdapter(reportAdapter);
                reportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<Report>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
