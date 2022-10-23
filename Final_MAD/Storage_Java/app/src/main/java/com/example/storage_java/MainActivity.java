package com.example.storage_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    Button writebtn,readbtn;
    EditText edttxt;
    TextView txtvw;

    private String filename="Sample.txt";
    private  String filepath="MyFileStorage";
    private  String myData="";
    File myExternalFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        writebtn=(Button)findViewById(R.id.writebtn);
        readbtn=(Button)findViewById(R.id.readbtn);
        edttxt=(EditText) findViewById(R.id.edittxt);
        txtvw=(TextView) findViewById(R.id.txtvw);
        if(!isExternalStorageAllowed()||isExternalStorageReadOnly()){
            writebtn.setEnabled(false);
        }else 
        {
            myExternalFile=new File(getExternalFilesDir(filepath),filename);
        }




    }
    public void writeData(View view){
    try {
        FileOutputStream fout=new FileOutputStream(myExternalFile);
        fout.write(edttxt.getText().toString().getBytes());
        fout.close();
            }

    catch (Exception e){
        e.printStackTrace();

            }
        Toast.makeText(this,"data Saved in File",Toast.LENGTH_LONG).show();
    }
    public void readData(View view)
    {
        try {
            FileInputStream fin=new FileInputStream(myExternalFile);
            DataInputStream din=new DataInputStream(fin);
            BufferedReader br=new BufferedReader(new InputStreamReader(din));
            String data;
            while ((data=br.readLine())!=null){
                myData=myData+data;

            }
            fin.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(this,"Data read from File",Toast.LENGTH_LONG).show();
        txtvw.setText(myData);

    }
    private static boolean isExternalStorageAllowed(){
    String exterStorageStat= Environment.getExternalStorageState();
    if(Environment.MEDIA_MOUNTED.equals(exterStorageStat))
    {
        return  true;
    }
    return false;

    }
    private static boolean isExternalStorageReadOnly(){

        String exterStorageStat= Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(exterStorageStat)){
            return true;
        }
        return false;
    }
}