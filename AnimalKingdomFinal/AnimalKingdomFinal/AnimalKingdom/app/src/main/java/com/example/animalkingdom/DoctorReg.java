package com.example.animalkingdom;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.blackcat.currencyedittext.CurrencyEditText;
import com.example.animalkingdom.helper.FirebaseHelper;
import com.example.animalkingdom.helper.GetMask;
import com.example.animalkingdom.model.Address;
import com.example.animalkingdom.model.Ads;
import com.example.animalkingdom.model.Category;
import com.example.animalkingdom.model.Doctor;
import com.example.animalkingdom.model.Image;
import com.example.animalkingdom.model.Local;
import com.example.animalkingdom.model.User;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.santalu.maskara.widget.MaskEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Retrofit;

public class DoctorReg extends AppCompatActivity {

    private final int REQUEST_CATEGORY = 100;
    private String selectedspecification = "";
    Spinner dynamicSpinner;
    private ImageView image0;
    private ImageView image1;
    private EditText edt_timing,edt_email,firstName,email,contact;
    private String Specification;
    private Button btn_register;
    private ProgressBar progressBar;
    private TextView text_toolbar;
    private Doctor doctor;
    private boolean newAds = true;
    private String currentPhotoPath;
    private CheckBox yes,no;
    private List<Image> imagesList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctorreg);
        initializeComponents();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            doctor = (Doctor) bundle.getSerializable("selectedDoctor");
            if(doctor!=null) {
                configData();
            }
        }
       // getAddress();
        configClicks();

        ArrayAdapter<String> myAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.specification));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        dynamicSpinner.setAdapter(myAdapter);
        dynamicSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Specification = String.valueOf(adapterView.getItemAtPosition(i));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void retrieveProfile() {
     //   progressBar.setVisibility(View.VISIBLE);
        DatabaseReference perfilRef = FirebaseHelper.getDatabaseReference()
                .child("Doctor")
                .child(FirebaseAuth.getInstance().getUid());
        perfilRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                doctor = snapshot.getValue(Doctor.class);
              if(doctor!=null)
              {
                configData();
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void configData() {
        text_toolbar.setText("Editing Ad");
        firstName.setText(doctor.getFirstName());
        selectedspecification = doctor.getSpecification();
        edt_timing.setText(doctor.getTiming());
        edt_email.setText(doctor.getEmail());
        contact.setText(doctor.getContact());
        if(doctor.getService())
        {
            yes.setChecked(true);
            no.setChecked(false);
        }else{
            no.setChecked(true);
            yes.setChecked(false);
        }
        Picasso.get().load(doctor.getImageUrls().get(0)).into(image0);
        Picasso.get().load(doctor.getImageUrls().get(1)).into(image1);
        newAds = false;
        btn_register.setText("Update");

    }

    public void DocdataValidate(View view) {
        String firstname=firstName.getText().toString();
        String Timing=edt_timing.getText().toString();
        String email=edt_email.getText().toString();
        String con=contact.getText().toString();
        String service=yes.isChecked()?"true":"false";

        if (!firstname.isEmpty()) {
            if (!Timing.isEmpty()) {
                if (!email.isEmpty()) {
                    if (!con.isEmpty()) {


                        if (doctor == null) {
                            doctor = new Doctor();
                        }
                        doctor.setId(FirebaseAuth.getInstance().getUid());
                        doctor.setFirstName(firstname);
                        doctor.setTiming(Timing);
                        doctor.setEmail(email);
                        doctor.setContact(con);
                        doctor.setService(service=="true"?true:false);
                        doctor.setSpecification(Specification);
                        if (newAds) {
                            if (imagesList.size() == 2) {
                                for (int i = 0; i < imagesList.size(); i++) {
                                    imageSaveToFirebase(imagesList.get(i), i);
                                }
                            } else {
                                Toast.makeText(this, "Select 2 images for the ad.", Toast.LENGTH_SHORT).show();
                            }
                        }else { //Edição
                            if (imagesList.size() > 0){
                                for (int i = 0; i < imagesList.size(); i++) {
                                    imageSaveToFirebase(imagesList.get(i), i);
                                }
                            }else{
                                btn_register.setText("Hold...");
                                doctor.Save(this, false);
                            }
                        }
                    } else {
                        contact.requestFocus();
                        contact.setError("Enter the contact info.");
                    }
                } else {
                    edt_email.requestFocus();
                    edt_email.setError("Please enter timing");
                 }
            } else {
                edt_timing.requestFocus();
                edt_timing.setError("Please enter timing");
            }
        } else {
            firstName.requestFocus();
            firstName.setError("Set the First Name");
        }
    }

    private void imageSaveToFirebase(Image imagem, int index) {

        btn_register.setText("Hold...");

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("doctor")
                .child(doctor.getId())
                .child("image" + index + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagem.getPathImage()));

        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {
            if (newAds) {
                Log.d("index",String.valueOf(index));
                doctor.getImageUrls().add(index, task.getResult().toString());
            } else {
                doctor.getImageUrls().set(imagem.getIndex(), task.getResult().toString());
            }

            if (imagesList.size() == index + 1){
                doctor.Save(this, newAds);
            }
        })).addOnFailureListener(e -> Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void configClicks() {
        findViewById(R.id.ib_back).setOnClickListener(v -> finish());
        image0.setOnClickListener(v -> showBottomDialog(0));
        image1.setOnClickListener(v -> showBottomDialog(1));
        }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent(int requestCode) {

        int request = 0;
        switch (requestCode) {
            case 0:
                request = 3;
                break;
            case 1:
                request = 4;
                break;
            case 2:
                request = 5;
                break;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
        }
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.AnimalKingdom.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, request);
        }
    }

    public void selectCategory(View view) {
        Intent intent = new Intent(this, CategoriesActivity.class);
        startActivityForResult(intent, REQUEST_CATEGORY);
    }

