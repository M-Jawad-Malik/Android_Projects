package com.example.animalkingdom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animalkingdom.adapter.AdapterCategory;
import com.example.animalkingdom.helper.CategoryList;
import com.example.animalkingdom.model.Category;

public class CategoriesActivity extends AppCompatActivity implements AdapterCategory.OnClickListener {

	private RecyclerView rv_categories;
	private AdapterCategory adapterCategory;
	private boolean allCategories = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		initializeComponentes();

		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			allCategories = (Boolean) bundle.getSerializable("all");
		}

		configClicks();
		InitializeRV();
	}

	private void InitializeRV(){
		rv_categories.setLayoutManager(new LinearLayoutManager(this));
		rv_categories.setHasFixedSize(true);
		adapterCategory = new AdapterCategory(CategoryList.getList(allCategories), this);
		rv_categories.setAdapter(adapterCategory);
	}

	private void configClicks(){
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	private void initializeComponentes(){
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Categories");

		rv_categories = findViewById(R.id.rv_categorias);
	}

	@Override
	public void OnClick(Category category) {
		Intent intent = new Intent();
		intent.putExtra("selectedCategory", category);
		setResult(RESULT_OK, intent);
		finish();
	}
}