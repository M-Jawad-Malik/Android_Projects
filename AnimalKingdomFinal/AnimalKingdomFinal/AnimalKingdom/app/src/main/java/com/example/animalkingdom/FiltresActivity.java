package com.example.animalkingdom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.animalkingdom.helper.SPFiltre;
import com.example.animalkingdom.model.Category;
import com.example.animalkingdom.model.Filter;

import java.util.Locale;

public class FiltresActivity extends AppCompatActivity {

	private Button btn_regions;
	private Button btn_province;
	private Button btn_category;

	private CurrencyEditText edt_valor_min;
	private CurrencyEditText edt_valor_max;

	private final int REQUEST_CATEGORIA = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filtres);

		initializeComponents();

		configClicks();
	}

	@Override
	protected void onStart() {
		super.onStart();
		configFilters();
	}

	private void configFilters() {
		Filter filtre = SPFiltre.getFiltre(this);
		if (!filtre.getProvince().getFullname_province().isEmpty()) {
			btn_province.setText(filtre.getProvince().getFullname_province());
			btn_regions.setVisibility(View.VISIBLE);
		} else {
			btn_province.setText("All Provinces");
			btn_regions.setVisibility(View.GONE);
		}

		if (!filtre.getCategory().isEmpty()) {
			btn_category.setText(filtre.getCategory());
		} else {
			btn_category.setText("All Catgories");
		}

		if (!filtre.getProvince().getRegion().isEmpty()) {
			btn_regions.setText(filtre.getProvince().getRegion());
		} else {
			btn_regions.setText("All Regions");
		}

		if (filtre.getValueMin() > 0){
			edt_valor_min.setValue(filtre.getValueMin() * 100);
		}else{
			edt_valor_min.setValue(0);
		}
		if (filtre.getValueMax() > 0){
			edt_valor_min.setValue(filtre.getValueMin() * 100);
		}else{
			edt_valor_max.setValue(0);
		}
	}

	private void configClicks() {
		findViewById(R.id.btn_clear).setOnClickListener(v -> {
			SPFiltre.ClearFilters(this);
			finish();
		});
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());

		btn_regions.setOnClickListener(v -> {
			Intent intent = new Intent(this, RegionActivity.class);
			intent.putExtra("filtres", true);
			startActivity(intent);
		});
		btn_province.setOnClickListener(v -> {
			Intent intent = new Intent(this, ProvinceActivity.class);
			intent.putExtra("filtres", true);
			startActivity(intent);
		});
		btn_category.setOnClickListener(v -> {
			Intent intent = new Intent(this, CategoriesActivity.class);
			startActivityForResult(intent, REQUEST_CATEGORIA);
		});
		findViewById(R.id.btn_filtrar).setOnClickListener(v -> {
			getValues();
			finish();
		});
	}

	private void getValues(){
		String minValue = String.valueOf(edt_valor_min.getRawValue() / 100);
		String maxValue = String.valueOf(edt_valor_max.getRawValue() / 100);

		SPFiltre.setFiltre(this, "minValue", minValue);
		SPFiltre.setFiltre(this, "maxValue", maxValue);
	}

	private void initializeComponents() {
		btn_regions = findViewById(R.id.btn_regioes);
		btn_province = findViewById(R.id.btn_estados);
		btn_category = findViewById(R.id.btn_categoria);

		edt_valor_min = findViewById(R.id.edt_valor_min);
		edt_valor_max = findViewById(R.id.edt_valor_max);

		edt_valor_min.setLocale(new Locale("en", "PK"));
		edt_valor_max.setLocale(new Locale("en", "PK"));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CATEGORIA) {
				Category selectedCategory = (Category) data.getExtras().getSerializable("selectedCategory");
				SPFiltre.setFiltre(this, "category", selectedCategory.getName());

				configFilters();
			}
		}
	}
}