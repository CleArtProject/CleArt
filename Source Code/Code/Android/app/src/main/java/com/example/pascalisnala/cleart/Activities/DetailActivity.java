package com.example.pascalisnala.cleart.Activities;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.adapter.ImageAdapter;
import com.example.pascalisnala.cleart.adapter.ViewPageAdapter;
import com.example.pascalisnala.cleart.fragments.FragmentInformation;
import com.example.pascalisnala.cleart.fragments.FragmentReport;

public class DetailActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    private ImageButton backButton;
    private CardView addReport;
    private TextView tvAttrName;
    private TextView tvRating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String attrname = null;
        String attrdesc = null;
        String attrloc = null;
        String attrschedule = null;
        String attrcategory = null;
        String attrrating = null;
        Integer attrid = -1;

        if(getIntent().hasExtra("attrid") && getIntent().hasExtra("attrname")&& getIntent().hasExtra("attrdesc")&& getIntent().hasExtra("attrloc")&& getIntent().hasExtra("attrschedule")){
            attrid = getIntent().getIntExtra("attrid",-1);
            attrname = getIntent().getStringExtra("attrname");
            attrdesc = getIntent().getStringExtra("attrdesc");
            attrloc = getIntent().getStringExtra("attrloc");
            attrschedule = getIntent().getStringExtra("attrschedule");
            attrcategory = getIntent().getStringExtra("attrcategory");
            attrrating = getIntent().getStringExtra("attrrating");
        }

        ViewPager viewPager = findViewById(R.id.viewPager_image);
        ImageAdapter adapter = new ImageAdapter(this);
        viewPager.setAdapter(adapter);

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
        tvRating.setText(attrrating);

        Bundle bundle = new Bundle();
        bundle.putInt("attrid",attrid);
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
}