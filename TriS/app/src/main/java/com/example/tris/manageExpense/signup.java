package com.example.tris.manageExpense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tris.ads.model.FirebaseHelper;
import com.example.tris.ads.model.User;
import com.example.tris.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(signup.this, com.example.tris.manageExpense.MainActivity.class);
                startActivity(intent);
            }
        });


    }
    public void validate(View view) {
        String nome = binding.name.getText().toString();
        String email = binding.email.getText().toString();
        String telefone = binding.telephone.getText().toString();
        String residential_id=binding.residentialId.getText().toString();

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!telefone.isEmpty()){
                    if (telefone.length() >= 11) {
                        if (binding.password.length()>7) {


                            binding.progressBar.setVisibility(View.VISIBLE);

                            User usuario = new User();
                            usuario.setName(nome);
                            usuario.setEmail(email);
                            usuario.setTelefone(telefone);
                            usuario.setResidential_id(residential_id);
                            CreateUser(usuario);
                        } else {
                            binding.password.requestFocus();
                            binding.password.setError("Password length should be at least 8");
                        }
                    } else {
                        binding.telephone.requestFocus();
                        binding.telephone.setError("Fill in a valid phone.");
                    }
                }else{
                    binding.telephone.requestFocus();
                    binding.telephone.setError("Enter telephone number.");
                }
            } else {
                binding.email.requestFocus();
                binding.email.setError("Fill in your email.");
            }
        } else {
            binding.name.requestFocus();
            binding.name.setError("fill in your name.");
        }
    }
    private void CreateUser(User usuario) {
        FirebaseHelper.getAuth()
                .createUserWithEmailAndPassword(usuario.getEmail(), binding.password.getText().toString())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        usuario.setId(id);
                        usuario.save(binding.progressBar, getBaseContext());

                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    }else{
                        String erro = FirebaseHelper.validErros(task.getException().getMessage());
                        Toast.makeText(this, erro, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}