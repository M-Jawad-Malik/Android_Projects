package com.example.resortingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import resortingapp.R;

public class RegionActivity extends AppCompatActivity implements AdapterRegion.OnClickListener {

	private AdapterRegion adapterRegion;
	private RecyclerView rv_regions;
	private boolean acesso = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_regioes);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			acesso = bundle.getBoolean("filtres");
		}
		iniciaComponentes();
		//configCliques();
		configRV();
		Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
		window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
	}

	private void configRV() {
		rv_regions.setLayoutManager(new LinearLayoutManager(this));
		rv_regions.setHasFixedSize(true);
		adapterRegion = new AdapterRegion(RegionsList.getList(SPFiltre.getFiltre(this).getProvince().getShortnm_province()), this);
		rv_regions.setAdapter(adapterRegion);
	}

//	//private void configCliques() {
//		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
//	}

	private void iniciaComponentes() {
		TextView text_toolbar = findViewById(R.id.toolbar_title);
		text_toolbar.setText("select region");
		rv_regions = findViewById(R.id.rv_regioes);
//		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	@Override
	public void OnClick(String regiao) {
		if (!regiao.equals("All Regions")){
			SPFiltre.setFiltre(this, "ddd", regiao);
			SPFiltre.setFiltre(this, "region", regiao);
			Toast.makeText(this, regiao+" Selected", Toast.LENGTH_SHORT).show();

		}else{
			SPFiltre.setFiltre(this, "ddd", "");
			SPFiltre.setFiltre(this, "region", "");
			Toast.makeText(this, "All Regions Selected", Toast.LENGTH_SHORT).show();
		}
		if (acesso){
			finish();
		}else{
			startActivity(new Intent(this, MainActivity.class));
		}
	}
}