package com.example.tris;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.Model.Category;

import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.MyViewHolder> {

	private List<Category> categoryList;
	private OnClickListener onClickListener;

	public AdapterCategory(List<Category> categoryList, OnClickListener onClickListener) {
		this.categoryList = categoryList;
		this.onClickListener = onClickListener;
	}

	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
		return new MyViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		Category category = categoryList.get(position);

		holder.img_catgory.setImageResource(category.getPath());
		holder.txt_catgory.setText(category.getName());

		holder.itemView.setOnClickListener(v -> onClickListener.OnClick(category));
	}

	@Override
	public int getItemCount() {
		return categoryList.size();
	}

	public interface OnClickListener{
		void OnClick(Category category);
	}

	static class MyViewHolder extends RecyclerView.ViewHolder{

		ImageView img_catgory;
		TextView txt_catgory;

		public MyViewHolder(@NonNull View itemView) {
			super(itemView);
			img_catgory = itemView.findViewById(R.id.img_category);
			txt_catgory = itemView.findViewById(R.id.txt_category);
		}
	}

}
