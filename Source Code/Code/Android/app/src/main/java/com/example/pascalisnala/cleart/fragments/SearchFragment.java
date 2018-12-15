package com.example.pascalisnala.cleart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.Activities.SearchActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttrSearchAdapter;
import com.example.pascalisnala.cleart.models.Attraction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    RecyclerView rvSearch;
    AttrSearchAdapter adapter;
    List<Attraction> attractions;
    ProgressBar pgSearch;
    TextView tvSearch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pgSearch = view.findViewById(R.id.pgSearch);
        rvSearch = view.findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSearch.setHasFixedSize(true);

        tvSearch = view.findViewById(R.id.tvSearch);

        fetchResult("");

        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    public void fetchResult (String key){
        Call<List<Attraction>> call = retrofitClient
                .getInstance()
                .getApi()
                .attrSearch(key);

        call.enqueue(new Callback<List<Attraction>>() {
            @Override
            public void onResponse(Call<List<Attraction>> call, Response<List<Attraction>> response) {
                pgSearch.setVisibility(View.GONE);
                attractions = response.body();
                adapter = new AttrSearchAdapter(attractions,getActivity());
                rvSearch.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                pgSearch.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error on: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
