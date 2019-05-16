package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.AttrReport;
import com.example.pascalisnala.cleart.models.Report;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AttrReportAdapter extends RecyclerView.Adapter<AttrReportAdapter.AttrReportViewHolder> {

    private Context mCtx;
    private ArrayList<Report> reports;

    public AttrReportAdapter(Context mCtx, ArrayList<Report> reports) {
        this.mCtx = mCtx;
        this.reports = reports;
    }

    @Override
    public AttrReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.attrreport_view,parent,false);
        return new AttrReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrReportViewHolder holder, int position) {
        Report report = reports.get(position);

        final int reportid = report.getReportid();

        holder.tvUsername.setText(report.getUser().getUsername());
        holder.tvSpecific.setText(report.getSpecifics());
        holder.tvIssues.setText(report.getIssue());
        holder.tvStatus.setText(report.getStatus());
        holder.tvDate.setText(report.getDatecreated());

        if(report.getUser().getImage()!=null){
            String url = retrofitClient.BASE_URL+"/uploads/user_images/"+report.getUser().getImage();
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.ivAttrReport);
        }

        holder.cardReport.setOnClickListener(new View.OnClickListener() {
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

    public class AttrReportViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvSpecific,tvIssues,tvStatus,tvDate;
        ImageView ivAttrReport;
        CardView cardReport;

        public AttrReportViewHolder(View itemView) {
            super(itemView);

            tvUsername =  itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivAttrReport = itemView.findViewById(R.id.ivAttrReport);
            cardReport = itemView.findViewById(R.id.cardReport);

        }
    }
}
