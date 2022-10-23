package com.example.tris.Ecommerce.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.tris.R;
import com.example.tris.databinding.OrderDetailsBinding;

public class OrderDetailsActivity extends AppCompatActivity
{
    OrderDetailsBinding orderDetailsBinding;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        orderDetailsBinding = DataBindingUtil.setContentView(this, R.layout.order_details);

        getWindow().setStatusBarColor(getResources().getColor(R.color.white,getTheme()));
    }
}
