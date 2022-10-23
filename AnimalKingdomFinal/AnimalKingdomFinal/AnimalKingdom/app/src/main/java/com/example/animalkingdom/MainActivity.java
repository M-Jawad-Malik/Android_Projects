package com.example.animalkingdom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animalkingdom.helper.FirebaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    public TextView txtsignup;
    public TextView txtfrgtpass;
    private EditText et_email;
    private EditText etpassword;
    private ProgressBar progressBar;
    private Button loginbtn;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0,0);
        View relativeLayout=findViewById(R.id.login_container);
        Animation animation= AnimationUtils.loadAnimation(this,android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);
        txtsignup=findViewById(R.id.tvSignup);
        txtfrgtpass=findViewById(R.id.tvforgetpassword);
        loginbtn=findViewById(R.id.loginbtn);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Signup.class);
                startActivity(intent);
            }
        });
        txtfrgtpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                recoveraccount(view);
            }
        });
        initializeComponents();
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(v);
            }
        });
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
        }
        mFirebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, Home.class);
                    startActivity(intent);
                }
            }
        };
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
            et_email.setError("Enter your password");
        }
    }
    private void login(String email, String senha) {
        FirebaseHelper.getAuth().signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, Home.class));
                        finish();
                    } else {
                        String erro = FirebaseHelper.validErros(task.getException().getMessage());
                        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
    }
    public void recoveraccount(View view) {
        Intent intent=new Intent(MainActivity.this,ForgetPassword.class);
        startActivity(intent);
    }
    private void initializeComponents() {


        et_email = findViewById(R.id.etEmail);
        etpassword = findViewById(R.id.etPassword);
        progressBar = findViewById(R.id.progressBar);

    }
}