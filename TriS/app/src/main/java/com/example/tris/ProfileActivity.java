package com.example.tris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tris.ads.model.FirebaseHelper;
import com.example.tris.ads.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    private EditText edt_nome;
    private MaskEditText edt_telefone;
    private EditText edt_email,residential_id;
    private ProgressBar progressBar;

    private final int SELECAO_GALERIA = 100;

    private ImageView imagem_perfil;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        startsComponents();
        retrieveProfile();
    }
    private void startsComponents() {
//        TextView text_toolbar = findViewById(R.id.text_toolbar);
//        text_toolbar.setText("Profile");

        edt_nome = findViewById(R.id.edt_nome);
        edt_telefone = findViewById(R.id.edt_telefone);
        edt_email = findViewById(R.id.edt_email);
        progressBar = findViewById(R.id.progressBar);
        residential_id=findViewById(R.id.edt_resd_id);

    }
    private void retrieveProfile() {
        progressBar.setVisibility(View.VISIBLE);
        DatabaseReference perfilRef = FirebaseHelper.getDatabaseReference()
                .child("users")
                .child(FirebaseHelper.getIdFirebase());
        perfilRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                configDaata();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void configDaata() {

        edt_nome.setText(user.getName());
        edt_telefone.setText(android.telephony.PhoneNumberUtils.formatNumber( user.getTelefone()));
        edt_email.setText(user.getEmail());
        residential_id.setText(user.getResidential_id());
        progressBar.setVisibility(View.GONE);

    }

    public void validaData(View view) {
        String nome = edt_nome.getText().toString();
        String t=edt_telefone.getText().toString();
        String r=residential_id.getText().toString();
        if (!nome.isEmpty()) {
            if (!t.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                user.setName(nome);
                user.setTelefone(t);
                user.setResidential_id(r);
                        user.save(progressBar, getBaseContext());
            } else {
                edt_telefone.requestFocus();
                edt_telefone.setError("Enter Telephone.");
            }
        } else {
            edt_nome.requestFocus();
            edt_nome.setError("Enter Name.");
        }
    }
}