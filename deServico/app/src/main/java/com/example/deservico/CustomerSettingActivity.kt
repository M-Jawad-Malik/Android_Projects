package com.example.deservico

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.deservico.databinding.ActivityCustomerSettingBinding
import com.example.deservico.databinding.ActivityDriverMapsBinding
import com.example.deservico.message.LatestMessageActivity
import com.example.deservico.messenger.MessengerUserStruct
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.user_row_new_message.view.*

class CustomerSettingActivity : AppCompatActivity() {
    lateinit var binding: ActivityCustomerSettingBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomerSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchCurrentUser()

    }
    private fun fetchCurrentUser(){
        try{
            val uid= FirebaseAuth.getInstance().uid
            val ref= FirebaseDatabase.getInstance().getReference("/msgUsersInfo/$uid")
            ref.addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    currentUser =snapshot.getValue(MessengerUserStruct::class.java)
                    if(currentUser!=null) {
                        if (currentUser!!.profileImageUrl != "") {
                            Picasso.get().load(currentUser!!.profileImageUrl).into(binding.ivProfilebackground)

                        } else {
                            binding.ivProfilebackground.setImageResource(R.drawable.avatar)
                        }
                        binding.profilename.hint = currentUser!!.username;
                        binding.phoneno.hint = currentUser!!.phoneno;
                    }
                }
                override fun onCancelled(error: DatabaseError) {

                }
            })}catch (e:Exception){
            Log.d("LatMsgActivity",e.message.toString())
        } }
    companion object{
        var currentUser: MessengerUserStruct?=null
    }
}