//    private void getAddress() {
//        //configCep();
//
//        DatabaseReference addressRef = FirebaseHelper.getDatabaseReference()
//                .child("address")
//                .child(FirebaseHelper.getIdFirebase());
//        addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    userAddress = snapshot.getValue(Address.class);
//                    edt_cep.setText(userAddress.getZipcode());
//                    local=new Local();
//                    local.setZipCode(userAddress.getZipcode());
//                    local.setState(userAddress.getProvince());
//                    local.setDistrict(userAddress.getDistrict());
//                    local.setLocaltimezone(userAddress.getCity());
//                    progressBar.setVisibility(View.GONE);
//                }else{
//                    finish();
//                    startActivity(new Intent(getBaseContext(), AddressActivity.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void showBottomDialog(int requestCode) {
        View modalBottomsheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(modalBottomsheet);
        bottomSheetDialog.show();
        modalBottomsheet.findViewById(R.id.btn_galeria).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            galleryPermissionCheck(requestCode);
        });
        modalBottomsheet.findViewById(R.id.btn_close).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
       });
    }

      private void configUpload(int requestCode, String imagePath) {
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

        Image imagem = new Image(imagePath, request);
        if (imagesList.size() > 0) {
            boolean found = false;
            for (int i = 0; i < imagesList.size(); i++) {
                if (imagesList.get(i).getIndex() == request) { //listagem
                    found = true;
                }
            }
            if (found) {
                imagesList.set(request, imagem);
            } else {
                imagesList.add(imagem);
            }
        } else {
            imagesList.add(imagem);
        }
        Log.i("INFOTEST", "configUpload: " + imagesList.size());
    }

    private void galleryPermissionCheck(int requestCode) {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openGallery(requestCode);
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(DoctorReg.this, "Permisson Denied", Toast.LENGTH_SHORT).show();
            }
        };
        showPermissionDialog(
                permissionListener,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                "You need to accept the permission to access the Device Gallery. Do you want to accept now?"
        );
    }

    private void openGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void showPermissionDialog(PermissionListener permissionListener, String[] permissoes, String msg) {
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permisson Denied")
                .setDeniedMessage(msg)
                .setDeniedCloseButtonText("No")
                .setGotoSettingButtonText("Yes")
                .setPermissions(permissoes)
                .check();
    }
    private void initializeComponents() {
        text_toolbar = findViewById(R.id.text_toolbar);
        text_toolbar.setText("New Registeration");
        dynamicSpinner = (Spinner) findViewById(R.id.dropStatus);
        image0 = findViewById(R.id.imagem0);
        image1 = findViewById(R.id.imagem1);
        firstName=findViewById(R.id.edt_firstname);
        edt_timing = findViewById(R.id.edt_timing);
        edt_email = findViewById(R.id.edt_email);
      //  progressBar = findViewById(R.id.progressBar);
        contact = findViewById(R.id.edt_contact);
        btn_register = findViewById(R.id.btn_register);
        yes=findViewById(R.id.simpleCheckBoxYes);
        no=findViewById(R.id.simpleCheckBoxNo);
        retrieveProfile();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bitmap bitmap0;
            Bitmap bitmap1;
            Bitmap bitmap2;

            Uri selectedImage = data.getData();
            String imagePath;

            if (requestCode <= 2) { //galeria

                try {
                    imagePath = selectedImage.toString();
                    switch (requestCode) {
                        case 0:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap0 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedImage);
                                bitmap0 = ImageDecoder.decodeBitmap(source);
                            }
                            image0.setImageBitmap(bitmap0);
                            break;
                        case 1:
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmap1 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                            } else {
                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedImage);
                                bitmap1 = ImageDecoder.decodeBitmap(source);
                            }
                            image1.setImageBitmap(bitmap1);
                            break;
//                        case 2:
//                            if (Build.VERSION.SDK_INT < 28) {
//                                bitmap2 = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
//                            } else {
//                                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedImage);
//                                bitmap2 = ImageDecoder.decodeBitmap(source);
//                            }
//                            image2.setImageBitmap(bitmap2);
//                            break;
                    }

                    configUpload(requestCode, imagePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else { //camera
                File file = new File(currentPhotoPath);
                imagePath = String.valueOf(file.toURI());

                switch (requestCode) {
                    case 3:
                        image0.setImageURI(Uri.fromFile(file));
                        break;
                    case 4:
                        image1.setImageURI(Uri.fromFile(file));
                        break;
//                    case 5:
//                        image2.setImageURI(Uri.fromFile(file));
//                        break;
                }

                configUpload(requestCode, imagePath);
            }
        }
    }
}