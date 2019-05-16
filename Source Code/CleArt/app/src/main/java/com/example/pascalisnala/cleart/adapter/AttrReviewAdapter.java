package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.AttrReview;
import com.example.pascalisnala.cleart.models.Review;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AttrReviewAdapter extends RecyclerView.Adapter<AttrReviewAdapter.AttrReviewViewHolder> {

    private Context mCtx;
    private ArrayList<Review> reviews;

    public AttrReviewAdapter(Context mCtx, ArrayList<Review> reviews) {
        this.mCtx = mCtx;
        this.reviews = reviews;
    }

    @Override
    public AttrReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.attrreview_view,parent,false);
        return new AttrReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrReviewViewHolder holder, int position) {
        Review review = reviews.get(position);

        holder.tvUsername.setText(review.getUser().getUsername());
        holder.tvRating.setText(String.valueOf(review.getRating()));
        holder.tvReview.setText(review.getReview());
        holder.tvDate.setText(review.getDatecreated());

        if(review.getUser().getImage()!=null){
            String url = retrofitClient.BASE_URL+"/uploads/user_images/"+review.getUser().getImage();
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.ivProfilePicture);
        }

    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class AttrReviewViewHolder extends RecyclerView.ViewHolder{
        TextView tvUsername,tvRating,tvReview,tvDate;
        ImageView ivProfilePicture;

        public AttrReviewViewHolder(View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvAttrName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvReview = itemView.findViewById(R.id.tvReview);
            tvDate = itemView.findViewById(R.id.tvDate);
            ivProfilePicture = itemView.findViewById(R.id.ivProfilePicture);
        }
    }

}
