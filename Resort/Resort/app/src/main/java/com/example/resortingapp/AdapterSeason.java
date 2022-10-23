package com.example.resortingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import resortingapp.R;

public class AdapterSeason extends RecyclerView.Adapter<AdapterSeason.MyViewHolder> {

    private List<String> seasonList;
    private AdapterSeason.OnClickListener onClickListener;

    public AdapterSeason(List<String> regionList, AdapterSeason.OnClickListener onClickListener) {
        this.seasonList = regionList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AdapterSeason.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_item, parent, false);
        return new AdapterSeason.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterSeason.MyViewHolder holder, int position) {
        String region = seasonList.get(position);

        holder.text_season.setText(region);

        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(region));
    }

    @Override
    public int getItemCount() {
        return seasonList.size();
    }

    public interface OnClickListener {
        void OnClick(String region);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView text_season;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            text_season = itemView.findViewById(R.id.text_region);
        }
    }
}
