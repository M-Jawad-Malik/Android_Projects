package com.example.resortingapp;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import resortingapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceDeatil extends AppCompatActivity implements LocationPickerDialog.OnLocationSelectedListener {
    private String Document_img1 = "";
    private ImageView mapbtn;
    TextView airquality;
    TextView catar;
    TextView catur;
    TextView caten;
    TextView desur;
    TextView desen;
    TextView desar;
    TextView nmur;
    TextView nmen;
    TextView nmar;
    TextView recage;
    TextView recsesn;
    TextView rectime;
    TextView reckey;
    TextView entrncfee;
    TextView food;
    TextView transpot;
    ImageView profile_img_btn;
    ImageView profile_img;
    TextView saveplc_detail;
    Button cancel_temp_dialog_btn, cancel_airquality_dialog_btn;
    TextView min_temp, max_temp, humidity_tv, wind_tv;
    private final String APIKEY = "30fda06e369e31b5a53073d028f66f90";
    ListView activitiesListView;
    private OpenWeatherApi openWeatherApi;
    private double tempApiResult, min, max, speed;
    private int humidity;


    private ConstraintLayout emptyParent;
    private final String[] permissions = new String[]{
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    final int RC_LOCATION = 0034;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_deatil);
        airquality = findViewById(R.id.airquality);
        catur = findViewById(R.id.catur);
        caten = findViewById(R.id.caten);
        catar=findViewById(R.id.catar);
        desur = findViewById(R.id.desur);
        desen = findViewById(R.id.desen);
        desar=findViewById(R.id.desar);
        nmur = findViewById(R.id.nmur);
        nmen = findViewById(R.id.nmen);
        nmar=findViewById(R.id.nmar);
        recage = findViewById(R.id.recage);
        recsesn = findViewById(R.id.recsesn);
        rectime = findViewById(R.id.rectime);
        reckey = findViewById(R.id.recomkey);
        entrncfee = findViewById(R.id.entrncfee);
        food = findViewById(R.id.food);
        saveplc_detail=findViewById(R.id.saveplc_detail);
        ApiConfig();
        transpot = findViewById(R.id.transpot);
        profile_img_btn = findViewById(R.id.prof_img_btn);
        profile_img = findViewById(R.id.prof_img);
        profile_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
                // uploadImage();
            }
        });
        saveplc_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
                DocumentReference reference = mFirebaseFirestore.collection("places").document(nmen.getText().toString());
                try {


                reference.set(new Place(new ArrayList<ActivityClass>(),Integer.parseInt(airquality.getText().toString().subSequence(13,14).toString()), catar.getText().toString(), caten.getText().toString(), catur.getText().toString(), new Cost( Integer.parseInt(entrncfee.getText().toString()), Integer.parseInt(food.getText().toString()), Integer.parseInt(transpot.getText().toString()), new ArrayList<Integer>()), desar.getText().toString(), desen.getText().toString(), desur.getText().toString(), getImgUrl(selectedImage).toString(), latitude, longitude, nmar.getText().toString(), nmen.getText().toString(), nmur.getText().toString(), recage.getText().toString(), Integer.parseInt(recsesn.getText().toString()), Integer.parseInt(rectime.getText().toString()), reckey.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getApplicationContext(),"Place Detail has been Successfully Saveed",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(getApplicationContext(),Tour.class);
                            startActivity(intent);
                        }
                    }
                });}catch(Exception c)
                {
Toast.makeText(getApplicationContext(),c.toString(),Toast.LENGTH_LONG).show();
                }

            }
        });


        mapbtn = findViewById(R.id.mapbtn);
        mapbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SDK_INT >= 23) {
                    if (!checkPermissions()) {
                        ActivityCompat.requestPermissions(PlaceDeatil.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, RC_LOCATION);

                    } else {
                        showMapDialog();
                    }
                } else {
                    showMapDialog();

                }
            }
        });


    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();

                        }
                    }
                });
        someActivityResultLauncher.launch(intent);


    }

    private boolean checkPermissions() {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void showMapDialog() {

        new LocationPickerDialog().show(
                getSupportFragmentManager(), "TAG_LOCATION");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case RC_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMapDialog();
                } else {
                    Toast.makeText(PlaceDeatil.this, "Location permission required", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2297:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                Uri uri = Uri.parse(f.getPath());
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 1);

            default:
        }
    }

    ////    public void onLocationSelected(Double lat, Double lng) {
