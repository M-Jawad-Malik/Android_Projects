package com.example.animalkingdom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalkingdom.adapter.AdapterRegion;
import com.example.animalkingdom.helper.RegionsList;
import com.example.animalkingdom.helper.SPFiltre;

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

		configCliques();
		configRV();
	}

	private void configRV() {
		rv_regions.setLayoutManager(new LinearLayoutManager(this));
		rv_regions.setHasFixedSize(true);
		adapterRegion = new AdapterRegion(RegionsList.getList(SPFiltre.getFiltre(this).getProvince().getShortnm_province()), this);
		rv_regions.setAdapter(adapterRegion);
	}

	private void configCliques() {
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	private void iniciaComponentes() {
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("select region");

		rv_regions = findViewById(R.id.rv_regioes);
	}

	@Override
	public void OnClick(String regiao) {
		if (!regiao.equals("All Regions")){
			SPFiltre.setFiltre(this, "ddd", regiao.substring(4, 6));
			SPFiltre.setFiltre(this, "region", regiao);
		}else{
			SPFiltre.setFiltre(this, "ddd", "");
			SPFiltre.setFiltre(this, "region", "");
		}
		if (acesso){
			finish();
		}else{
			startActivity(new Intent(this, Home.class));
		}
	}
}