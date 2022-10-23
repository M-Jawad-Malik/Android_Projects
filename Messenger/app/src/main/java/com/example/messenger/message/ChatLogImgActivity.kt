package com.example.messenger.message

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messenger.ChatMessage
import com.example.messenger.NewMessageActivity
import com.example.messenger.R
import com.example.messenger.Utils
import com.example.messenger.register.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_chat_log_img.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream


class ChatLogImgActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log_img)
         val myUri: Uri = Uri.parse(intent.getStringExtra("IMG"));
        chat_log_img_imgvw.setImageURI(myUri)
        Utils.blackIconStatusBar(this, R.color.white)
        sendbtn_chat_log_img.setOnClickListener{
            performSendImage(myUri)
            val intent= Intent(this, ChatLogActivity::class.java) //Transfer of data, significant for launching of activities
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

    }
    fun performSendImage(uri: Uri){
        var imageStream: InputStream? = getContentResolver().openInputStream(uri);
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        //val encodedImage = encodeImage(selectedImage)
        var base64 = encodeImage(selectedImage)
       base64 = "data:image/" + getMimeType(this, uri).toString() + ";base64," + base64
       performSendMessage(base64)
    }
    fun performSendMessage(string:String){
        val fromId= FirebaseAuth.getInstance().uid
        val user=  intent.getParcelableExtra<MainActivity.User>("user")
        val toId=user?.uid
        if(fromId==null)return
        val text=string;
        val ref= FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toref= FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatmessage= ChatMessage(ref.key!!,text,fromId,toId!!,System.currentTimeMillis()/1000)
        ref.setValue(chatmessage)
        toref.setValue(chatmessage)
    }


    fun getMimeType(context: Context, uri: Uri): String? {
        val extension: String?
        extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.getContentResolver().getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    private fun encodeImage(bm: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }
}