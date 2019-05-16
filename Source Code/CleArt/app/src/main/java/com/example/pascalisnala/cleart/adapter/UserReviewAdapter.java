package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.UserReview;

import java.util.List;

public class UserReviewAdapter extends RecyclerView.Adapter<UserReviewAdapter.UserReviewViewHolder> {
    private Context mCtx;
    private List<UserReview> userReviews;

    public UserReviewAdapter(Context mCtx, List<UserReview> userReviews) {
        this.mCtx = mCtx;
        this.userReviews = userReviews;
    }

    @Override
    public UserReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.userreview_view,parent,false);
        return new UserReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserReviewViewHolder holder, int position) {
        UserReview userReview = userReviews.get(position);
        holder.tvAttrName.setText(userReview.getAttrname());
        holder.tvReview.setText(userReview.getReview());
        holder.tvRating.setText(String.valueOf(userReview.getRating()));
        holder.tvDate.setText(userReview.getDatecreated());

    }

    @Override
    public int getItemCount() {
        return userReviews.size();
    }

    class UserReviewViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttrName,tvRating,tvReview,tvDate;

        public UserReviewViewHolder(View itemView) {
            super(itemView);

            tvAttrName = itemView.findViewById(R.id.tvAttrName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvReview = itemView.findViewById(R.id.tvReview);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
