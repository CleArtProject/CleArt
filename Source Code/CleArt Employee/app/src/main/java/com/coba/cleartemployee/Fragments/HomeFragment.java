package com.coba.cleartemployee.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coba.cleartemployee.API.retrofitClient;
import com.coba.cleartemployee.Adapter.AttrReportAdapter;
import com.coba.cleartemployee.R;
import com.coba.cleartemployee.Storage.SharedPrefManager;
import com.coba.cleartemployee.models.AttrReport;
import com.coba.cleartemployee.models.AttrReportResponse;
import com.coba.cleartemployee.models.Employee;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class HomeFragment extends Fragment {
    RecyclerView rvAttrReport;
    AttrReportAdapter adapter;
    List<AttrReport> attrReportList;
    private TextView tvWelcome;
    private ImageView imgDone;
    private int attrid;
    Employee employee;
    AttrReport attrReport;
    View view;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Integer attrid = this.getArguments().getInt("attrid",-1);
        final String empname = this.getArguments().getString("empname");
        final Integer empid = this.getArguments().getInt("empid", -1);
        final Integer reportid = this.getArguments().getInt("reportid", -1);


//        Bundle bundle = new Bundle();
//        bundle.putInt("attrid",employee.getAttrid());
//        bundle.putString("empname",employee.getName());
//        bundle.putInt("empid", employee.getEmpid());
//        bundle.putInt("reportid", attrReport.getReportid());

        rvAttrReport = view.findViewById(R.id.rvUserReport);
        tvWelcome = view.findViewById(R.id.tvWelcome);
        rvAttrReport.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttrReport.setHasFixedSize(true);



        tvWelcome.setText("Welcome, " + empname + "!");



        Call<AttrReportResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .loadattrReport(attrid);
        call.enqueue(new Callback<AttrReportResponse>() {
            @Override
            public void onResponse(Call<AttrReportResponse> call, Response<AttrReportResponse> response) {
                attrReportList = response.body().getAttrReports();
                adapter = new AttrReportAdapter(getActivity(),attrReportList, empid);
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

    }
}
