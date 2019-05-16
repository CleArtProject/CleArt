package com.example.pascalisnala.cleart.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.Activities.ReportActivity;
import com.example.pascalisnala.cleart.Activities.ReviewActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.AttrReviewAdapter;
import com.example.pascalisnala.cleart.models.AttrReview;
import com.example.pascalisnala.cleart.models.Review;
import com.example.pascalisnala.cleart.responses.AttrReviewResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentInformation extends Fragment {
    RecyclerView rvAttrReview;
    AttrReviewAdapter adapter;
    ArrayList<Review> reviews;
    View view;

    public FragmentInformation(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        view = inflater.inflate(R.layout.information_fragment,container,false);

        CardView test = view.findViewById(R.id.cardReport);
        TextView containPhonenum = view.findViewById(R.id.containPhonenum);
        TextView containAddress = view.findViewById(R.id.containAddress);
        TextView containCategory = view.findViewById(R.id.containCategory);
        TextView containSchedule = view.findViewById(R.id.containSchedule);

        Integer attrid = this.getArguments().getInt("attrid",-1);
        String attrname = this.getArguments().getString("attrname");
        String attrdesc = this.getArguments().getString("attrdesc");
        String attrloc = this.getArguments().getString("attrloc");
        String attrschedule = this.getArguments().getString("attrschedule");
        String attrcategory = this.getArguments().getString("attrcategory");

        containPhonenum.setText(attrdesc);
        containAddress.setText(attrloc);
        containSchedule.setText(attrschedule);
        containCategory.setText(attrcategory);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment;
                fragment = new FragmentReport();
                displayFragment(fragment);
            }
        });

        return view;
    }

    private void displayFragment(Fragment fragment){
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.layoutInformation,fragment)
                .commit();
    }

    private CardView addReport;
    private CardView addReview;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvAttrReview = view.findViewById(R.id.rvAttrReview);
        rvAttrReview.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAttrReview.setHasFixedSize(true);

        addReport = view.findViewById(R.id.cardReport);
        addReview = view.findViewById(R.id.cardReview);

        final Integer attrid = this.getArguments().getInt("attrid",-1);
        final String attrname = this.getArguments().getString("attrname");
        final String attrdesc = this.getArguments().getString("attrdesc");
        final String attrloc = this.getArguments().getString("attrloc");
        final String attrschedule = this.getArguments().getString("attrschedule");
        final String attrcategory = this.getArguments().getString("attrcategory");

        Call<ArrayList<Review>> call = retrofitClient
                .getInstance()
                .getApi()
                .getReview(attrid,null);
       call.enqueue(new Callback<ArrayList<Review>>() {
           @Override
           public void onResponse(Call<ArrayList<Review>> call, Response<ArrayList<Review>> response) {
               reviews = response.body();
               if(reviews!=null){
                   adapter = new AttrReviewAdapter(getActivity(),reviews);
                   if (adapter != null) {
                       rvAttrReview.setAdapter(adapter);
                   } else {
                       Toast.makeText(getActivity(), "null", Toast.LENGTH_LONG).show();
                   }
               }

           }

           @Override
           public void onFailure(Call<ArrayList<Review>> call, Throwable t) {
               Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
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

        addReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ReviewActivity.class);
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
