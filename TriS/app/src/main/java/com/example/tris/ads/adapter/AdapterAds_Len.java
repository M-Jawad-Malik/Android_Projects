package com.example.tris.ads.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.R;
import com.example.tris.ads.model.Ads;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAds_Len extends RecyclerView.Adapter<AdapterAds_Len.MyViewHolder> {

    private List<Ads> adsList;
    private OnClickListener onClickListener;
    public AdapterAds_Len(List<Ads> adsList, OnClickListener onClickListener) {
        this.adsList = adsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adsitem1, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ads ads = adsList.get(position);

        Picasso.get().load(ads.getImageUrls().get(0)).into(holder.img_ads);
        holder.text_title.setText(ads.getTitle());
        holder.text_value.setText("Rs " + ads.getPrice());
        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(ads));
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public interface OnClickListener{
        void OnClick(Ads ads);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_ads;
        TextView text_title;
        TextView text_value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_ads = itemView.findViewById(R.id.img_ads);
            text_title = itemView.findViewById(R.id.text_title);
            text_value = itemView.findViewById(R.id.text_value);
        }
    }
}
