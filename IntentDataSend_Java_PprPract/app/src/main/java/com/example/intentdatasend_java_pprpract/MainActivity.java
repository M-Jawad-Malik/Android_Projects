package com.example.intentdatasend_java_pprpract;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText edttxt1;
    TextView txtvw1;
    Button send1;
    String msg;
    public static final int REQUEST_CODE=1;
    public static String et_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edttxt1=(EditText)findViewById(R.id.edttxt1);
        txtvw1=(TextView)findViewById(R.id.txtvw1);
        send1=(Button)findViewById(R.id.send1);
        msg=edttxt1.getText().toString();
        send1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

Send();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==REQUEST_CODE){
                if(data!=null){
                    txtvw1.setText(et_msg);
                }
            }
        }
    }

    public void Send(){
        Intent intent=new Intent(this,SecondActivity.class);
        intent.putExtra("String",msg);
        startActivityForResult(intent,REQUEST_CODE);
    }

}