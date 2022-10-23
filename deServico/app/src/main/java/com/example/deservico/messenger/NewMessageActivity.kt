package com.example.deservico.message

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deservico.R
import com.example.deservico.message.ChatLogActivity
import com.example.deservico.messenger.MessengerUserStruct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*


class NewMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_new_message
        )
        supportActionBar?.title="Select User"
        recyclerview_newmessage.layoutManager=LinearLayoutManager(this)
        fetchUser()
        if(checkPermissionPhone()){
            getPhoneNumbers()
        }

    }
    companion object{
        val USER_KEY="USER_KEY"
    }
    private fun fetchUser(){
        val ref=FirebaseDatabase.getInstance().getReference("/msgUsersInfo")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                snapshot.children.forEach {
                    val user = it.getValue(MessengerUserStruct::class.java)
                    if (user != null) {

                        if (user.uid != FirebaseAuth.getInstance().uid)
                        {
                            if(checkPermissionPhone())
                            {
                                for (i in namePhoneList!!.distinct())
                                {
                                    if(i.contains(user.phoneno.substring(1)))
                                    {
                                        adapter.add(UserItem(user))
                                    }
                                }
                    }
                        }}
                }

                adapter.setOnItemClickListener { item, view ->

                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatLogActivity::class.java)
                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                    finish()
                }

                recyclerview_newmessage.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    var namePhoneMap: MutableMap<String, String>?=null;
    var namePhoneList: ArrayList<String>?=null;
    private fun getPhoneNumbers() {


        namePhoneList = ArrayList()
        namePhoneMap= linkedMapOf()
        val context:Context=this
        val phones: Cursor? =context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        // Loop Through All The Numbers
        while (phones!!.moveToNext()) {
            val name: String =
                phones!!.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            var phoneNumber: String =
                phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            // Cleanup the phone number
            phoneNumber = phoneNumber.replace("[()\\s-]+".toRegex(), "")
            namePhoneList!!.add(phoneNumber)
            // Enter Into Hash Map
            namePhoneMap!!.put(phoneNumber, name)
        }
        // Get The Contents of Hash Map in Log
        for (i in namePhoneList!!) {
            Log.d("Hello", "Name :$i")
        }
        phones.close()
    }
    public fun checkPermissionPhone():Boolean{
        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)
        {
            return true

        }else{
            //Request PermissionA

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS), "1".toInt())
            return false
        }
    }
}
class UserItem(val user: MessengerUserStruct):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
viewHolder.itemView.textView.text=user.username
        if(user.profileImageUrl!="")
        {
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imageView)

        }else{
            viewHolder.itemView.imageView.setImageResource(R.drawable.avatar)
        }

    }
    override fun getLayout(): Int {
return R.layout.user_row_new_message
    }

}
