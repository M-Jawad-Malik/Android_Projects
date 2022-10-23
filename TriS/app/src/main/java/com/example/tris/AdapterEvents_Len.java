package com.example.tris;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.Model.Ads;
import com.example.tris.Model.Event;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterEvents_Len extends RecyclerView.Adapter<AdapterEvents_Len.MyViewHolder> {

    private List<Event> eventList;
    private OnClickListener onClickListener;
    public AdapterEvents_Len(List<Event> eventList, OnClickListener onClickListener) {
        this.eventList = eventList;
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
        Event event = eventList.get(position);

        Picasso.get().load(event.getUrlImages().get(0)).into(holder.img_event);
        holder.text_title.setText(event.getTitle());
        holder.text_value.setText("Rs " + event.getPrice());
        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(event));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public interface OnClickListener{
        void OnClick(Event event);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_event;
        TextView text_title;
        TextView text_value;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_event = itemView.findViewById(R.id.img_ads);
            text_title = itemView.findViewById(R.id.text_title);
            text_value = itemView.findViewById(R.id.text_value);
        }
    }
}
