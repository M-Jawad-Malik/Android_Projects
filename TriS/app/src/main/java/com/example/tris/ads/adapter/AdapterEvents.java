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
import com.example.tris.ads.model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEvents extends RecyclerView.Adapter<AdapterEvents.MyViewHolder> {

    private List<Event> adsList;
    private OnClickListener onClickListener;
    public AdapterEvents(List<Event> adsList, OnClickListener onClickListener) {
        this.adsList = adsList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adsitem, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Event event = adsList.get(position);

        Picasso.get().load(event.getUrlImages().get(0)).into(holder.img_ads);
        holder.text_title.setText(event.getTitle());
        holder.text_value.setText("Rs " + event.getPrice());
        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(event));
    }

    @Override
    public int getItemCount() {
        return adsList.size();
    }

    public interface OnClickListener{
        void OnClick(Event ads);
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
