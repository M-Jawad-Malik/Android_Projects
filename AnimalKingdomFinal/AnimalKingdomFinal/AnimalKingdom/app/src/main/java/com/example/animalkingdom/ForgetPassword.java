package com.example.animalkingdom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.animalkingdom.helper.FirebaseHelper;

public class ForgetPassword extends AppCompatActivity {

    private EditText edt_email;
    private ProgressBar progressBar;
    private Button sendlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        edt_email = findViewById(R.id.etforegetEmail);
        progressBar = findViewById(R.id.progressBar);
        sendlink=findViewById(R.id.bSignUp);
        sendlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateemail(v);
            }
        });

    }

    public void validateemail(View view) {
        String email = edt_email.getText().toString();
        if (!email.isEmpty()) {
//            progressBar.setVisibility(View.VISIBLE);
            resetEmail(email);
        } else {
            edt_email.requestFocus();
            edt_email.setError("Enter Valid Email");
        }
    }
    private void resetEmail(String email) {
        FirebaseHelper.getAuth().sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Reset link has been sent at your e-mail", Toast.LENGTH_SHORT).show();
            } else {
                String erro = FirebaseHelper.validErros(task.getException().getMessage());
                Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();

                Log.i("error", "resetemail: " + task.getException().getMessage());
            }
//            progressBar.setVisibility(View.GONE);
        });
    }
}