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
		iniciaCompomentes();
		configRV();
	//	configCliques();
		Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
		window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
	}

//	//private void configCliques(){
//		findViewById(R.id.ib_back).setOnClickListener(view -> finish());
//	}

	private void configRV(){
		rv_province.setLayoutManager(new LinearLayoutManager(this));
		rv_province.setHasFixedSize(true);
		adapterProvince = new AdapterProvince(ProvinceList.getList(), this);
		rv_province.setAdapter(adapterProvince);
	}

	private void iniciaCompomentes(){
		TextView text_toolbar = findViewById(R.id.toolbar_title);
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
				Toast.makeText(this, province.getFullname_province()+" Selected", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(this, RegionActivity.class));
			}
		}else{
			Toast.makeText(this, "Pakistan All Cities Selected", Toast.LENGTH_SHORT).show();
			SPFiltre.setFiltre(this, "shrtnmprovince", "");
			SPFiltre.setFiltre(this, "fullname_province", "");
			SPFiltre.setFiltre(this, "ddd", "");
			SPFiltre.setFiltre(this, "region", "");
			finish();
		}
	}
}