package com.example.animalkingdom;
import androidx.appcompat.app.AppCompatActivity;
import com.example.animalkingdom.helper.FirebaseHelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animalkingdom.model.User;
import com.santalu.maskara.widget.MaskEditText;
public class Signup extends AppCompatActivity {

    private EditText edt_name;
    private EditText edt_email;
    private MaskEditText edt_telephone;
    private EditText edt_password;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        initializeComponents();
        edt_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edt_password.getText().toString().trim().length() < 8)
                        edt_password.setError("Failed");
                    else
                        edt_password.setError(null);
                }
            }
        });



        toolbarConfig();
    }

    private void toolbarConfig(){
        findViewById(R.id.ib_back).setOnClickListener(view -> finish());
    }



    public void validate(View view) {
        String nome = edt_name.getText().toString();
        String email = edt_email.getText().toString();
        String telefone = edt_telephone.getUnMasked();
        String senha = edt_password.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()){
                    if (telefone.length() == 11) {
                        if (senha.length()>7) {

                            progressBar.setVisibility(View.VISIBLE);

                            User usuario = new User();
                            usuario.setName(nome);
                            usuario.setEmail(email);
                            usuario.setTelefone(telefone);
                            usuario.setPassword(senha);
                            CreateUser(usuario);
                        } else {
                            edt_password.requestFocus();
                            edt_password.setError("Password length should be at least 8");
                        }
                    } else {
                        edt_telephone.requestFocus();
                        edt_telephone.setError("Fill in a valid phone.");
                    }
                }else{
                    edt_telephone.requestFocus();
                    edt_telephone.setError("Enter telephone number.");
                }
            } else {
                edt_email.requestFocus();
                edt_email.setError("Fill in your email.");
            }
        } else {
            edt_name.requestFocus();
            edt_name.setError("fill in your name.");
        }
    }


    private void CreateUser(User usuario) {
        FirebaseHelper.getAuth()
                .createUserWithEmailAndPassword(usuario.getEmail(), usuario.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        usuario.setId(id);
                        usuario.salvar(progressBar, getBaseContext());

                        startActivity(new Intent(this, Home.class));
                        finish();
                    }else{
                        String erro = FirebaseHelper.validErros(task.getException().getMessage());
                        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void initializeComponents() {
        TextView text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("Create Account");
        edt_name = findViewById(R.id.edt_name);
        edt_email = findViewById(R.id.edt_email);
        edt_telephone = findViewById(R.id.edt_telephone);
        edt_password = findViewById(R.id.edt_password);
        progressBar = findViewById(R.id.progressBar);
    }
}
