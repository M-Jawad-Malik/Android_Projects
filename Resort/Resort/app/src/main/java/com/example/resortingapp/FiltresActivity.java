package com.example.resortingapp;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blackcat.currencyedittext.CurrencyEditText;
import java.util.Locale;
import resortingapp.R;
public class FiltresActivity extends AppCompatActivity {
	private Button btn_regions;
	private Button btn_province;
	private Button btn_category;
	private Button btn_season;
	private CurrencyEditText edt_valor_min;
	private CurrencyEditText edt_valor_max;
	private final int REQUEST_CATEGORIA = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acitivity_filters);
		initializeComponents();
		configClicks();
		Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
		window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
		//makeStatusbarTransparent();
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
		if (!filtre.getInterest().isEmpty()) {
			btn_category.setText(filtre.getInterest());
		} else {
			btn_category.setText("All Interest");
		}

		if (!filtre.getProvince().getRegion().isEmpty()) {
			btn_regions.setText(filtre.getProvince().getRegion());
		} else {
			btn_regions.setText("All Cities");
		}
		if (!filtre.getSeason().isEmpty()) {
			btn_season.setText(filtre.getSeason());
		} else {
			btn_season.setText("All Seasons");
		}
//		if (filtre.getValueMin() > 0){
//			edt_valor_min.setValue(filtre.getValueMin() * 100);
//		}else{
//			edt_valor_min.setValue(0);
//		}
//		if (filtre.getValueMax() > 0){
//			edt_valor_min.setValue(filtre.getValueMin() * 100);
//		}else{
//			edt_valor_max.setValue(0);
//		}
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
			Intent intent = new Intent(this, InterestActivity.class);
			startActivityForResult(intent, REQUEST_CATEGORIA);
		});
		findViewById(R.id.btn_filtrar).setOnClickListener(v -> {
			getValues();
			finish();
		});
		btn_season.setOnClickListener(v->{
			Intent intent = new Intent(this, SeasonActivity.class);
			intent.putExtra("filtres", true);
			startActivity(intent);
		});
	}
	private void getValues(){
//		String minValue = String.valueOf(edt_valor_min.getRawValue() / 100);
//		String maxValue = String.valueOf(edt_valor_max.getRawValue() / 100);

//		SPFiltre.setFiltre(this, "minValue", minValue);
//		SPFiltre.setFiltre(this, "maxValue", maxValue);
	}
	private void initializeComponents() {
		btn_regions = findViewById(R.id.btn_regioes);
		btn_province = findViewById(R.id.btn_estados);
		btn_category = findViewById(R.id.btn_categoria);
//		edt_valor_min.setLocale(new Locale("en", "PK"));
//		edt_valor_max.setLocale(new Locale("en", "PK"));
		btn_season=findViewById(R.id.btn_season);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CATEGORIA) {
				Interest selectedInterest = (Interest) data.getExtras().getSerializable("selectedInterest");
				SPFiltre.setFiltre(this, "interest", selectedInterest.getName());
				configFilters();
			}
		}
	}
	private void makeStatusbarTransparent() {

		if (Build.VERSION.SDK_INT >= 21) {
			getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.next_2));
		}
	}
}