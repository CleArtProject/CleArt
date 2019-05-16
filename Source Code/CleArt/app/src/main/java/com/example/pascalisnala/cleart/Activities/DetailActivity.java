package com.example.pascalisnala.cleart.Activities;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.ImageAdapter;
import com.example.pascalisnala.cleart.adapter.ViewPageAdapter;
import com.example.pascalisnala.cleart.fragments.FragmentInformation;
import com.example.pascalisnala.cleart.fragments.FragmentReport;
import com.example.pascalisnala.cleart.models.Attraction;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ImageButton backButton;
    private CardView addReport;
    private TextView tvAttrName;
    private TextView tvRating;

    String attrname;
    String attrdesc;
    String attrloc;
    String attrschedule;
    String attrcategory;
    Float attrrating;
    ArrayList<String> image = new ArrayList<>();

    View view;
    ProgressBar pg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        view = findViewById(R.id.loading);
        view.setVisibility(View.VISIBLE);
        pg = findViewById(R.id.pgDetail);

        int attrid = -1;

        if(getIntent().hasExtra("attrid")){
            attrid = getIntent().getIntExtra("attrid",-1);
            Log.d("attrid", "onCreate: "+attrid);
        }

        Call<Attraction> call = retrofitClient
                .getInstance()
                .getApi()
                .getAttr(attrid);

        final int finalAttrid = attrid;
        call.enqueue(new Callback<Attraction>() {
            @Override
            public void onResponse(Call<Attraction> call, Response<Attraction> response) {
                view.setVisibility(View.GONE);
                pg.setVisibility(View.GONE);

                Attraction attraction = response.body();
                attrname = attraction.getAttrname();
                attrdesc = attraction.getPhonenumber();
                attrloc = attraction.getLocation();
                attrschedule = attraction.getSchedule();
                attrcategory = attraction.getCategory();
                attrrating = attraction.getRating();
                image = attraction.getImage();


                ViewPager viewPager = findViewById(R.id.viewPager_image);
                if(image.size()>0){
                    ImageAdapter adapter = new ImageAdapter(DetailActivity.this,image);
                    viewPager.setAdapter(adapter);
                }

                tabLayout = findViewById(R.id.tablayout_id);
                viewPager = findViewById(R.id.viewPager_id);
                ViewPageAdapter adapter1 = new ViewPageAdapter(getSupportFragmentManager());

                viewPager.setAdapter(adapter1);
                tabLayout.setupWithViewPager(viewPager);

                backButton = findViewById(R.id.backButton);
                backButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                tvAttrName = findViewById(R.id.tvAttrName);
                tvAttrName.setText(attrname);

                tvRating = findViewById(R.id.tvRating);
                tvRating.setText(String.format("%.1f", attrrating));

                Bundle bundle = new Bundle();
                bundle.putInt("attrid", finalAttrid);
                bundle.putString("attrname",attrname);
                bundle.putString("attrdesc",attrdesc);
                bundle.putString("attrloc",attrloc);
                bundle.putString("attrschedule",attrschedule);
                bundle.putString("attrcategory",attrcategory);

                FragmentInformation fragmentInformation = new FragmentInformation();
                fragmentInformation.setArguments(bundle);

                FragmentReport fragmentReport = new FragmentReport();
                fragmentReport.setArguments(bundle);


                adapter1.AddFragment(fragmentInformation,"Information");
                adapter1.AddFragment(fragmentReport,"Report");

                viewPager.setAdapter(adapter1);
                tabLayout.setupWithViewPager(viewPager);
            }

            @Override
            public void onFailure(Call<Attraction> call, Throwable t) {
                Log.d("detail", "onFailure: " + t.getMessage());
            }
        });

    }
}