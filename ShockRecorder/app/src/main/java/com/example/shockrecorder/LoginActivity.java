package com.example.shockrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email;
    private EditText etpassword;
    private ProgressBar progressBar;
    private Button loginbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginbtn=findViewById(R.id.loginbtn);
        et_email = findViewById(R.id.etEmail);
        etpassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(v);
            }
        });
    }
    private void login(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    } else {
                        String erro = FirebaseHelper.validErros(task.getException().getMessage());
                        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }
    public void validate(View view) {
        String email = et_email.getText().toString();
        String pass = etpassword.getText().toString();

        if (!email.isEmpty()) {
            if (!pass.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                login(email, pass);
            } else {
                et_email.requestFocus();
                et_email.setError("Enter your email");
            }
        } else {
            etpassword.requestFocus();
            et_email.setError("Enter your email");
        }
    }
}