package com.example.resortingapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import resortingapp.R;


public class tourguiderreg extends AppCompatActivity {
    Spinner dynamicSpinner;
    String travel_type;
    private ImageView imagem1;
    private ImageView imagem2;
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView region;
    private TextView language;
    private TextView contact;
    private TextView weblink;
    private TextView fblink;
    private boolean imageCheck = true;
    Guider guider;
    private Button register;
    private List<Image> imagemList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tourguiderreg);
firstName=findViewById(R.id.edt_firstname);
        lastName=findViewById(R.id.edt_lastname);
        email=findViewById(R.id.edt_email);
        region=findViewById(R.id.edt_region);
        language=findViewById(R.id.edt_language);
        contact=findViewById(R.id.edt_contact);
        weblink=findViewById(R.id.edt_weblink);
        fblink=findViewById(R.id.edt_fblink);
        register=findViewById(R.id.btn_register);
        guider=new Guider();
        //DropDown Coding
        dynamicSpinner = (Spinner) findViewById(R.id.dropStatus);
        ArrayAdapter<String> myAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dynamicSpinner.setAdapter(myAdapter);
        dynamicSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        travel_type = String.valueOf(adapterView.getItemAtPosition(i));
                        //Toast.makeText(Plan_Trip.this, travel_type, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }

                }
        );
        //Dropdown Coding
        imagem1 = findViewById(R.id.imagem1);
        imagem2 = findViewById(R.id.imagem2);
        imagem1.setOnClickListener(v -> showBottomDialog(1));
        imagem2.setOnClickListener(v -> showBottomDialog(2));
        makeStatusbarTransparent();
}
    private void verfifyGalleryPermission(int requestCode) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                OpenGallery(requestCode);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(tourguiderreg.this, "Permisson Denied", Toast.LENGTH_SHORT).show();
            }
        };
        ShowPermissionDialog(
                permissionListener,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                "You need to accept the permission to access the Device Gallery. Do you want to accept now?"
        );
    }

    private void OpenGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

        private void ShowPermissionDialog(PermissionListener permissionListener, String[] permissoes, String msg) {
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permisson Denied")
                .setDeniedMessage(msg)
                .setDeniedCloseButtonText("No")
                .setGotoSettingButtonText("Yes")
                .setPermissions(permissoes)
                .check();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bitmap bitmap0;
            Bitmap bitmap1;
            Bitmap bitmap2;

            Uri imageSelected = data.getData();
            String imagelink;

             if (requestCode <= 2) { //galeria

                try {
                    imagelink = imageSelected.toString();
                    switch (requestCode) {
                        case 1:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageSelected);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageSelected);
                                bitmap1 = ImageDecoder.decodeBitmap(source);
                            }
                            imagem1.setImageBitmap(bitmap1);
                            break;
                        case 2:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), imageSelected);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), imageSelected);
                                bitmap2 = ImageDecoder.decodeBitmap(source);
                            }
                            imagem2.setImageBitmap(bitmap2);
                            break;
                    }

                    configUpload(requestCode, imagelink);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void configUpload(int requestCode, String imageLink) {
        int request = 0;
        switch (requestCode) {
            case 0:
            case 3:
                request = 0;
                break;
            case 1:
            case 4:
                request = 1;
                break;
            case 2:
            case 5:
                request = 2;
                break;
        }

        Image imagem = new Image(imageLink, request);
        if (imagemList.size() > 0) {
            boolean check = false;
            for (int i = 0; i < imagemList.size(); i++) {
                if (imagemList.get(i).getIndex() == request) { //listagem
                    check = true;
                }
            }
            if (check) {
                imagemList.set(request, imagem);
            } else {
                imagemList.add(imagem);
            }
        } else {
            imagemList.add(imagem);
        }
           }




    private void showBottomDialog(int requestCode) {
        View modalBottomsheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(modalBottomsheet);
        bottomSheetDialog.show();
        modalBottomsheet.findViewById(R.id.btn_galeria).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            verfifyGalleryPermission(requestCode);
        });
        modalBottomsheet.findViewById(R.id.btn_close).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
        });
    }


    public void registerGuider(View view) {

        if (!firstName.getText().toString().isEmpty()) {
            if (!email.getText().toString().isEmpty()) {
                if (!travel_type.isEmpty()) {
                    if (!contact.toString().isEmpty()) {

                        guider.setFirstName(firstName.getText().toString());
                        guider.setLastName(lastName.getText().toString());
                        guider.setGender(travel_type.toString());
                        guider.setEmail(email.getText().toString());
                        guider.setRegion(region.getText().toString());
                        guider.setLanguage(language.getText().toString());
                        guider.setContact(contact.getText().toString());
                        guider.setWebLink(weblink.getText().toString());
                        guider.setFbLink(fblink.getText().toString());
                        if(imageCheck)
                        {
                            if (imagemList.size() == 2) {
                                for (int i = 0; i < imagemList.size(); i++) {
                                    salvarImagemFirebase(imagemList.get(i), i);
                                }
                            } else {
                                Toast.makeText(this, "Select both of images.", Toast.LENGTH_SHORT).show();
                            }
                        }else { //Edição

                            register.setText("Hold...");
                            savetoFirebase();
                        }

                    } else {
                        contact.requestFocus();
                        contact.setError("Please Enter Contact");
                    }
                } else {
                    Toast.makeText(this, "select a gender", Toast.LENGTH_SHORT).show();
                }
            } else {
                email.requestFocus();
                email.setError("Please enter a valid email.");
            }
        } else {
            firstName.requestFocus();
            firstName.setError("Set First Name.");
        }
    }
    public void savetoFirebase()
    {
        DatabaseReference anuncioPublicoRef = FirebaseHelper.getDatabaseReference()
                .child("Guider")
                .child(FirebaseHelper.getIdFirebase());
        anuncioPublicoRef.setValue(guider);
        register.setText("Register");
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        contact.setText("");
        language.setText("");
        region.setText("");
        weblink.setText("");
        fblink.setText("");
        imagem1.setImageResource(R.drawable.ic_camera);
        imagem2.setImageResource(R.drawable.ic_camera);
        guider=null;

    }
    private void salvarImagemFirebase(Image imagem, int index) {

        register.setText("Hold...");

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("Guider")
                .child(FirebaseHelper.getIdFirebase())
                .child("image" + index + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagem.getPathImage()));

        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {
            if (imageCheck) {
                Log.d("index",String.valueOf(index));
                guider.getUrlImagens().add(index, task.getResult().toString());
            } else {
                guider.getUrlImagens().set(imagem.getIndex(), task.getResult().toString());
            }

            if (imagemList.size() == index + 1){
                savetoFirebase();
            }
        })).addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
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

