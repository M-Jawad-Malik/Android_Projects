package com.example.framgmets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;
import java.net.PortUnreachableException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration config=getResources().getConfiguration();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        if(config.orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            LandscapeFragment landscapeFragment=new LandscapeFragment();
            fragmentTransaction.replace(android.R.id.content,landscapeFragment);

        }else
        {
            PortraitFragment portraitFragment=new PortraitFragment();
            fragmentTransaction.replace(android.R.id.content,portraitFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}