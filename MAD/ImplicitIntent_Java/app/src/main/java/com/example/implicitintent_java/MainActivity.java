package com.example.implicitintent_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText editText,editText2,editText3;
    Button btn1,btn2,btn3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.editText);
        editText2=(EditText)findViewById(R.id.editText2);
        editText3=(EditText)findViewById(R.id.editText3);
/*        btn1=(Button)findViewById(R.id.button);
        btn2=(Button)findViewById(R.id.button2);
        btn3=(Button)findViewById(R.id.button3);*/

    }
    public void openWebsite(View view){
        String url=editText.getText().toString();
        Uri uri=Uri.parse(url);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void openLocation(View view){
        String loc=editText2.getText().toString();
        Uri uri=Uri.parse("geo:0,0?q="+loc);
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void ShareText(View view){
        String txt=editText3.getText().toString();
        String type="text/plain";
        ShareCompat.IntentBuilder.from(this).setType(type).setChooserTitle("Share this Text with: ").setText(txt).startChooser();
    }

}