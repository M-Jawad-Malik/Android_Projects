package com.example.deservico

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.deservico.databinding.ActivityServProSignUp2Binding
import com.example.deservico.messenger.MessengerUserStruct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class ServProSignUp2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var  binding: ActivityServProSignUp2Binding;
    var arr:ArrayList<String>?= ArrayList();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityServProSignUp2Binding.inflate(layoutInflater);
        setContentView(binding.root);

        val spinner: Spinner = findViewById(R.id.spinner)
        Utils.blackIconStatusBar(this,R.color.Light_background)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array .planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
binding.profilephoto.setOnClickListener {
    if(isRequestPermissionAllowed()){
        val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, GALLERY)
    }else{
        requestForReadStoragePermission()
    }

    arr=intent.getStringArrayListExtra( "val");
    binding.SerProSignUpbtn.setOnClickListener {
        PerformRegister()

        //saveUserToFirebaseDatabase(selectedPhotoUri.toString())
    }
}


    }
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== READ_STORAGE_PERMISSION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission has been Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this,"Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun isRequestPermissionAllowed():Boolean{
        val result= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }
    private fun requestForReadStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE.toString())){
            Toast.makeText(this,"Request Required to Upload Profile Photo",Toast.LENGTH_LONG).show()
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION)
    }
    var selectedPhotoUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== GALLERY)
        {
            if(resultCode== Activity.RESULT_OK)
            {
                try{
                    if(data!!.data!=null)
                    {
                        selectedPhotoUri=data.data
                        /* iv_background.isVisible=true
                         val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,data.data)
                         val bitmapDrawable=BitmapDrawable(bitmap)*/
                        binding.ivBackground.setImageURI(data.data)
                    }
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }
    }
    companion object//Data class contain variable constant
    {
        private val READ_STORAGE_PERMISSION=1
        private val GALLERY=2
    }
    //Saving User to Database


    private fun PerformRegister(){
        val email=arr!![0].toString().trim(){it<=' '}
        val password=arr!![1].toString().trim(){it<=' '}
        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this,"Please Enter Email or Password First",Toast.LENGTH_LONG).show()
            return
        }
        if(selectedPhotoUri==null) {
            Toast.makeText(this,"Please Select an Image",Toast.LENGTH_LONG).show()
            return
        }
        auth= FirebaseAuth.getInstance()
        //FirebaseAuth to create  a user with email and password
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "You are registered Successfully.",
                        Toast.LENGTH_SHORT).show()
                    uploadImagetoFirebaseStorage()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, task.exception!!.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private  fun uploadImagetoFirebaseStorage(){

        val filename= UUID.randomUUID().toString()
        val ref=FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        saveUserToFirebaseDatabase(it.toString())
                    }


                }
                .addOnFailureListener{
                    Toast.makeText(this,it.toString(),Toast.LENGTH_LONG).show()
                //so some logging here
                }


    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String){
        val uid= auth!!.currentUser!!.uid?:""
        //Messenger Firebase
        val refMessenger=FirebaseDatabase.getInstance().getReference("/msgUsersInfo/$uid")
        val userMessenger= MessengerUserStruct(uid!!,arr!![0],arr!![2],profileImageUrl,binding.phoneno.text.toString())
        refMessenger.setValue(userMessenger)
            .addOnSuccessListener {

            }
            .addOnFailureListener{
            }
        ///////////////
        val ref= FirebaseDatabase.getInstance().getReference("/Users").child("/SerPro").child("/${binding.spinner.selectedItem.toString()}/$uid")
        val user= SerProStruct(uid,arr!![0],arr!![2],profileImageUrl,binding.phoneno.text.toString(),binding.spinner.selectedItem.toString(),"")
        ref.setValue(user)
                .addOnSuccessListener {
                    val intent=Intent(this, DriverMapsActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to Save User",Toast.LENGTH_LONG).show()
                }
        val refSerType= FirebaseDatabase.getInstance().getReference("/SerType/$uid/serType")
        refSerType.setValue(binding.spinner.selectedItem.toString())
    }





}
