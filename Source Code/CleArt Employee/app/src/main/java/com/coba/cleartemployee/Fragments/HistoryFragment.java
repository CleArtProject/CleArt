package com.coba.cleartemployee.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coba.cleartemployee.API.retrofitClient;
import com.coba.cleartemployee.Adapter.AttrFixedAdapter;
import com.coba.cleartemployee.R;
import com.coba.cleartemployee.models.AttrFixed;
import com.coba.cleartemployee.models.AttrFixedResponse;
import com.coba.cleartemployee.models.AttrReport;

import org.w3c.dom.Attr;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment {

    RecyclerView rvHistory;
    AttrFixedAdapter adapter;
    List<AttrFixed> attrFixedList;
    View view;

    public HistoryFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment,container,false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Integer attrid = this.getArguments().getInt("attrid",-1);
        final String empname = this.getArguments().getString("empname");
        final Integer empid = this.getArguments().getInt("empid", -1);
        final Integer reportid = this.getArguments().getInt("reportid", -1);

        rvHistory = view.findViewById(R.id.rvFinishedUserReport);
        rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHistory.setHasFixedSize(true);

        Call<AttrFixedResponse> call = retrofitClient
                .getInstance()
                .getApi()
                .loadFixedReport(attrid);
        call.enqueue(new Callback<AttrFixedResponse>() {
            @Override
            public void onResponse(Call<AttrFixedResponse> call, Response<AttrFixedResponse> response) {
                attrFixedList = response.body().getAttrReports();
                adapter = new AttrFixedAdapter(getActivity(), attrFixedList);
                if(adapter!=null){
                    rvHistory.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AttrFixedResponse> call, Throwable t) {

            }
        });
    }
}
