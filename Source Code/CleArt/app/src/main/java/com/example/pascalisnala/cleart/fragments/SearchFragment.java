package com.example.pascalisnala.cleart.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttrSearchAdapter;
import com.example.pascalisnala.cleart.models.Attraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    RecyclerView rvSearch;
    AttrSearchAdapter adapter;
    List<Attraction> attractions;
    ProgressBar pgSearch;
    EditText etSearch;

    Timer timer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pgSearch = view.findViewById(R.id.pgSearch);
        pgSearch.setVisibility(View.GONE);
        rvSearch = view.findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSearch.setHasFixedSize(true);

        etSearch = view.findViewById(R.id.etSearch);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2 > 4){
                    new Handler().postDelayed(new Runnable(){
                        public void run(){
                            pgSearch.setVisibility(View.VISIBLE);
                            String query = etSearch.getText().toString().trim();
                            Call<ArrayList<Attraction>> call = retrofitClient
                                    .getInstance()
                                    .getApi()
                                    .attrSearch(query);
                            call.enqueue(new Callback<ArrayList<Attraction>>() {
                                @Override
                                public void onResponse(Call<ArrayList<Attraction>> call, Response<ArrayList<Attraction>> response) {
                                    pgSearch.setVisibility(View.GONE);
                                    if(response.body().size()>0){
                                        adapter = new AttrSearchAdapter(response.body(),getActivity());
                                        rvSearch.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ArrayList<Attraction>> call, Throwable t) {
                                    pgSearch.setVisibility(View.GONE);
                                    Log.d("attrsearch", "onFailure: "+t.getMessage());

                                }
                            });

                        }},700);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}
