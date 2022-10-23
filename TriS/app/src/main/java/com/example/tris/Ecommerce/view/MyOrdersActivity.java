package com.example.tris.Ecommerce.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tris.Ecommerce.adapter.MyOrdersAdapter;
import com.example.tris.R;
import com.example.tris.databinding.MyOrdersBinding;

import java.util.ArrayList;


public class MyOrdersActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MyOrdersAdapter myOrdersAdapter;
    MyOrdersBinding myOrdersBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        myOrdersBinding = DataBindingUtil.setContentView(this, R.layout.my_orders);

        recyclerView = myOrdersBinding.recyclerview;

        getWindow().setStatusBarColor(getResources().getColor(R.color.white,getTheme()));

        setUpRecyclerView();
        getProductsInCart();
    }

    private void setUpRecyclerView()
    {
        linearLayoutManager = new LinearLayoutManager(MyOrdersActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // cartBinding.recyclerview.setHasFixedSize(true);
        //  cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }

    private void getProductsInCart()
    {
        myOrdersAdapter = new MyOrdersAdapter(recyclerView,MyOrdersActivity.this,new ArrayList<Integer>(),new ArrayList<String>(),new ArrayList<String>());


        myOrdersAdapter.update(R.drawable.shoes1,"Refund Accepted","Asian WNDR-13 Running Shoes for Men(Green, Grey)");
        myOrdersAdapter.update(R.drawable.shoes2,"Delivered on Oct 30,2019","Asian WNDR-13 Running Shoes for Men(Green, Grey)");

        recyclerView.setAdapter(myOrdersAdapter);
        myOrdersAdapter.notifyDataSetChanged();
    }
}