////
////        if (weatherController != null){
////            weatherController.getWeatherData(lat,lng);
////            showProgressBar();
////
////        }
//
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (weatherController != null){
//            weatherController.onActivityStop();
//            hideProgressBar();
//        }
    }

    //
    double latitude=0;
    double longitude=0;
    @Override
    public void onLocationSelected(Double lat, Double lng) {

//        if (weatherController != null){
//            weatherController.getWeatherData(lat,lng);
//            showProgressBar();
//
//        }
        if (LocationPickerDialog.latlngCond == true) {

           // Toast.makeText(this, "Lat:" + lat + " lng: " + lng, Toast.LENGTH_LONG).show();

            getTempApi(lat, lng);
            latitude=lat;
            longitude=lng;

        }

    }

    //Wheather Data
    private void getTempApi(double latitude, double longitude) {
        Call<JsonObject> call = null;
        try {
            call = openWeatherApi.getTemp(latitude, longitude, APIKEY);
        } catch (Exception ce) {
            Toast.makeText(this, ce.toString(), Toast.LENGTH_LONG).show();
        }
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (!response.isSuccessful()) {
                    Log.d("ErrorWheather", "Code: " + response.code());
                    return;
                }
                JsonObject root = response.body();

                JsonObject main = root.getAsJsonObject("main");
                JsonElement element_temp = main.get("temp");
                JsonElement element_min_temp = main.get("temp_min");
                JsonElement element_max_temp = main.get("temp_max");
                JsonElement element_humidity = main.get("humidity");

                JsonObject wind = root.getAsJsonObject("wind");
                JsonElement element_speed = wind.get("speed");

                tempApiResult = element_temp.getAsDouble() - 273.15;
                min = element_min_temp.getAsDouble() - 273.15;
                max = element_max_temp.getAsDouble() - 273.15;
                humidity = element_humidity.getAsInt();
                speed = element_speed.getAsDouble() * 3.6;
                airquality.setText("Temperature: " + (int) tempApiResult +
                        "\nMinimum Temperature: " + (int) min +
                        "\nMaximum Temperature: " + (int) max +
                        "\nHumidt: " + (int) humidity +
                        "\nSpeed: " + (int) speed);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("ErrWheather", "onFailure: " + t.getMessage());
            }
        });

    }

    public void ApiConfig() {
        //retrofit config
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.openweathermap.org/").addConverterFactory(GsonConverterFactory.create()).build();
        openWeatherApi = retrofit.create(OpenWeatherApi.class);
    }
    //image upload code

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(PlaceDeatil.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                        Uri uri = Uri.parse(f.getPath());
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 1);
                    } else {
                        ActivityCompat.requestPermissions(PlaceDeatil.this, new String[]{Manifest.permission.CAMERA}, 2297);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    Uri selectedImage=null;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
          //  Toast.makeText(getApplicationContext(), "eee".toString(), Toast.LENGTH_LONG).show();
            if (requestCode == 1) {
                File f = new File(Environment.getExternalStorageDirectory().toString());
                for (File temp : f.listFiles()) {
                    if (temp.getName().equals("temp.jpg")) {
                        f = temp;
                  //      Toast.makeText(getApplicationContext(), f.getPath().toString(), Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                try {
                    Bitmap bitmap;
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), bitmapOptions);
                    bitmap = getResizedBitmap(bitmap, 400);
                    profile_img.setImageBitmap(bitmap);
                    BitMapToString(bitmap);
                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    f.delete();
                    OutputStream outFile = null;
                    File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {

                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
            } else if (requestCode == 2) {
                try {
                    selectedImage = data.getData();
                    String[] filePath = {MediaStore.Images.Media.DATA};
                    Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePath[0]);
                    String picturePath = c.getString(columnIndex);

                    c.close();
                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                 //   Toast.makeText(this, thumbnail.toString(), Toast.LENGTH_LONG).show();

                    thumbnail = getResizedBitmap(thumbnail, 400);
                    Log.w("path of image from gallery......******************.........", picturePath + "");
                    profile_img.setImageBitmap(thumbnail);
                    BitMapToString(thumbnail);
                    uploadImg(selectedImage);

                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();

                }
            } else if (requestCode == 2296) {
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (Environment.isExternalStorageManager()) {
                        // perform action when allow permission success
                        selectImage();
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } else {
            Toast.makeText(getApplicationContext(), "eee1".toString(), Toast.LENGTH_LONG).show();
        }

    }

    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(PlaceDeatil.this, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(PlaceDeatil.this, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(PlaceDeatil.this, new String[]{WRITE_EXTERNAL_STORAGE}, 2296);
        }
    }

    UploadTask uploadTask = null;

    //upload image to firebase
    void uploadImg(Uri file) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        uploadTask = riversRef.putFile(file);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }
    Uri downloadUri=null;
    Uri getImgUrl(Uri file) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final StorageReference ref = storageRef.child("images/" + file.getLastPathSegment());
        uploadTask = ref.putFile(file);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                  downloadUri   = task.getResult();

                } else {
                    // Handle failures
                    // ...
                }
            }
        });
        return downloadUri;
    }

}
