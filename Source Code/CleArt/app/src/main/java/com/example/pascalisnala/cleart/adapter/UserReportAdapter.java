package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.Report;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.UserReport;
import com.example.pascalisnala.cleart.models.UserReview;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class UserReportAdapter extends RecyclerView.Adapter<UserReportAdapter.UserReportViewHolder>{
    private Context mCtx;
    private ArrayList<Report> reports;

    public UserReportAdapter(Context mCtx, ArrayList<Report> reports) {
        this.mCtx = mCtx;
        this.reports = reports;
    }

    @Override
    public UserReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.userreport_view,parent,false);
        return new UserReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserReportViewHolder holder, int position) {
        Report userReport = reports.get(position);
        final int reportid = userReport.getReportid();
        holder.tvAttrName.setText(userReport.getAttraction().getAttrname());
        holder.tvStatus.setText(userReport.getStatus());
        holder.tvIssues.setText(userReport.getIssue());
        holder.tvSpecific.setText(userReport.getSpecifics());
        holder.tvDate.setText(userReport.getDatecreated());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("reportid",reportid);
                BottomSheetDialogFragment bottomSheetDialogFragment = new BottomsheetDialog();
                bottomSheetDialogFragment.setArguments(bundle);
                bottomSheetDialogFragment.show(((FragmentActivity)mCtx).getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });



    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public class UserReportViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttrName,tvSpecific,tvIssues,tvDate,tvStatus;
        ConstraintLayout layout;

        public UserReportViewHolder(View itemView) {
            super(itemView);
            tvAttrName = itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            layout = itemView.findViewById(R.id.layoutUserReport);
        }
    }

}
