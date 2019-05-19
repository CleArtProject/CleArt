package com.example.pascalisnala.cleart.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.Report;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomsheetDialog extends BottomSheetDialogFragment {

    TextView tvUsernameDialog,tvStatusDialog,tvReportDialog,tvSpecificDialog,tvCommentDialog;
    ImageView ivDialog;
    View viewDialog;
    ProgressBar pgDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int reportid = getArguments().getInt("reportid",-1);

        View v = inflater.inflate(R.layout.dialog_layout,container,false);
//        viewDialog = v.findViewById(R.id.viewDialog);
//        pgDialog = v.findViewById(R.id.pgDialog);
//        viewDialog.setVisibility(View.VISIBLE);
//        pgDialog.setVisibility(View.VISIBLE);

        tvUsernameDialog = v.findViewById(R.id.tvUsernameDialog);
        tvStatusDialog = v.findViewById(R.id.tvStatusDialog);
        tvReportDialog = v.findViewById(R.id.tvReportDialog);
        tvSpecificDialog = v.findViewById(R.id.tvSpecificDialog);
        tvCommentDialog = v.findViewById(R.id.tvCommentDialog);
        ivDialog = v.findViewById(R.id.ivDialog);

        Call<Report> call = retrofitClient
                .getInstance()
                .getApi()
                .getReport(reportid);

        call.enqueue(new Callback<Report>() {
            @Override
            public void onResponse(Call<Report> call, Response<Report> response) {
//                viewDialog.setVisibility(View.GONE);
//                pgDialog.setVisibility(View.GONE);
                Report report = response.body();
                tvUsernameDialog.setText(report.getUser().getUsername());
                tvStatusDialog.setText("Status: "+report.getStatus());
                tvReportDialog.setText(report.getIssue());
                tvSpecificDialog.setText(report.getSpecifics());
                tvCommentDialog.setText(report.getComment());
                if(report.getImage().size()>0){
                    String url = retrofitClient.BASE_URL+"/uploads/report_images/"+report.getImage().get(0);
                    Log.d("url_test", "onResponse: "+url);
                    Picasso.get()
                            .load(url)
                            .fit()
                            .centerCrop()
                            .into(ivDialog);
                }
            }

            @Override
            public
            void onFailure(Call<Report> call, Throwable t) {
                viewDialog.setVisibility(View.GONE);
                pgDialog.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });



        return v;
    }
}
