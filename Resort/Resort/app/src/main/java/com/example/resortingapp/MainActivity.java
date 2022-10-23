package com.example.resortingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import resortingapp.R;

import com.example.resortingapp.Onboarding.OnboardingActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private DrawerLayout mDrawerLayout;

    private Toolbar mToolbar;
    private TextView mTextView;
    public String lan = "null";
    NavigationView mNavigationView;

    //back_press
    boolean doubleBackToExitPressedOnce = false;

    //nav_header
    private CircleImageView mNavProfileAvatar;
    private TextView mNavDisplayName;
    private TextView mNavEmail;

    //fragments_tags
    private final String TOUR_FRAGMENT_TAG = "tour";
    private final String PROFILE_FRAGMENT_TAG = "profile";
    private final String HELP_FEEDBACK_FRAGMENT_TAG = "help_feedback";

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListner;

    int close = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        language_checker();
        FirestoreQueries.getUser(new FirestoreQueries.FirestoreUserCallback() {
            @Override
            public void onCallback(User user) {
                mNavProfileAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_avatar);
                Picasso.get().load(user.imageURL).fit().into(mNavProfileAvatar);
                mNavDisplayName = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_name);
                mNavDisplayName.setText(user.displayName + "");
                mNavEmail = mNavigationView.getHeaderView(0).findViewById(R.id.nav_user_email);
                mNavEmail.setText(user.email + "");
            }
        });

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(null);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toggle.getDrawerArrowDrawable().setColor(getColor(R.color.next_2));
        } else {
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.next_2));
        }
        toggle.syncState();


        int fragmentId = getIntent().getIntExtra("FRAGMENT_ID", 0);
        Bundle bundle = new Bundle();
        bundle.putInt("TARGET_FRAGMENT_ID", fragmentId);
        TourFragment tourFragment = new TourFragment();
        tourFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, tourFragment, TOUR_FRAGMENT_TAG).commit();
        mNavigationView.setCheckedItem(R.id.nav_tour);
        changeToolbarTitle(getString(R.string.nav_tour));

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
                    startActivity(intent);
                }
            }
        };
        makeStatusbarTransparent();
    }

    private void language_checker() {
        if (SplashScreenActivity.lan.equalsIgnoreCase("ar")) {
            setLocale("ar");
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getResources().getString(R.string.close), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1000);
    }

    public static void mToast(Context con,String msg, int duration) {
        Toast.makeText(con, msg, duration).show();
    }


    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_profile:
                if (getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG) != null) {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG)).commit();
                    InterestsAdapter.intrests_hashSet.clear();
                    ProfileFragment.interest_adapter.notifyDataSetChanged();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ProfileFragment(), PROFILE_FRAGMENT_TAG).commit();
                }
                if (getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG)).commit();
                if (getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG)).commit();
                changeToolbarTitle(getString(R.string.nav_profile));
                break;
            case R.id.nav_tour:
                if (getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG) != null) {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new TourFragment(), TOUR_FRAGMENT_TAG).commit();
                }
                if (getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG)).commit();
                if (getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG)).commit();
                changeToolbarTitle(getString(R.string.nav_tour));
                break;
            case R.id.nav_help_feedback:
                if (getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG) != null) {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag(HELP_FEEDBACK_FRAGMENT_TAG)).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new HelpFeedbackFragment(), HELP_FEEDBACK_FRAGMENT_TAG).commit();
                }
                if (getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(PROFILE_FRAGMENT_TAG)).commit();
                if (getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG) != null)
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag(TOUR_FRAGMENT_TAG)).commit();
                changeToolbarTitle(getString(R.string.nav_help_feedback));
                break;
            case R.id.nav_upcoming_event:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.meetup.com/cities/pk/"));
                startActivity(i);
                break;
            case R.id.nav_logout:
               // AuthUI.getInstance().signOut(this);
                mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
                mFirebaseAuth.signOut();
                GoogleSignInOptions gso = new GoogleSignInOptions.
                        Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                        build();
                GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(getBaseContext(),gso);
                googleSignInClient.signOut();
                break;
            case R.id.nav_guider:
                Intent intent=new Intent(this,tourguiderreg.class);
                startActivity(intent);
                break;
            case R.id.english:
                selectyourlanguage(2);
                break;
            case R.id.arabic:
                selectyourlanguage(1);
                break;
            case R.id.urdu:
                selectyourlanguage(3);
                break;
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    public void changeToolbarTitle(String str) {
        mTextView = mToolbar.findViewById(R.id.toolbar_title);
        mTextView.setText(str);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
    }

    public void selectyourlanguage(int i) {
        final String[] listitem = {"Arabic", "English","Urdu"};
        if (i != 0) {
            switch (i) {
                case (1): {
                    setLocale("ar");
                    //recreate();
                }
                break;
                case (2): {
                    setLocale("en");
                    // recreate();
                }
                break;
                case (3): {
                    setLocale("ur");
                    // recreate();
                }
                break;
            }
            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
        }

    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration conf = new Configuration();
        conf.locale = locale;
        getBaseContext().getResources().updateConfiguration(conf, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor pref = getSharedPreferences("settings", MODE_PRIVATE).edit();
        pref.putString("my_lang", lang);
        pref.apply();
    }

    private void makeStatusbarTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.next_2));
        }
    }

}
