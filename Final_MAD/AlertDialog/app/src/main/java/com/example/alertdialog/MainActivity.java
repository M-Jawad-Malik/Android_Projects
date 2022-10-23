package com.example.alertdialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button menuBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         menuBtn=(Button)findViewById(R.id.btn);
        registerForContextMenu(menuBtn);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select an option");
        menu.add(0,v.getId(),0,"Edit");
        menu.add(0,v.getId(),0,"Cut");
        menu.add(0,v.getId(),0,"Copy");
        menu.add(0,v.getId(),0,"Paste");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {


        if(item.getTitle()=="Edit"){
            Toast.makeText(this,"Edit Clicked",Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle()=="Cut"){
            Toast.makeText(this,"Cut Clicked",Toast.LENGTH_SHORT).show();
        }
        else if(item.getTitle()=="Copy"){
            Toast.makeText(this,"Copy Clicked",Toast.LENGTH_SHORT).show();
        }
       else if(item.getTitle()=="Paste"){
            Toast.makeText(this,"Paste Clicked",Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        showAlertDialogBox();

    }

    public void showAlertDialogBox()
    {
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to Exit");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            finish();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
       AlertDialog alertdialog=alertDialogBuilder.create();
       alertdialog.show();

    }
}