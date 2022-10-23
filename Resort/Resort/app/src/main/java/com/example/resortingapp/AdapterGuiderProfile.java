package com.example.resortingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import resortingapp.R;

public class AdapterGuiderProfile extends RecyclerView.Adapter<AdapterGuiderProfile.MyViewHolder> {

	private List<Guider> guiderList;
	private OnClickListener onClickListener;
	public AdapterGuiderProfile(List<Guider> guiderList, OnClickListener onClickListener) {
		this.guiderList = guiderList;
		this.onClickListener = onClickListener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guiderprofile, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Guider guider = guiderList.get(position);

		Picasso.get().load(guider.getUrlImagens().get(0)).into(holder.img_ads);
		holder.text_name.setText(guider.firstName.toString());
		holder.text_language.setText(guider.language.toString());
		holder.text_region.setText(guider.region.toString());


		holder.itemView.setOnClickListener(view -> onClickListener.OnClick(guider));
	}

	@Override
	public int getItemCount() {
		return guiderList.size();
	}

	public interface OnClickListener{

		void OnClick(Guider guider);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder {

		ImageView img_ads;
		TextView text_name;
		TextView text_contact;
		TextView text_language;
		TextView text_region;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			img_ads = itemView.findViewById(R.id.img_ads);
			text_name = itemView.findViewById(R.id.text_name);
			text_contact = itemView.findViewById(R.id.text_contact);
			text_language = itemView.findViewById(R.id.text_langauge);
			text_region = itemView.findViewById(R.id.text_region);
		}
	}
}
