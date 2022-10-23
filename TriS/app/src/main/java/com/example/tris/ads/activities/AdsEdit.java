package com.example.tris.ads.activities;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.example.tris.HomePage;
import com.example.tris.R;
import com.example.tris.ads.model.Ads;
import com.example.tris.ads.model.AdsCategoryList;
import com.example.tris.ads.model.Category;
import com.example.tris.ads.model.Event;
import com.example.tris.ads.model.EventCategoryList;
import com.example.tris.ads.model.FirebaseHelper;
import com.example.tris.ads.model.Image;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdsEdit extends AppCompatActivity {
    ScrollView scrl1,scrl2;
    private final int REQUEST_AdsCATEGORY = 100;
    private final int REQUEST_EventsCATEGORY = 101;
    private String selectedcategory = "";
    private ProgressBar progressBar;
    private TextView txt_local;
    private TextView text_toolbar;
    ConstraintLayout adreg,eventreg;
    Button create_event_btn,create_ad_btn;
    TextInputEditText title,title1, desc,desc1,price,entryfee,discount,address,adressEvent;
    Button category,category1;
    ImageView image0,image01,image1,image10,image2,image20;
    private List<Image> imagesList = new ArrayList<>();
    private Ads ads;
    private Event event;
    private boolean newAds = true;
    private boolean newEvent = true;
    private String currentPhotoPath;
    String userAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adsregistration);
        initializeComponents();
        configClicks();
        Bundle bundle = getIntent().getExtras();

        if (bundle != null){
            ads = (Ads) bundle.getSerializable("selectedAds");
            event=(Event) bundle.getSerializable("selectedEvent");
            if(ads!=null){
                configDataAd();
                scrl1.setVisibility(View.VISIBLE);
                scrl2.setVisibility(View.GONE);
            }
            if(event!=null)
            { scrl1.setVisibility(View.GONE);
                scrl2.setVisibility(View.VISIBLE);
                configDataEvent();
            }
        }
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(AdsEdit.this,R.color.white));

    }
    public void initializeComponents()
    {   scrl1=findViewById(R.id.scrl1);
        scrl2=findViewById(R.id.scrl2);
        adreg=findViewById(R.id.ad_reg);
        eventreg=findViewById(R.id.event_reg);
        create_event_btn=findViewById(R.id.create_event_btn);
        create_ad_btn=findViewById(R.id.create_ad_btn);
        title=findViewById(R.id.title);
        title1=findViewById(R.id.title1);
        category=findViewById(R.id.category);
        category1=findViewById(R.id.category1);
        desc=findViewById(R.id.description);
        desc1=findViewById(R.id.description1);
        price=findViewById(R.id.price);
        entryfee=findViewById(R.id.price1);
        discount=findViewById(R.id.discount);
        address=findViewById(R.id.address);
        adressEvent=findViewById(R.id.address1);
        image0=findViewById(R.id.image0);
        image1=findViewById(R.id.image1);
        image01=findViewById(R.id.image01);
        image10=findViewById(R.id.image10);
        image2=findViewById(R.id.image2);
        image20=findViewById(R.id.image21);
    }
    private void configDataAd() {
        //text_toolbar.setText("Editing Ad");
        selectedcategory = ads.getCategory();
        category.setText(selectedcategory);
        title.setText(ads.getTitle());
        desc.setText(ads.getDescription());
        price.setText(String.valueOf(ads.getPrice()));
        discount.setText(String.valueOf(ads.getDiscount()));
        address.setText(ads.getAddress());
        Picasso.get().load(ads.getImageUrls().get(0)).into(image0);
        Picasso.get().load(ads.getImageUrls().get(1)).into(image1);
        Picasso.get().load(ads.getImageUrls().get(2)).into(image2);
        newAds = false;
    }
    private void configDataEvent() {
//        text_toolbar.setText("Editing Event");
        selectedcategory = event.getCategory();
        category1.setText(selectedcategory);
        title1.setText(event.getTitle());
        desc1.setText(event.getDescription());
        entryfee.setText(String.valueOf(event.getPrice()));
        adressEvent.setText(event.getAddress());
        Picasso.get().load(event.getUrlImages().get(0)).into(image01);
        Picasso.get().load(event.getUrlImages().get(1)).into(image10);
        Picasso.get().load(event.getUrlImages().get(2)).into(image20);
        newAds = false;
    }
    public void EventdataValidate() {
        String title = title1.getText().toString();
        String description = desc1.getText().toString();
        String category_val=category1.getText().toString();
        String price_val=entryfee.getText().toString();
        String Address_val=adressEvent.getText().toString();

        if (!title.isEmpty()) {
            if (!category_val.isEmpty()) {
                if (!description.isEmpty()) {
                    if (!price_val.isEmpty()) {
                        if (!Address_val.isEmpty()) {
                            if (event == null) {
                                event = new Event();
                            }
                            event.setUserId(FirebaseHelper.getIdFirebase());
                            event.setTitle(title);
                            event.setCategory(category_val);
                            event.setDescription(description);
                            event.setPrice(Double.valueOf(price_val));
                            event.setAddress(Address_val);
                            event.setApproved("");
                            ArrayList<String> str = new ArrayList<>();
                            str.add("somme");
                            str.add("some");
                            str.add("some");
                            event.setUrlImages(str);

                            if (newEvent) {
                                if (imagesList.size() == 3) {

                                    for (int i = 0; i < imagesList.size(); i++) {
                                        imageEventSaveToFirebase(imagesList.get(i), i);
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Select 3 images for the event.", Toast.LENGTH_SHORT).show();
                                }
                            } else { //Edição
                                if (imagesList.size() > 0) {
                                    for (int i = 0; i < imagesList.size(); i++) {
                                        imageEventSaveToFirebase(imagesList.get(i), i);
                                    }
                                } else {
                                    create_event_btn.setText("Hold...");
                                    event.Save(this, false);
                                }
                            }

                        }    else{
                            adressEvent.requestFocus();
                            adressEvent.setError("Set the address.");
                        }
                    }    else{
                        entryfee.requestFocus();
                        entryfee.setError("Set the price.");
                    }
                } else {
                    desc1.requestFocus();
                    desc1.setError("Enter the description.");
                }
            } else {
                Toast.makeText(getApplicationContext(), "select a category.", Toast.LENGTH_SHORT).show();
            }

        } else {
            title1.requestFocus();
            title1.setError("Set the title.");
        }
    }
    public void AddataValidate() {
        String title1 = title.getText().toString();
        String description = desc.getText().toString();
        String category_val=category.getText().toString();
        String price_val=price.getText().toString();
        String discount_val=discount.getText().toString();
        String Address_val=address.getText().toString();


        if (!title1.isEmpty()) {
            if (!category_val.isEmpty()) {
                if (!description.isEmpty()) {
                    if (!price_val.isEmpty()) {
                        if (!discount_val.isEmpty()) {
                            if (!Address_val.isEmpty()) {

                                if (ads == null) {
                                    ads = new Ads();
                                }
                                ads.setUserId(FirebaseHelper.getIdFirebase());
                                ads.setTitle(title1);
                                ads.setCategory(category_val);
                                ads.setDescription(description);
                                ads.setPrice(Double.valueOf(price_val));
                                ads.setDiscount(Double.valueOf(discount_val));
                                ads.setAddress(Address_val);
                                ads.setApproved("");
                               if(newAds) {
                                   ArrayList<String> str = new ArrayList<>();
                                   str.add("Some");
                                   str.add("for");
                                   str.add("For");
                                   ads.setImageUrls(str);
                               }
                                if (newAds) {
                                    if (imagesList.size() == 3) {

                                        for (int i = 0; i < imagesList.size(); i++) {
                                            imageSaveToFirebase(imagesList.get(i), i);
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Select 3 images for the ad.", Toast.LENGTH_SHORT).show();
                                    }
                                } else { //Edição
                                    if (imagesList.size() > 0) {
                                        for (int i = 0; i < imagesList.size(); i++) {
                                            imageSaveToFirebase(imagesList.get(i), i);
                                        }
                                    } else {
                                        create_ad_btn.setText("Hold...");
                                        ads.Save(this, false);
                                    }
                                }
                            }    else{
                                discount.requestFocus();
                                discount.setError("Set the discount.");
                            }
                        }    else{
                            address.requestFocus();
                            address.setError("Set the address.");
                        }
                    }    else{
                        price.requestFocus();
                        price.setError("Set the price.");
                    }
                } else {
                    desc.requestFocus();
                    desc.setError("Enter the description.");
                }
            } else {
                Toast.makeText(getApplicationContext(), "select a category.", Toast.LENGTH_SHORT).show();
            }

        } else {
            title.requestFocus();
            title.setError("Set the title.");
        }
    }
    private void imageEventSaveToFirebase(Image imagem, int index) {

        create_event_btn.setText("Hold...");

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("events")
                .child(event.getId())
                .child("image" + index + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagem.getPathImage()));

        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {
            if (newEvent) {
                Toast.makeText(getApplicationContext(),String.valueOf(event.getUrlImages().size()),Toast.LENGTH_LONG).show();
                event.getUrlImages().set(index, task.getResult().toString());
            } else {
                event.getUrlImages().set(imagem.getIndex(), task.getResult().toString());
            }

            if (imagesList.size() == index + 1){
                event.Save(this, newEvent);
            }
        })).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void imageSaveToFirebase(Image imagem, int index) {

        create_ad_btn.setText("Hold...");

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("images")
                .child("ads")
                .child(ads.getId())
                .child("image" + index + ".jpeg");

        UploadTask uploadTask = storageReference.putFile(Uri.parse(imagem.getPathImage()));

        uploadTask.addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnCompleteListener(task -> {
            if (newAds) {
                Toast.makeText(getApplicationContext(),String.valueOf(ads.getImageUrls().size()),Toast.LENGTH_LONG).show();
                ads.getImageUrls().set(index, task.getResult().toString());
            } else {
                ads.getImageUrls().set(imagem.getIndex(), task.getResult().toString());
            }

            if (imagesList.size() == index + 1){
                ads.Save(this, newAds);
            }
        })).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    public void selectAdsCategory() {
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        intent.putExtra("all", "ads");
        startActivityForResult(intent, REQUEST_AdsCATEGORY);
    }
    public void selectEventsCategory() {
        Intent intent = new Intent(getApplicationContext(), CategoriesActivity.class);
        intent.putExtra("all", "events");
        startActivityForResult(intent, REQUEST_EventsCATEGORY);
    }
    private void getAddress() {
        //configCep();

//        DatabaseReference addressRef = FirebaseHelper.getDatabaseReference()
//                .child("users")
//                .child(FirebaseHelper.getIdFirebase());
//        addressRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//				userAddress =(String) snapshot.getValue();
////					edt_cep.setText(userAddress.getZipcode());
////					progressBar.setVisibility(View.GONE);
//                }else{
////                    finish();
////					startActivity(new Intent(getBaseContext(), AddressActivity.class));
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }
    private void showBottomDialog(int requestCode) {
        View modalBottomsheet = getLayoutInflater().inflate(R.layout.layout_bottom_sheet, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(AdsEdit.this, R.style.BottomSheetDialog);
        bottomSheetDialog.setContentView(modalBottomsheet);
        bottomSheetDialog.show();

//		modalBottomsheet.findViewById(R.id.btn_camera).setOnClickListener(v -> {
//			bottomSheetDialog.dismiss();
//			verificaPermissaoCamera(requestCode);
//		});

        modalBottomsheet.findViewById(R.id.btn_galeria).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            galleryPermissionCheck(requestCode);
        });

        modalBottomsheet.findViewById(R.id.btn_close).setOnClickListener(v -> {
            bottomSheetDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Closing", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(), "Permisson Denied", Toast.LENGTH_SHORT).show();
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            Bitmap bitmap0;
            Bitmap bitmap1;
            Bitmap bitmap2;

            Uri selectedImage = data.getData();
            String imagePath;

            if (requestCode == REQUEST_AdsCATEGORY) {

                Category ch_category = (Category) data.getSerializableExtra("selectedCategory");
                selectedcategory = ch_category.getName();
                for(int i = 0; i< AdsCategoryList.getList().size(); i++)
                {
                    if(AdsCategoryList.getList().get(i).getName().contains(selectedcategory) )
                    {

                        category.setText(selectedcategory);
                        desc.requestFocus();
                    }}
                //Add in different one

//				btn_category.setText(selectedcategory);
            } else if (requestCode <= 2) { //galeria
                if(scrl2.getVisibility()==View.GONE&&scrl1.getVisibility()==View.VISIBLE) {
                    try {
                        imagePath = selectedImage.toString();
                        switch (requestCode) {
                            case 0:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap0 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap0 = ImageDecoder.decodeBitmap(source);
                                }
                                image0.setImageBitmap(bitmap0);
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap1 = ImageDecoder.decodeBitmap(source);
                                }
                                image1.setImageBitmap(bitmap1);
                                break;
                            case 2:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap2 = ImageDecoder.decodeBitmap(source);
                                }
                                image2.setImageBitmap(bitmap2);
                                break;
                        }

                        configUpload(requestCode, imagePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        imagePath = selectedImage.toString();
                        switch (requestCode) {
                            case 0:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap0 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap0 = ImageDecoder.decodeBitmap(source);
                                }
                                image01.setImageBitmap(bitmap0);
                                break;
                            case 1:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap1 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap1 = ImageDecoder.decodeBitmap(source);
                                }
                                image10.setImageBitmap(bitmap1);
                                break;
                            case 2:
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap2 = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), selectedImage);
                                } else {
                                    ImageDecoder.Source source = ImageDecoder.createSource(getApplicationContext().getContentResolver(), selectedImage);
                                    bitmap2 = ImageDecoder.decodeBitmap(source);
                                }
                                image20.setImageBitmap(bitmap2);
                                break;
                        }

                        configUpload(requestCode, imagePath);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if(requestCode==REQUEST_EventsCATEGORY)
            {
                Category ch_category = (Category) data.getSerializableExtra("selectedCategory");
                selectedcategory = ch_category.getName();
                //Add in different one
                for(int i = 0; i< EventCategoryList.getList().size(); i++) {
                    if (EventCategoryList.getList().get(i).getName().contains(selectedcategory)) {
                        category1.setText(selectedcategory);
                        desc1.requestFocus();
                    }
                }
            }
        }
    }
    private void configClicks() {
        create_ad_btn.setOnClickListener(v->AddataValidate());
        create_event_btn.setOnClickListener(v->EventdataValidate());
        category.setOnClickListener(v->selectAdsCategory());
        category1.setOnClickListener(v->selectEventsCategory());
        image0.setOnClickListener(v -> showBottomDialog(0));
        image1.setOnClickListener(v -> showBottomDialog(1));
        image2.setOnClickListener(v -> showBottomDialog(2));
        image01.setOnClickListener(v -> showBottomDialog(0));
        image10.setOnClickListener(v -> showBottomDialog(1));
        image20.setOnClickListener(v -> showBottomDialog(2));
    }
}
