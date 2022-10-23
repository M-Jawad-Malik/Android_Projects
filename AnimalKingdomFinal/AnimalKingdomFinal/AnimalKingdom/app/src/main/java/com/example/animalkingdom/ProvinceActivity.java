package com.example.animalkingdom;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.animalkingdom.adapter.AdapterProvince;
import com.example.animalkingdom.helper.ProvinceList;
import com.example.animalkingdom.helper.SPFiltre;
import com.example.animalkingdom.model.Province;

public class ProvinceActivity extends AppCompatActivity implements AdapterProvince.OnClickListener {

	private RecyclerView rv_province;
	private AdapterProvince adapterProvince;

	private boolean acesso = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_province);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null){
			acesso = bundle.getBoolean("filtres");
		}

		startsComponents();
		configRV();
		configCliques();
	}

	private void configCliques(){
		findViewById(R.id.ib_back).setOnClickListener(view -> finish());
	}

	private void configRV(){
		rv_province.setLayoutManager(new LinearLayoutManager(this));
		rv_province.setHasFixedSize(true);
		adapterProvince = new AdapterProvince(ProvinceList.getList(), this);
		rv_province.setAdapter(adapterProvince);
	}

	private void startsComponents(){
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Provinces");

		rv_province = findViewById(R.id.rv_estados);
	}

	@Override
	public void OnClick(Province province) {
		if (!province.getFullname_province().equals("Pakistan")){
				SPFiltre.setFiltre(this, "shrtnmprovince", province.getShortnm_province());
			SPFiltre.setFiltre(this, "fullname_province", province.getFullname_province());
			if (acesso){
				finish();
			}else{
				startActivity(new Intent(this, RegionActivity.class));
			}
		}else{
			SPFiltre.setFiltre(this, "shrtnmprovince", "");
			SPFiltre.setFiltre(this, "fullname_province", "");

			finish();
		}
	}
}