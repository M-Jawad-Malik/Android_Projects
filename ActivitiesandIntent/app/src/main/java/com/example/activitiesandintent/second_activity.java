package com.example.activitiesandintent;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class second_activity extends AppCompatActivity {

    EditText webedtxt;
    EditText edttxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        TextView txtvw = (TextView) findViewById(R.id.txtvw);
        Intent intent = getIntent();
        String msg=intent.getStringExtra("String");
        txtvw.setText(msg);
        webedtxt=(EditText)findViewById(R.id.web_edttxt);
        edttxt=(EditText)findViewById(R.id.edttxt);

    }
public void Send(View view){
    Intent intent=new Intent(); //Transfer of data, significant for launching of activities
    intent.putExtra(MainActivity.et_msg,edttxt.getText().toString());
    setResult(RESULT_OK,intent);
    finish();
}
public void browse(View view){
String url=edttxt.getText().toString();
  /*  Uri uri=Uri.parse(url);
    Intent intent=new Intent(Action.View)
            */
    }
}