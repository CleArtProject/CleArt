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
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttractionAdapter;
import com.example.pascalisnala.cleart.models.Attraction;
import com.example.pascalisnala.cleart.responses.HomeResponse;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.storage.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView rvAttr;
    AttractionAdapter adapter;
    ArrayList<Attraction> attrList;
    private TextView tvWelcome;

    ProgressBar pgHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pgHome = view.findViewById(R.id.pgHome);

        tvWelcome = view.findViewById(R.id.tvWelcome);
        rvAttr = view.findViewById(R.id.rvAttr);

        rvAttr.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttr.setHasFixedSize(true);
        rvAttr.setNestedScrollingEnabled(false);

        User user = SharedPrefManager
                .getInstance(getActivity())
                .getUser();

        tvWelcome.setText("Welcome, " + user.getName() + "!");

        Call<ArrayList<Attraction>> call = retrofitClient
                .getInstance()
                .getApi()
                .getAttrData();
        call.enqueue(new Callback<ArrayList<Attraction>>() {
            @Override
            public void onResponse(Call<ArrayList<Attraction>> call, Response<ArrayList<Attraction>> response) {
                pgHome.setVisibility(View.GONE);
                attrList = response.body();
                adapter = new AttractionAdapter(getActivity(), attrList);
                if (adapter != null) {
                    rvAttr.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Attraction>> call, Throwable t) {
                pgHome.setVisibility(View.GONE);
            }
        });
    }
}
