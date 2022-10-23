package com.example.animalkingdom.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalkingdom.R;
import com.example.animalkingdom.model.Province;

import java.util.List;

public class AdapterProvince extends RecyclerView.Adapter<AdapterProvince.MyViewHolder> {

	private List<Province> ProvinceList;
	private OnClickListener onClickListener;

	public AdapterProvince(List<Province> provinceList, OnClickListener onClickListener) {
		this.ProvinceList = provinceList;
		this.onClickListener = onClickListener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.estado_item, parent, false);
		return new MyViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Province province = ProvinceList.get(position);

		holder.text_state.setText(province.getFullname_province());

		holder.itemView.setOnClickListener(view -> onClickListener.OnClick(province));
	}

	@Override
	public int getItemCount() {
		return ProvinceList.size();
	}

	public interface OnClickListener {
		void OnClick(Province province);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder{

		TextView text_state;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);

			text_state = itemView.findViewById(R.id.text_state);
		}
	}

}
