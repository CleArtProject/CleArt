package com.example.pascalisnala.cleart.Activities;



import android.app.ActionBar;

import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttrSearchAdapter;
import com.example.pascalisnala.cleart.models.Attraction;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    RecyclerView rvSearch;
    AttrSearchAdapter adapter;
    List<Attraction> attractions;
    ProgressBar pgSearch;
    TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        pgSearch = findViewById(R.id.pgSearch);
        rvSearch = findViewById(R.id.rvSearch);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setHasFixedSize(true);

        tvSearch = findViewById(R.id.tvSearch);

        fetchResult("");
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
                adapter = new AttrSearchAdapter(attractions,SearchActivity.this);
                rvSearch.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Attraction>> call, Throwable t) {
                pgSearch.setVisibility(View.GONE);
                Toast.makeText(SearchActivity.this, "Error on: "+t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                fetchResult(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                fetchResult(s);
                return false;
            }
        });

        return true;
    }
}
