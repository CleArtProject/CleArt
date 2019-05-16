package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pascalisnala.cleart.API.retrofitClient;
import com.example.pascalisnala.cleart.Activities.DetailActivity;
import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.Attraction;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AttrSearchAdapter extends RecyclerView.Adapter<AttrSearchAdapter.AttrSearchViewHolder> {

    private ArrayList<Attraction> attractions;
    private Context mCtx;

    public AttrSearchAdapter(ArrayList<Attraction> attractions, Context mCtx) {
        this.attractions = attractions;
        this.mCtx = mCtx;
    }

    @Override
    public AttrSearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.attrsearch_view, parent, false);
        return new AttrSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AttrSearchViewHolder holder, final int position) {
        holder.tvAttrName.setText(attractions.get(position).getAttrname());
        holder.tvAttrLoc.setText(attractions.get(position).getLocation());
        if(attractions.get(position).getImage().size()>0){
            String url = retrofitClient.BASE_URL+"/uploads/"+attractions.get(position).getImage().get(0);
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(holder.imAttr);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mCtx, DetailActivity.class);
                intent.putExtra("attrid", attractions.get(position).getAttrid());
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public class AttrSearchViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttrName, tvAttrLoc;
        ImageView imAttr;
        ConstraintLayout layout;

        public AttrSearchViewHolder(View itemView) {
            super(itemView);
            tvAttrName = itemView.findViewById(R.id.tvAttrName);
            tvAttrLoc = itemView.findViewById(R.id.tvAttrLoc);
            imAttr = itemView.findViewById(R.id.imAttr);
            layout = itemView.findViewById(R.id.parentLayout);
        }
    }

}
