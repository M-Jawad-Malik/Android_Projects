package com.example.resortingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import resortingapp.R;

public class GuiderProfileDetailActivity extends AppCompatActivity {
    private ImageView img;
    private TextView text_name;
    private TextView text_gender;
    private TextView text_email;
    private TextView text_contactvalue;
    private TextView text_languagevalue;
    private TextView text_regionvalue;
    private TextView text_weblinkvalue;
    private TextView text_fblinkvalue;

    private Guider guider;

    private User user;

    private List<String> favoritosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guider_profile_detail);

        InitializeComponentes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String s=bundle.getString("selectedGuider");
            guider= new Gson().fromJson(s, Guider.class);
                    configDados();
//            recuperaUsuario();
        }

        configCliques();
        Window window = this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(this,R.color.next_2));
    }



    private void alertAutenticacao(String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are not authenticated");
        builder.setMessage(msg);
        builder.setNegativeButton("No", ((dialog, which) -> {
            dialog.dismiss();
        })).setPositiveButton("Yes", ((dialog, which) -> {
            startActivity(new Intent(this, MainActivity.class));
        }));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void configCliques() {
        findViewById(R.id.ib_voltar).setOnClickListener(v -> finish());
        findViewById(R.id.ib_ligar).setOnClickListener(v -> makeTeleCall());
        findViewById(R.id.ib_msg).setOnClickListener(v->msgSend());
    }

    private void makeTeleCall() {
        if (FirebaseHelper.getAutentication()) {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.fromParts("tel", guider.getContact(), null));
            startActivity(intent);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
    private void msgSend(){
        if (FirebaseHelper.getAutentication()) {
        Uri sms_uri=null;
        sms_uri = Uri.parse("smsto:" + guider.getContact());
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, sms_uri);
        sms_intent.putExtra("sms_body", "Hey there! I am "+guider.getFirstName());
        startActivity(sms_intent);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

//    private void recuperaUsuario() {
//        DatabaseReference usuarioRef = FirebaseHelper.getDatabaseReference()
//                .child("users")
//                .child(ads.getIdUsuario());
//        usuarioRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                user = snapshot.getValue(User.class);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void configDados() {
        Picasso.get().load(guider.getUrlImagens().get(0)).into(img);

        text_name.setText(guider.getFirstName()+" "+guider.getLastName());
        text_gender.setText(guider.getGender());
        text_email.setText(guider.getEmail());
        text_contactvalue.setText(guider.getContact());
        text_languagevalue.setText(guider.getLanguage());
        text_regionvalue.setText(guider.getRegion());
        text_weblinkvalue.setText(guider.getWebLink());
        text_fblinkvalue.setText(guider.getFbLink());
    }

    private void InitializeComponentes() {
        img = findViewById(R.id.sliderView);

        text_name = findViewById(R.id.text_name);
        text_gender = findViewById(R.id.text_gender);
        text_email = findViewById(R.id.text_email);
        text_contactvalue = findViewById(R.id.text_contactvalue);
        text_languagevalue = findViewById(R.id.text_langauge_value);
        text_regionvalue = findViewById(R.id.text_region_value);
        text_weblinkvalue = findViewById(R.id.text_weblink_value);
        text_fblinkvalue = findViewById(R.id.text_fblink_value);
         }
}