package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.User;
import com.example.pascalisnala.cleart.models.UserReport;
import com.example.pascalisnala.cleart.models.UserReview;

import java.util.List;
import java.util.zip.Inflater;

public class UserReportAdapter extends RecyclerView.Adapter<UserReportAdapter.UserReportViewHolder>{
    private Context mCtx;
    private List<UserReport> userReports;

    public UserReportAdapter(Context mCtx, List<UserReport> userReports) {
        this.mCtx = mCtx;
        this.userReports = userReports;
    }

    @Override
    public UserReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.userreport_view,parent,false);
        return new UserReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserReportViewHolder holder, int position) {
        UserReport userReport = userReports.get(position);
        holder.tvAttrName.setText(userReport.getAttrname());
        holder.tvStatus.setText(userReport.getStatus());
        holder.tvIssues.setText(userReport.getIssue());
        holder.tvSpecific.setText(userReport.getSpecifics());
        holder.tvDate.setText(userReport.getDatecreated());

    }

    @Override
    public int getItemCount() {
        return userReports.size();
    }

    public class UserReportViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttrName,tvSpecific,tvIssues,tvDate,tvStatus;

        public UserReportViewHolder(View itemView) {
            super(itemView);
            tvAttrName = itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }

}
