package com.example.tris.manageExpense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.tris.databinding.ActivityDashBoardBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class dashBoard extends AppCompatActivity {
    double totalIncome = 0;
    double totalExpense = 0;
    double totalBalance = 0;

    private void loadData() {
        firebaseFirestore.collection("Expense").document(firebaseAuth.getUid()).collection("Notes")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot ds : task.getResult()) {
                    com.example.tris.manageExpense.TransactionModel model = new com.example.tris.manageExpense.TransactionModel(
                            ds.getString("id"),
                            ds.getString("note"),
                            ds.getString("amount"),
                            ds.getString("type"),
                            ds.getString("date")
                    );
                    int x = Integer.parseInt(ds.getString("amount"));
                    if(ds.getString("type").equals("Expense"))
                        totalExpense = x+totalExpense;
                    else
                        totalIncome+=x;

                    transactionModelArrayList.add(model);
                }
                totalBalance = totalIncome-totalExpense;
                binding.totalBalance.setText(totalBalance+"");
                binding.totalIncome.setText(totalIncome+"");
                binding.totalExpense.setText(totalExpense+"");

                transactionAdapter = new transactionAdapter(dashBoard.this, transactionModelArrayList);
                binding.history.setAdapter(transactionAdapter);

            }
        });
    }
    ActivityDashBoardBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    ArrayList<com.example.tris.manageExpense.TransactionModel> transactionModelArrayList;
    transactionAdapter transactionAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        transactionModelArrayList = new ArrayList<>();

        binding.history.setLayoutManager(new LinearLayoutManager(this));
        binding.history.setHasFixedSize(true);

        binding.floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(dashBoard.this, AddTransaction.class));
            }

        });
        loadData();
    }
}
