package com.example.pascalisnala.cleart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.Activities.ReportActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttrReportAdapter;
import com.example.pascalisnala.cleart.models.AttrReport;
import com.example.pascalisnala.cleart.models.AttrReportResponse;
import com.example.pascalisnala.cleart.models.AttrReviewResponse;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReport extends Fragment {
    RecyclerView rvAttrReport;
    AttrReportAdapter adapter;
    List<AttrReport> attrReportList;
    View view;

    public FragmentReport() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.report_fragment, container, false);
        return view;
    }

    private CardView addReport;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addReport = view.findViewById(R.id.addReport);

        rvAttrReport = view.findViewById(R.id.rvAttrReport);
        rvAttrReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttrReport.setHasFixedSize(true);

        final Integer attrid = this.getArguments().getInt("attrid",-1);
        final String attrname = this.getArguments().getString("attrname");
        final String attrdesc = this.getArguments().getString("attrdesc");
        final String attrloc = this.getArguments().getString("attrloc");
        final String attrschedule = this.getArguments().getString("attrschedule");

        Call<AttrReportResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .loadattrReport(attrid);
        call.enqueue(new Callback<AttrReportResponse>() {
            @Override
            public void onResponse(Call<AttrReportResponse> call, Response<AttrReportResponse> response) {
                attrReportList = response.body().getAttrReports();
                adapter = new AttrReportAdapter(getActivity(),attrReportList);
                if (adapter != null) {
                    rvAttrReport.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<AttrReportResponse> call, Throwable t) {

            }
        });
        addReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReportActivity.class);
                i.putExtra("attrid",attrid);
                i.putExtra("attrname",attrname);
                i.putExtra("attrdesc",attrdesc);
                i.putExtra("attrloc", attrloc);
                i.putExtra("attrschedule",attrschedule);
                startActivity(i);
            }
        });
    }

}