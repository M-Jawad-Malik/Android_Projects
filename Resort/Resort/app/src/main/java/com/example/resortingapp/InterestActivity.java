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

public class InterestActivity extends AppCompatActivity implements AdapterInterest.OnClickListener {
    private RecyclerView rv_interest;
    private AdapterInterest adapterInterest;
    private boolean allInterest = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);
        initializeComponentes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            allInterest = (Boolean) bundle.getSerializable("all");
        }

        configClicks();
        InitializeRV();
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
    }

    private void InitializeRV(){
        rv_interest.setLayoutManager(new LinearLayoutManager(this));
        rv_interest.setHasFixedSize(true);
        adapterInterest = new AdapterInterest(InterestList.getList(allInterest), this);
        rv_interest.setAdapter(adapterInterest);
    }

    private void configClicks(){
      //  findViewById(R.id.ib_back).setOnClickListener(v -> finish());
    }

    private void initializeComponentes(){
        TextView text_toolbar = findViewById(R.id.toolbar_title);
        text_toolbar.setText("Interest");

        rv_interest = findViewById(R.id.rv_categorias);
    }

    @Override
    public void OnClick(Interest interest) {
        Intent intent = new Intent();
        intent.putExtra("selectedInterest", interest);
        setResult(RESULT_OK, intent);
        finish();
    }
}