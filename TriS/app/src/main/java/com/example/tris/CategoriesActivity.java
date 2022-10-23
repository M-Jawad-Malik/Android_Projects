package com.example.tris;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.Model.AdsCategoryList;
import com.example.tris.Model.Category;
import com.example.tris.Model.EventCategoryList;

public class CategoriesActivity extends AppCompatActivity implements AdapterCategory.OnClickListener {

	private RecyclerView rv_categories;
	private AdapterCategory adapterCategory;
	private String allCategories = "";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_categories);
		initializeComponentes();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			allCategories =  bundle.getString("all");
				}
		configClicks();
		InitializeRV();
	}

	private void InitializeRV() {
		rv_categories.setLayoutManager(new LinearLayoutManager(this));
		rv_categories.setHasFixedSize(true);

		if (allCategories.contains("ads")) {
			adapterCategory = new AdapterCategory(AdsCategoryList.getList(), this);

		} else if(allCategories.contains("events"))
		{
			adapterCategory = new AdapterCategory(EventCategoryList.getList(), this);

		}
		rv_categories.setAdapter(adapterCategory);
	}

	private void configClicks(){
		//findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	private void initializeComponentes(){
		TextView text_toolbar = findViewById(R.id.toolbar_text);
		text_toolbar.setText("Categories");
		rv_categories = findViewById(R.id.rv_categorias);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void OnClick(Category category) {
		Intent intent = new Intent();
		intent.putExtra("selectedCategory", category);
		setResult(RESULT_OK, intent);
		finish();
	}
}