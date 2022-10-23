package com.example.activitiesandintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public TextView txtvw;
    public static final int Text_Request=1;
    String msg;
    public static String et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.firstactBtn);
        EditText edtxt=(EditText)findViewById(R.id.edttxt);
        msg=edtxt.getText().toString();
        txtvw=(TextView)findViewById(R.id.txtvw);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK)
        {
            if(requestCode==Text_Request){
                if(data!=null){
                txtvw.setText(data.getStringExtra(et_msg));
            }
            }

        }
        else {
            Toast.makeText(this,"Result Canceled",Toast.LENGTH_SHORT).show();
        }
    }

    public void launchSecondActivity(View view){
    Intent intent=new Intent(this,second_activity.class);
    intent.putExtra("String",msg);
    startActivityForResult(intent,Text_Request);
}}