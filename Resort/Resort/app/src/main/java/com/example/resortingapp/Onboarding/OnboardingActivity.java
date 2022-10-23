package com.example.resortingapp.Onboarding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.resortingapp.MainActivity;
import com.example.resortingapp.SplashScreenActivity;
import com.example.resortingapp.User;
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
import java.util.Map;

import resortingapp.R;


public class OnboardingActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
int RC_SIGN_IN=1;
    //firebase
    private FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore mFirebaseFirestore;
    private CollectionReference users;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListner;
    private FirebaseUser user;
    public static String lan = "null";
    private boolean internet_checked = false;
    private boolean flag = false;

    private boolean isFirstAnimation = false;
    private ViewPager viewPager;
    private OnboardingAdapter onboardingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        makeStatusbarTransparent();

        viewPager = findViewById(R.id.onboarding_view_pager);

        onboardingAdapter = new OnboardingAdapter(this);
        viewPager.setAdapter(onboardingAdapter);
        viewPager.setPageTransformer(false, new OnboardingPageTransformer());
       // mFirebaseAuth.addAuthStateListener(mFirebaseAuthListner);


    }


    // Listener for next button press
    public void nextPage(View view) {
        if (view.getId() == R.id.button2) {
            if (viewPager.getCurrentItem() < onboardingAdapter.getCount() - 1) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
            }
        }
    }
public void nextPage4(View view)
{

    List<AuthUI.IdpConfig> providers = Arrays.asList(new AuthUI.IdpConfig.GoogleBuilder().build());
    AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout.Builder(R.layout.activity_login).setGoogleButtonId(R.id.gmail_btn).build();
    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setIsSmartLockEnabled(false).setTheme(R.style.AppThemeFirebaseAuth).setAuthMethodPickerLayout(customLayout).build(), RC_SIGN_IN);

}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //Log.d(TAG, "onActivityResult: entered ");
            setUser();

        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "sign in canceled", Toast.LENGTH_SHORT).show();
            finish();
        }}
    private void start_activity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        super.onPause();
      // mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListner);
    }
//    private void showSnackBar() {
//        snackbar = Snackbar.make(coordinatorLayout, getResources().getString(R.string.lost_internet), Snackbar.LENGTH_INDEFINITE)
//                .setAction(getResources().getString(R.string.retry), new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (snackbar.isShown()) {
//                            snackbar.dismiss();
//                        }
//                        checkInternet();
//                        onResume();
//                    }
//                });
//        snackbar.show();
//    }
    protected void setUser() {
        mFirebaseAuth = FirebaseAuth.getInstance();
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
//                            if (task.isSuccessful())
//                               // Log.d(TAG, "onComplete: " + " new user registered");
//                            else
//                                //Log.d(TAG, "Failed!!");
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
                              //  Log.d(TAG, "onSuccess: user updated successfully");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                             //   Log.d(TAG, "onFailure: Failed to update user image");
                            }
                        });

                    }
                }

                start_activity();
            }
        });
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
