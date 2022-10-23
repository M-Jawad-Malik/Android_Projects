package com.example.messenger.message

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.messenger.ChatMessage
import com.example.messenger.NewMessageActivity
import com.example.messenger.R
import com.example.messenger.register.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*
import kotlinx.android.synthetic.main.user_row_new_message.view.*
import java.io.ByteArrayOutputStream


class ChatLogActivity : AppCompatActivity() {
    val adapter=GroupAdapter<GroupieViewHolder>()
    var touser:MainActivity.User?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        touser=  intent.getParcelableExtra<MainActivity.User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title=touser?.username
        ListenForMessage()
        sendbtn_chat_log.setOnClickListener {
            performSendMessage(editTextTextPersonName.text.toString())
        }
        recyclerview_chat_log.adapter=adapter
        pic.setOnClickListener {
            if(isRequestPermissionAllowed()){
                val intent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, GALLERY)
            }else{
                requestForReadStoragePermission()
            }
        }
    }
    companion object//Data class contain variable constant
    {
        private val READ_STORAGE_PERMISSION=1
        private val GALLERY=2
        private val IMG_URI="IMG"
        private  val FIRST_ACTIVITY_REQUEST_CODE=3
    }
    private fun isRequestPermissionAllowed():Boolean{
        val result= ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        return result== PackageManager.PERMISSION_GRANTED
    }
    private fun requestForReadStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE.toString()
            )){
            Toast.makeText(this, "Request Required to Upload Profile Photo", Toast.LENGTH_LONG).show()
        }
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            READ_STORAGE_PERMISSION
        )
    }
    private fun ListenForMessage(){
        val fromid=FirebaseAuth.getInstance().uid
        val toid=touser?.uid
        val ref= FirebaseDatabase.getInstance().getReference("/user-messages/$fromid/$toid")
        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if (chatMessage != null) {
                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid) {
                        val currentUser = LatestMessageActivity.currentUser
                        adapter.add(ChatToItem(chatMessage.text, currentUser!!))

                    } else {
                        adapter.add(ChatFromItem(chatMessage.text, touser!!))

                    }

                    recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== READ_STORAGE_PERMISSION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission has been Granted", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    var selectedPhotoUri: Uri?=null
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==GALLERY)
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
                        val user=  intent.getParcelableExtra<MainActivity.User>(NewMessageActivity.USER_KEY)
                        val intent=Intent(this, ChatLogImgActivity::class.java)
                        intent.putExtra("user", user)
                        intent.putExtra(IMG_URI, data.data.toString())
                        startActivityForResult(intent, FIRST_ACTIVITY_REQUEST_CODE)
                    }
                }catch (e: Exception)
                {
                    e.printStackTrace()
                }
            }
        }
    }
    fun performSendMessage(string: String){
        val fromId=FirebaseAuth.getInstance().uid
        val user=  intent.getParcelableExtra<MainActivity.User>(NewMessageActivity.USER_KEY)
        val toId=user?.uid
        if(fromId==null)return
        val text=string;
        val ref=FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toref=FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatmessage=ChatMessage(
            ref.key!!,
            text,
            fromId,
            toId!!,
            System.currentTimeMillis() / 1000
        )
        ref.setValue(chatmessage)
                .addOnSuccessListener {
                  editTextTextPersonName.text.clear()
                    recyclerview_chat_log.scrollToPosition(adapter.itemCount - 1)
                }
        toref.setValue(chatmessage)

   val latestMessageFromRef=FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageFromRef.setValue(chatmessage)
    val latestMessageToRef=FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageToRef.setValue(chatmessage)





    }

}
class ChatFromItem(val text: String, val user: MainActivity.User):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if(text.indexOf("image/")!=-1){
            viewHolder.itemView.chatfromtext.visibility = View.GONE;
            viewHolder.itemView.chatfromimg.visibility=View.VISIBLE;
            //   imageBytes = Base64.decode(text.substring(text.indexOf(";base64,") + 7), Base64.DEFAULT)
            viewHolder.itemView.chatfromimg.setImageBitmap(convertBase64ToBitmap((text.substring(text.indexOf(";base64,") + 8))))
        }else {
            viewHolder.itemView.chatfromtext.visibility =View.VISIBLE;
            viewHolder.itemView.chatfromimg.visibility=View.GONE;
            viewHolder.itemView.chatfromtext.text = text
        }
                viewHolder.itemView.chatfromtext.text=text
   Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imagechatfromrow)
    }

    override fun getLayout(): Int {
       return R.layout.chat_from_row
    }

}
class ChatToItem(val text: String, val user: MainActivity.User):Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if(text.indexOf("image/")!=-1){
            viewHolder.itemView.chattotext.visibility = View.GONE;
            viewHolder.itemView.chattoimg.visibility=View.VISIBLE;
            //   imageBytes = Base64.decode(text.substring(text.indexOf(";base64,") + 7), Base64.DEFAULT)
            viewHolder.itemView.chattoimg.setImageBitmap(convertBase64ToBitmap((text.substring(text.indexOf(";base64,") + 8))))
        }else {
            viewHolder.itemView.chattotext.visibility =View.VISIBLE;
            viewHolder.itemView.chattoimg.visibility=View.GONE;
            viewHolder.itemView.chattotext.text = text
        }
        Picasso.get().load(user.profileImageUrl).into(viewHolder.itemView.imagechattorow)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }

}
fun convertBase64ToBitmap(b64: String): Bitmap? {
    val imageAsBytes = Base64.decode(b64.toByteArray(), Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
}