package com.example.animalkingdom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.animalkingdom.R;

import java.util.List;

public class AdapterRegion extends RecyclerView.Adapter<AdapterRegion.MyViewHolder> {

	private List<String> regionsList;
	private OnClickListener onClickListener;

	public AdapterRegion(List<String> regionList, OnClickListener onClickListener) {
		this.regionsList = regionList;
		this.onClickListener = onClickListener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_item, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		String region = regionsList.get(position);

		holder.text_region.setText(region);

		holder.itemView.setOnClickListener(view -> onClickListener.OnClick(region));
	}

	@Override
	public int getItemCount() {
		return regionsList.size();
	}

	public interface OnClickListener {
		void OnClick(String region);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder{

		TextView text_region;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			text_region = itemView.findViewById(R.id.text_region);
		}
	}

}
