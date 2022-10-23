package com.example.resortingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import resortingapp.R;

public class SeasonActivity  extends AppCompatActivity implements AdapterSeason.OnClickListener {

    private AdapterSeason adapterSeason;
    private RecyclerView rv_season;
    private boolean acesso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            acesso = bundle.getBoolean("filtres");

        }
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
        iniciaComponentes();

        //configCliques();
        configRV();
    }

    private void configRV() {
        rv_season.setLayoutManager(new LinearLayoutManager(this));
        rv_season.setHasFixedSize(true);
        adapterSeason = new AdapterSeason(SeasonList.getList(), this);
        rv_season.setAdapter(adapterSeason);
    }

//	//private void configCliques() {
//		findViewById(R.id.ib_back).setOnClickListener(v -> finish());
//	}

    private void iniciaComponentes() {
        TextView text_toolbar = findViewById(R.id.toolbar_title);
        text_toolbar.setText("select season");
        rv_season = findViewById(R.id.rv_regioes);
//        findViewById(R.id.ib_back).setOnClickListener(v -> finish());
    }

    @Override
    public void OnClick(String season) {
        if (!season.equals("All Seasons")){
            SPFiltre.setFiltre(this, "season", season);
        }else{
            SPFiltre.setFiltre(this, "season", "");
        }
        if (acesso){
            finish();
        }else{
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}