package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.AttrReview;

import java.util.List;

public class AttrReviewAdapter extends RecyclerView.Adapter<AttrReviewAdapter.AttrReviewViewHolder> {

    private Context mCtx;
    private List<AttrReview> attrReviewList;

    public AttrReviewAdapter(Context mCtx, List<AttrReview> attrReviewList) {
        this.mCtx = mCtx;
        this.attrReviewList = attrReviewList;
    }

    @Override
    public AttrReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = LayoutInflater.from(mCtx).inflate(R.layout.attrreview_view,parent,false);
        return new AttrReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrReviewViewHolder holder, int position) {
        final AttrReview attrreview = attrReviewList.get(position);

        holder.tvUsername.setText(attrreview.getUsername());
        holder.tvRating.setText(String.valueOf(attrreview.getRating()));
        holder.tvReview.setText(attrreview.getReview());
        holder.tvDate.setText(attrreview.getDatecreated());

    }

    @Override
    public int getItemCount() {
        return attrReviewList.size();
    }

    class AttrReviewViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvRating,tvReview,tvDate;

        public AttrReviewViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvAttrName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvReview = itemView.findViewById(R.id.tvReview);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }

}
