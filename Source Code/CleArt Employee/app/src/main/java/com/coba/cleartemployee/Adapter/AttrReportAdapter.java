package com.coba.cleartemployee.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coba.cleartemployee.API.retrofitClient;
import com.coba.cleartemployee.R;
import com.coba.cleartemployee.models.AttrReport;
import com.coba.cleartemployee.models.LoginResponse;
import com.coba.cleartemployee.models.UpdateResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AttrReportAdapter extends RecyclerView.Adapter<AttrReportAdapter.AttrReportViewHolder> {

    private Context mCtx;
    private List<AttrReport> attrReportList;
    Dialog myDialog;
    int empid;

    public AttrReportAdapter(Context mCtx, List<AttrReport> attrReportList, int empid) {
        this.mCtx = mCtx;
        this.attrReportList = attrReportList;
        this.empid = empid;
    }

    @Override
    public AttrReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.userreport_view,parent,false);

        AttrReportViewHolder vHolder = new AttrReportViewHolder(view);

        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.detailreport_popup);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(final AttrReportViewHolder vHolder, int position) {
        final AttrReport attrReport = attrReportList.get(position);

        vHolder.tvUsername.setText(attrReport.getUsername());
        vHolder.tvSpecific.setText(attrReport.getSpecifics());
        vHolder.tvIssues.setText(attrReport.getIssue());
        vHolder.tvStatus.setText(attrReport.getStatus());
        vHolder.tvDate.setText(attrReport.getDatecreated());

        vHolder.reportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvUsername = (TextView) myDialog.findViewById(R.id.tvAttrName);
                TextView tvSpecific = (TextView) myDialog.findViewById(R.id.tvSpecific);
                TextView tvIssues = (TextView) myDialog.findViewById(R.id.tvIssues);
                TextView tvDate = (TextView) myDialog.findViewById(R.id.tvDate);
                TextView tvStatus = (TextView) myDialog.findViewById(R.id.tvStatus);
                final Button btnFix = (Button) myDialog.findViewById(R.id.buttonFix);

                tvUsername.setText(attrReportList.get(vHolder.getAdapterPosition()).getUsername());
                tvSpecific.setText(attrReportList.get(vHolder.getAdapterPosition()).getSpecifics());
                tvIssues.setText(attrReportList.get(vHolder.getAdapterPosition()).getIssue());
                tvDate.setText(attrReportList.get(vHolder.getAdapterPosition()).getDatecreated());
                tvStatus.setText(attrReportList.get(vHolder.getAdapterPosition()).getStatus());
                myDialog.show();

                btnFix.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mCtx, "Issue Resolved",Toast.LENGTH_SHORT).show();
                        Call<UpdateResponse> call = retrofitClient
                                .getInstance()
                                .getApi()
                                .updateReport(attrReport.getReportid(),empid);
                        Log.d("", "onClick: "+attrReport.getReportid() + "" + empid);

                        call.enqueue(new Callback<UpdateResponse>() {
                            @Override
                            public void onResponse(Call<UpdateResponse> call, Response<UpdateResponse> response) {
                                UpdateResponse updateResponse = response.body();
                                btnFix.setVisibility(View.GONE);
                            }

                            @Override
                            public void onFailure(Call<UpdateResponse> call, Throwable t) {

                            }
                        });
                    }
                });

            }
        });



    }

    @Override
    public int getItemCount() {
        return attrReportList.size();
    }

    public class AttrReportViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvSpecific,tvIssues,tvStatus,tvDate;
        ConstraintLayout reportItem;

        public AttrReportViewHolder(View itemView) {
            super(itemView);

            reportItem = (ConstraintLayout) itemView.findViewById(R.id.reportItem);
            tvUsername =  itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
