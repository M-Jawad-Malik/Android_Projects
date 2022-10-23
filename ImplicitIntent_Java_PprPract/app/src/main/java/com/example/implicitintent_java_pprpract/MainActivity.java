package com.example.implicitintent_java_pprpract;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.core.app.ShareCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText weblink;
    EditText location;
    EditText textoshare;
    Button searchbtn;
    Button locatebtn;
    Button sharebtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         weblink=(EditText)findViewById(R.id.weblink);
         location=(EditText)findViewById(R.id.location);
         textoshare=(EditText)findViewById(R.id.texttoShare);
         searchbtn=(Button)findViewById(R.id.searchbtn);
         locatebtn=(Button)findViewById(R.id.locatebtn);
         sharebtn=(Button)findViewById(R.id.sharebtn);
         searchbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 searchWeb();
             }
         });
         locatebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 locatePlace();
             }
         });
         sharebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 shareText();
             }
         });
    }
    public void searchWeb(){
        Uri uri=Uri.parse(weblink.getText().toString());
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void locatePlace(){
        Uri uri=Uri.parse("geo:0,0?q="+location.getText().toString());
        Intent intent=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);
    }
    public void shareText(){
        ShareCompat.IntentBuilder.from(this).setType("text/plain").setChooserTitle("Share this text with: ").setText(textoshare.getText().toString()).startChooser();
    }
}