package com.example.tris.manageExpense;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tris.databinding.ActivityUpdateDeleteBinding;

public class update_delete extends AppCompatActivity {
    ActivityUpdateDeleteBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateDeleteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}