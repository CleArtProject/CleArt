package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.AttrReport;

import java.util.List;

public class AttrReportAdapter extends RecyclerView.Adapter<AttrReportAdapter.AttrReportViewHolder> {

    private Context mCtx;
    private List<AttrReport> attrReportList;

    public AttrReportAdapter(Context mCtx, List<AttrReport> attrReportList) {
        this.mCtx = mCtx;
        this.attrReportList = attrReportList;
    }

    @Override
    public AttrReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.attrreport_view,parent,false);
        return new AttrReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrReportViewHolder holder, int position) {
        AttrReport attrReport = attrReportList.get(position);

        holder.tvUsername.setText(attrReport.getUsername());
        holder.tvSpecific.setText(attrReport.getSpecifics());
        holder.tvIssues.setText(attrReport.getIssue());
        holder.tvStatus.setText(attrReport.getStatus());
        holder.tvDate.setText(attrReport.getDatecreated());

    }

    @Override
    public int getItemCount() {
        return attrReportList.size();
    }

    public class AttrReportViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvSpecific,tvIssues,tvStatus,tvDate;

        public AttrReportViewHolder(View itemView) {
            super(itemView);

            tvUsername =  itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
