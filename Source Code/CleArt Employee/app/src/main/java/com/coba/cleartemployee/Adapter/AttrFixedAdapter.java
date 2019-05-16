package com.coba.cleartemployee.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.coba.cleartemployee.R;
import com.coba.cleartemployee.models.AttrFixed;

import java.util.List;

public class AttrFixedAdapter extends RecyclerView.Adapter<AttrFixedAdapter.AttrFixedViewHolder> {

    private Context mCtx;
    private List<AttrFixed> attrFixedList;
    Dialog myDialog;

    public AttrFixedAdapter(Context mCtx, List<AttrFixed> attrFixedList) {
        this.mCtx = mCtx;
        this.attrFixedList = attrFixedList;
    }

    @Override
    public AttrFixedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.historyreport_view,parent,false);

        AttrFixedViewHolder vHolder = new AttrFixedViewHolder(view);

        myDialog = new Dialog(mCtx);
        myDialog.setContentView(R.layout.detailfixedreport_popup);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(final AttrFixedViewHolder vHolder, int position) {
        AttrFixed attrFixed = attrFixedList.get(position);

        vHolder.tvUsername.setText(attrFixed.getUsername());
        vHolder.tvSpecific.setText(attrFixed.getSpecifics());
        vHolder.tvIssues.setText(attrFixed.getIssue());
        vHolder.tvStatus.setText(attrFixed.getStatus());
        vHolder.tvDate.setText(attrFixed.getDatecreated());

        vHolder.historyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvUsername = (TextView) myDialog.findViewById(R.id.tvAttrName);
                TextView tvSpecific = (TextView) myDialog.findViewById(R.id.tvSpecific);
                TextView tvIssues = (TextView) myDialog.findViewById(R.id.tvIssues);
                TextView tvDate = (TextView) myDialog.findViewById(R.id.tvDate);
                TextView tvStatus = (TextView) myDialog.findViewById(R.id.tvStatus);
                final Button btnClose = (Button) myDialog.findViewById(R.id.buttonClose);

                tvUsername.setText(attrFixedList.get(vHolder.getAdapterPosition()).getUsername());
                tvSpecific.setText(attrFixedList.get(vHolder.getAdapterPosition()).getSpecifics());
                tvIssues.setText(attrFixedList.get(vHolder.getAdapterPosition()).getIssue());
                tvDate.setText(attrFixedList.get(vHolder.getAdapterPosition()).getDatecreated());
                tvStatus.setText(attrFixedList.get(vHolder.getAdapterPosition()).getStatus());
                myDialog.show();

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {

        return attrFixedList.size();
    }

    public class AttrFixedViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvSpecific,tvIssues,tvStatus,tvDate;
        ConstraintLayout historyItem;

        public AttrFixedViewHolder(View itemView) {
            super(itemView);

            historyItem = itemView.findViewById(R.id.historyItem);
            tvUsername =  itemView.findViewById(R.id.tvAttrName);
            tvSpecific = itemView.findViewById(R.id.tvSpecific);
            tvIssues = itemView.findViewById(R.id.tvIssues);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
