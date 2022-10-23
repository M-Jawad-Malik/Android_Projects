package com.example.animalkingdom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.navview);
        drawerLayout = findViewById(R.id.drawer);


        //adding customised toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //toggle button
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //when an item is selected from menu
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                  //  Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.profile:
//                        Toast.makeText(getApplicationContext(),"Profile",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(getApplicationContext(), DoctorReg.class);
                    startActivity(intent);
                    break;

                case R.id.txtFeedback:
                    // Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent1 = new Intent(getApplicationContext(), helpandfeedback.class);
                    startActivity(intent1);
                    break;

                case R.id.logout:
                    // Toast.makeText(getApplicationContext(),"Privacy",Toast.LENGTH_SHORT).show();
                    //close drawer
                    drawerLayout.closeDrawer(GravityCompat.START);
                    FirebaseAuth.getInstance().signOut();
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent2);
                    break;


            }

            return true;
        });
        try {


            BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);

            int id = getIntent().getIntExtra("id", 0);
            if (id == 2) {
                navView.setSelectedItemId(R.id.menu_my_ads);
            }
        }catch (Exception ce){
            Toast.makeText(this,ce.toString(),Toast.LENGTH_LONG).show();
        }
    }
}