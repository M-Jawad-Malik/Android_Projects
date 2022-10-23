package com.example.intentdatasend_java_pprpract;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    EditText edttxt2;
    TextView txtvw2;
    Button send2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        edttxt2=(EditText)findViewById(R.id.edttxt2);
        txtvw2=(TextView)findViewById(R.id.txtvw2);
        send2=(Button)findViewById(R.id.send2);
        Intent intent=getIntent();
        String msg=intent.getStringExtra("String");
        txtvw2.setText(msg);
send2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        send();
    }
});
    }
    public void send(){
        Intent intent=new Intent();
        intent.putExtra(MainActivity.et_msg,txtvw2.getText().toString());
        setResult(RESULT_OK);
        finish();
    }

}