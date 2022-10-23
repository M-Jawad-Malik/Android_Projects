package com.example.tris;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.google.android.material.navigation.NavigationView;

public class ViewDialog {
TextView maincontent;
    public static String type;
    public void showDialog(String content,String type,NavController nav, Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.newcustom_layout);
        maincontent=dialog.findViewById(R.id.maincontent);
        maincontent.setText(content);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FrameLayout mDialogNo = dialog.findViewById(R.id.frmNo);
        mDialogNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("type", "ad");
                if(type.contains("newads")) {

                    nav.navigate(R.id.menu_new_ads, bundle);
                }else if(type.contains("myads")){
                    nav.navigate(R.id.menu_my_ads,bundle);
                }
                dialog.dismiss();
            }
        });

        FrameLayout mDialogOk = dialog.findViewById(R.id.frmOk);
        mDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", "event");
                if(type.contains("newads")) {

                nav.navigate(R.id.menu_new_ads,bundle);
                }else if(type.contains("myads")){
                    nav.navigate(R.id.menu_my_ads,bundle);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}