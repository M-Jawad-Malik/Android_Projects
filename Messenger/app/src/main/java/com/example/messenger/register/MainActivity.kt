    package com.example.messenger.register

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import android.Manifest
import android.app.Activity
import android.net.Uri
import android.os.Parcelable
import android.provider.MediaStore
import com.example.messenger.R
import com.example.messenger.message.LatestMessageActivity
import com.example.messenger.register.LoginActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signupBtn.setOnClickListener {

            PerformRegister()
            uploadImagetoFirebaseStorage()
        }


        txtSingIn.setOnClickListener {
            val intent=Intent(this@MainActivity, LoginActivity::class.java);
            startActivity(intent);
        }

        profilephoto.setOnClickListener {
            if(isRequestPermissionAllowed()){
                val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, GALLERY)
            }else{
                requestForReadStoragePermission()
            }
        }

    }
    private fun PerformRegister(){
       val email=txtemail.text.toString().trim(){it<=' '}
        val password=txtpassword.text.toString().trim(){it<=' '}
        if(email.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this,"Please Fill the Box",Toast.LENGTH_LONG).show()
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
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@MainActivity, task.exception!!.toString(),
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    private  fun uploadImagetoFirebaseStorage(){
      if(selectedPhotoUri==null)return
        val filename=UUID.randomUUID().toString()
            val ref=FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Toast.makeText(this,"Photo saved ${it.metadata?.path}",Toast.LENGTH_LONG).show()
                ref.downloadUrl.addOnSuccessListener {
                    saveUserToFirebaseDatabase(it.toString())
                }

            }
            .addOnFailureListener{
                //so some logging here
            }


    }
    private fun saveUserToFirebaseDatabase(profileImageUrl: String){

        val uid=FirebaseAuth.getInstance().uid?:""
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
       val user= User(uid,txtemail.text.toString(),txtusername.text.toString(),profileImageUrl)
        ref.setValue(user)
            .addOnSuccessListener {
                Toast.makeText(this,"Finally we saved user FireStore Database",Toast.LENGTH_SHORT).show()
               val intent=Intent(this, LatestMessageActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener{
                Toast.makeText(this,"Oye",Toast.LENGTH_SHORT).show()
            }
    }
    @Parcelize
    class User(val uid:String,val email:String,val username:String,val profileImageUrl:String):Parcelable{
        constructor():this("","","","")
    }
     public fun checkPermission(){
         if(ContextCompat.checkSelfPermission(this,
                 android.Manifest.permission.INTERNET)== PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(this,
                 android.Manifest.permission.ACCESS_NETWORK_STATE)== PackageManager.PERMISSION_GRANTED)
         {
             Toast.makeText(this,"You already have Access",Toast.LENGTH_LONG).show()

         }else{
             //Request PermissionA
             ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.INTERNET,android.Manifest.permission.ACCESS_NETWORK_STATE), Network_Internet)

         }
     }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
        ) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            if(requestCode== Network_Internet)
            {
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    Toast.makeText(this,"Permission Granted for Camera",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this,"Permission Denied for Camera",Toast.LENGTH_SHORT).show()
                }

            }
            if(requestCode== READ_STORAGE_PERMISSION)
            {
                if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                   Toast.makeText(this,"Permission has been Granted",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,"Storage Permission Denied",Toast.LENGTH_SHORT).show()
                }
            }
        }
    companion object//Data class contain variable constant
    {
        private const val CAMERA_PERMISSION_CODE=1 //to check which permission i am looking for
        private const val FINE_LOCATION_PERMISSION_CODE=2
        private const  val Network_Internet=3
        private val READ_STORAGE_PERMISSION=1
        private val GALLERY=2
    }
    //Storage Permisson Code Starts here
    private fun isRequestPermissionAllowed():Boolean{
        val result=ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }
    private fun requestForReadStoragePermission(){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE.toString())){
                Toast.makeText(this,"Request Required to Upload Profile Photo",Toast.LENGTH_LONG).show()
            }
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION)
    }

    var selectedPhotoUri:Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== GALLERY)
        {
            if(resultCode==Activity.RESULT_OK)
            {
                try{
                    if(data!!.data!=null)
                    {
                        selectedPhotoUri=data.data
                       /* iv_background.isVisible=true
                        val bitmap=MediaStore.Images.Media.getBitmap(contentResolver,data.data)
                        val bitmapDrawable=BitmapDrawable(bitmap)*/
                        iv_background.setImageURI(data.data)
                    }
                }catch (e:Exception)
                {
                    e.printStackTrace()
                }
            }
        }
    }
    //Storage Permission Code Ends here

/*
   public void awe(){

    var fAuth=FirebaseAuth.getInstance()
    fAuth.createUserWithEmailAndPassword("jawaddmuhammad@gmail.com","548246J@WAD865316805")
    .addOnCompleteListener {
        if(!it.isSuccessful) {
            Toast.makeText(this,"Fail",Toast.LENGTH_SHORT).show()
            return@addOnCompleteListener
        }
        Toast.makeText(this,"Successfully Created ${it.result?.user?.uid}", Toast.LENGTH_SHORT).show()
    }*/


}