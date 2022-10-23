package com.example.resortingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import resortingapp.R;

import com.example.resortingapp.Onboarding.OnboardingActivity;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = "SplashScreenActivity";

    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;

    //firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference users;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListner;
    private FirebaseUser user;
    private final int RC_SIGN_IN = 1;
    public static String lan = "null";
    private boolean internet_checked = false;
    private boolean flag = false;

    private boolean isFirstAnimation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadLocale();
        setContentView(R.layout.activity_splash_screen);
        coordinatorLayout = findViewById(R.id.coordinator);
        makeStatusbarTransparent();

        Animation hold = AnimationUtils.loadAnimation(this, R.anim.hold);
        final Animation translateScale = AnimationUtils.loadAnimation(this, R.anim.translate_scale);

        final ImageView imageView = findViewById(R.id.splash_screen_logo);

        translateScale.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!isFirstAnimation) {
                    imageView.clearAnimation();
                    checkInternet();
                    firebaseLogin();
                    if (internet_checked) {
                        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);
                    } else
                        showSnackBar();
                }

                isFirstAnimation = true;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hold.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.startAnimation(translateScale);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imageView.startAnimation(hold);
    }

    private void start_activity() {
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void checkInternet() {
        if (!SeyahaUtils.checkInternetConnectivity(this)) {
            showSnackBar();
        } else {
            internet_checked = true;
        }
    }

    private void firebaseLogin() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        mFirebaseAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    flag=true;
                    Intent intent=new Intent(getBaseContext(), OnboardingActivity.class);
                    startActivity(intent);
                } else {
                    if (!flag)
                        setUser();
                }
            }
        };
    }

    private void showSnackBar() {
        snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.lost_internet), Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (snackbar.isShown()) {
                            snackbar.dismiss();
                        }
                        checkInternet();
                        onResume();
                    }
                });
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListner);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult: entered ");
            setUser();

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "sign in canceled", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    protected void setUser() {
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        users = mFirebaseFirestore.collection("users");
        users.whereEqualTo("email", mFirebaseAuth.getCurrentUser().getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> intrests = new ArrayList<>();
                List<String> toursCommentedOn = new ArrayList<>();
                User mUser = null;
                if (queryDocumentSnapshots.isEmpty()) {

                    DocumentReference newUserRefernce = users.document();
                    mUser = new User(mFirebaseAuth.getCurrentUser().getDisplayName(),
                            mFirebaseAuth.getCurrentUser().getEmail(),
                            mFirebaseAuth.getCurrentUser().getPhotoUrl().toString() + "?height=500",
                            intrests,
                            false,
                            toursCommentedOn,
                            newUserRefernce.getId());

                    newUserRefernce.set(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                                Log.d(TAG, "onComplete: " + " new user registered");
                            else
                                Log.d(TAG, "Failed!!");
                        }
                    });
                } else {
                    User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                    if (!user.imageURL.equals(mFirebaseAuth.getCurrentUser().getPhotoUrl().toString()) || !user.displayName.equals(mFirebaseAuth.getCurrentUser().getDisplayName())) {
                        final DocumentReference userRef = mFirebaseFirestore.collection("users").document(user.userId);

                        Map<String, Object> updatedData = new HashMap<>();
                        updatedData.put("imageURL", mFirebaseAuth.getCurrentUser().getPhotoUrl().toString() + "?height=500");
                        updatedData.put("displayName", mFirebaseAuth.getCurrentUser().getDisplayName());

                        userRef.update(updatedData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "onSuccess: user updated successfully");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: Failed to update user image");
                            }
                        });

                    }
                }

                start_activity();
            }
        });
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

    public void loadLocale() {
        SharedPreferences pref = getSharedPreferences("settings", MODE_PRIVATE);
        String lang = pref.getString("my_lang", "en");
        if (lang.equals("ar")) {
            lan = "ar";
        }else if (lang.equals("ur")) {
            lan = "ur";
        }
        else {
            lan = "en";
        }

        setLocale(lang);
    }

    private void makeStatusbarTransparent() {

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
