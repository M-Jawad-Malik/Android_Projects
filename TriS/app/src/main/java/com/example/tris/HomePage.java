package com.example.tris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tris.Ecommerce.view.AllCategoryActivity;
import com.example.tris.Ecommerce.view.CartActivity;
import com.example.tris.ads.fragments.HomeFragment;
import com.example.tris.Ecommerce.view.MyOrdersActivity;
import com.example.tris.Ecommerce.view.User;
import com.example.tris.ads.fragments.MyAdsFragment;
import com.example.tris.manageExpense.dashBoard;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Fragment fragment = null;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        /*LinearLayout lin_lay_add_paynow = (LinearLayout)findViewById(R.id.home_linear_layout);

        View pay_now_view = getLayoutInflater().inflate(R.layout.home, null);
        lin_lay_add_paynow.addView(pay_now_view);*/
       // ImageView button =  findViewById(R.id.hamburger);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);
//        button.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                drawerLayout.openDrawer(GravityCompat.START);
//            }
//        });
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        }


        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

      /*  View decorView = getWindow().getDecorView();
// Hide both the navigation bar and the status bar.
// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
// a general rule, you should design your app to hide the status bar whenever you
// hide the navigation bar.
        int uiOptions =  View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);*/

        Drawable background = getResources().getDrawable(R.drawable.gradient_home,getTheme());
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent,getTheme()));
        getWindow().setBackgroundDrawable(background);


        try {


            BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
           NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            NavigationUI.setupWithNavController(navView, navController);

              navView.setItemIconTintList(null);
        navView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.home:
                        navController.navigate(R.id.menu_home);
                        break;
                    case R.id.cart:
                        ViewDialog alert = new ViewDialog();
                        alert.showDialog("Your Ads or Your Events","myads",navController,HomePage.this);
                        break;
                    case R.id.shopping_bag:
                        navController.navigate(R.id.menu_favorites);
                        break;
                    case R.id.message:
                        ViewDialog alert4 = new ViewDialog();
                        alert4.showDialog("What do you want to post?","newads",navController,HomePage.this);

                       //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();






//                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomePage.this);
//                        alertDialog.setTitle("What do you want to Post");
//                        alertDialog.setMessage("Click below");
//                        alertDialog.setNegativeButton("Event", ((dialog, whitch) -> {
//
//                        })).setPositiveButton("Ads", ((dialog, whitch) -> {
//                            Bundle bundle = new Bundle();
//                            bundle.putString("type", "ad");
//                            navController.navigate(R.id.menu_new_ads,bundle);
//                        }));
//
//                        AlertDialog dialog = alertDialog.create();
//                        dialog.show();

                        break;

                    case R.id.user:
                        Intent intent = new Intent(getApplicationContext(), dashBoard.class);
                        startActivity(intent);
                        break;
                }


                return false;
            }
        });


        if (savedInstanceState == null)
        {
            navView.setSelectedItemId(R.id.home); // change to whichever id should be default
        }
        }catch (Exception ce){
            Toast.makeText(this,ce.toString(),Toast.LENGTH_LONG).show();
        }

   }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        switch (menuItem.getItemId())
        {

            case R.id.wishlist:
                navController.navigate(R.id.menu_favorites);
                break;
            case R.id.account:
                Intent intent=new Intent(this,ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.notifications:    break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent1=new Intent(this,com.example.tris.manageExpense.MainActivity.class);
                startActivity(intent1);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    void cart()
    {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    void all_category()
    {
        Intent intent = new Intent(this, AllCategoryActivity.class);
        startActivity(intent);
    }

    void my_orders()
    {
        Intent intent = new Intent(this, MyOrdersActivity.class);
        startActivity(intent);
    }

//    void switchfragment(Fragment fragment)
//    {
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction().replace(R.id.framelayout,fragment).commit();
//    }

}