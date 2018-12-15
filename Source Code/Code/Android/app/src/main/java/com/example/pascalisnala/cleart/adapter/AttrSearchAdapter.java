package com.example.pascalisnala.cleart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pascalisnala.cleart.R;
import com.example.pascalisnala.cleart.models.Attraction;

import org.w3c.dom.Text;

import java.util.List;

public class AttrSearchAdapter extends RecyclerView.Adapter<AttrSearchAdapter.AttrSearchViewHolder> {

    private List<Attraction> attractions;
    private Context mCtx;

    public AttrSearchAdapter(List<Attraction> attractions, Context mCtx) {
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
    public void onBindViewHolder(AttrSearchViewHolder holder, int position) {
        holder.tvAttrName.setText(attractions.get(position).getAttrname());
        holder.tvAttrLoc.setText(attractions.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }

    public class AttrSearchViewHolder extends RecyclerView.ViewHolder{
        TextView tvAttrName, tvAttrLoc;

        public AttrSearchViewHolder(View itemView) {
            super(itemView);
            tvAttrName = itemView.findViewById(R.id.tvAttrName);
            tvAttrLoc = itemView.findViewById(R.id.tvAttrLoc);
        }
    }

}
