package com.example.animalkingdom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animalkingdom.R;
import com.example.animalkingdom.helper.GetMask;
import com.example.animalkingdom.model.Ads;
import com.example.animalkingdom.model.Doctor;
import com.squareup.picasso.Picasso;

import java.nio.channels.DatagramChannel;
import java.util.List;

public class AdapterDoctor extends RecyclerView.Adapter<AdapterDoctor.MyViewHolder> {

    private List<Doctor> doctorList;
    private OnClickListener onClickListener;
    public AdapterDoctor(List<Doctor> doctorList, OnClickListener onClickListener) {
        this.doctorList = doctorList;
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
        Doctor doctor = doctorList.get(position);
        Picasso.get().load(doctor.getImageUrls().get(0)).into(holder.img_ads);
        holder.text_title.setText(doctor.getFirstName());
        holder.text_value.setText(doctor.getSpecification());
        holder.text_local.setText(doctor.getContact());
        holder.itemView.setOnClickListener(view -> onClickListener.OnClick(doctor));
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public interface OnClickListener{
        void OnClick(Doctor doctor);
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_ads;
        TextView text_title;
        TextView text_value;
        TextView text_local;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_ads = itemView.findViewById(R.id.img_ads);
            text_title = itemView.findViewById(R.id.text_title);
            text_value = itemView.findViewById(R.id.text_value);
            text_local = itemView.findViewById(R.id.text_local);
        }
    }
}
