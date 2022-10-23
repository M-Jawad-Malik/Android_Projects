package com.example.animalkingdom;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.model.Address;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;

public class AddressActivity extends AppCompatActivity {

	private MaskEditText edt_cep;
	private EditText edt_uf;
	private EditText edt_county;
	private EditText edt_district;
	private ProgressBar progressBar;

	private Address address;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adress);
		initlializeComponents();
		getAddress();
		configClicks();
	}

	private void configClicks(){
		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
	}

	public void dataValidation(View view) {
		String cep = edt_cep.getMasked();
		String uf = edt_uf.getText().toString();
		String county = edt_county.getText().toString();
		String district = edt_district.getText().toString();

		if (!cep.isEmpty()) {
			if (cep.length() == 5){
				if (!uf.isEmpty()) {
					if (!county.isEmpty()) {
						if (!district.isEmpty()) {
							progressBar.setVisibility(View.VISIBLE);

							if (address == null){
								address = new Address();
							}

							Address address = new Address();
							address.setZipcode(cep);
							address.setProvince(uf);
							address.setCity(county);
							address.setDistrict(district);
							address.Save(FirebaseHelper.getIdFirebase(),getBaseContext(), progressBar);
						} else {
							edt_district.requestFocus();
							edt_district.setError("fill in the District.");
						}
					} else {
						edt_county.requestFocus();
						edt_county.setError("fill in the City.");
					}
				} else {
					edt_uf.requestFocus();
					edt_uf.setError("fill in the State.");
				}
			}else{
				edt_cep.requestFocus();
				edt_cep.setError("Enter Valid Zip Code.");
			}
		} else {
			edt_cep.requestFocus();
			edt_cep.setError("Fill in Zip Code.");
		}
	}

	private void getAddress(){
		progressBar.setVisibility(View.VISIBLE);
		DatabaseReference AddressRef = FirebaseHelper.getDatabaseReference()
				.child("address")
				.child(FirebaseHelper.getIdFirebase());
		AddressRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				if (snapshot.exists()){
					address = snapshot.getValue(Address.class);
					configAddress(address);
				}else{
					progressBar.setVisibility(View.GONE);
				}
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});
	}

	private void configAddress(Address address){
		edt_cep.setText(address.getZipcode());
		edt_uf.setText(address.getProvince());
		edt_county.setText(address.getCity());
		edt_district.setText(address.getDistrict());

		progressBar.setVisibility(View.GONE);
	}

	private void initlializeComponents() {
		TextView text_toolbar = findViewById(R.id.text_toolbar);
		text_toolbar.setText("Address");

		edt_cep = findViewById(R.id.edt_cep);
		edt_uf = findViewById(R.id.edt_uf);
		edt_county = findViewById(R.id.edt_municipio);
		edt_district = findViewById(R.id.edt_bairro);

		progressBar = findViewById(R.id.progressBar);
	}
}