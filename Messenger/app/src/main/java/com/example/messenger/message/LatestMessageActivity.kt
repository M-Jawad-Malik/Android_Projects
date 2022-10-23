package com.example.messenger.message

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.messenger.ChatMessage
import com.example.messenger.register.LoginActivity
import com.example.messenger.NewMessageActivity
import com.example.messenger.R
import com.example.messenger.register.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_latest_message.*
import kotlinx.android.synthetic.main.latestmessage_row.view.*

class LatestMessageActivity : AppCompatActivity() {

    companion object{
        var currentUser:MainActivity.User?=null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_message)
        listenToLatestMessages()
        recyclerReview.adapter=adapter
        recyclerReview.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))
        adapter.setOnItemClickListener{item,view->
            val intent=Intent(this,ChatLogActivity::class.java)
            val row=item as latestMessageRow
            intent.putExtra(NewMessageActivity.USER_KEY,row.chatPartnerUser)
            startActivity(intent)
        }

        fetchCurrentUser()
        checkUserIsLogin()

           }
   /* public synchronized fun  show(){

    }*/
    private fun refreshRecyclerViewLatestMsg(){
        adapter.clear()
        latestMessageMap.values.forEach{

            adapter.add(latestMessageRow(it))

        }
    }
    var latestMessageMap=HashMap<String,ChatMessage>()
    private fun listenToLatestMessages(){
        val fromId=FirebaseAuth.getInstance().uid

        val ref=FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")

        ref.addChildEventListener(object: ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

             val chatMessage=snapshot.getValue(ChatMessage::class.java)

                latestMessageMap[snapshot.key!!]=chatMessage!!

                refreshRecyclerViewLatestMsg()
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                var chatMessage=snapshot.getValue(ChatMessage::class.java)?:return
                latestMessageMap[snapshot.key!!]=chatMessage
                refreshRecyclerViewLatestMsg()
            }
            override fun onCancelled(error: DatabaseError) {

            }
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }
            override fun onChildRemoved(snapshot: DataSnapshot) {

            }
        })
    }

    class latestMessageRow(val chatMessage:ChatMessage):Item<GroupieViewHolder>(){
        var chatPartnerUser:MainActivity.User?=null
        override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.latestMessagetextvw2.text=chatMessage.text
           var chatPartnerId:String
              if(chatMessage.fromId==FirebaseAuth.getInstance().uid)
              {
                  chatPartnerId=chatMessage.toId

              }else{

                  chatPartnerId=chatMessage.fromId
              }
               val ref=FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")

               ref.addListenerForSingleValueEvent(object: ValueEventListener{
                   override fun onDataChange(snapshot: DataSnapshot) {
                       chatPartnerUser=snapshot.getValue(MainActivity.User::class.java)
                       viewHolder.itemView.latestMessagetextvw1.text=chatPartnerUser?.username
                    Picasso.get().load(chatPartnerUser?.profileImageUrl).into(viewHolder.itemView.circularimageview)
                   }
                   override fun onCancelled(error: DatabaseError) {
                   }
               })
         }
        override fun getLayout(): Int {
            return R.layout.latestmessage_row
        }
    }

    val adapter=GroupAdapter<GroupieViewHolder>()

    private fun fetchCurrentUser(){
        val uid=FirebaseAuth.getInstance().uid
        val ref=FirebaseDatabase.getInstance().getReference("/users/$uid")
 ref.addListenerForSingleValueEvent(object: ValueEventListener{
     override fun onDataChange(snapshot: DataSnapshot) {
         currentUser=snapshot.getValue(MainActivity.User::class.java)
     }
     override fun onCancelled(error: DatabaseError) {

     }
 }) }

    private fun checkUserIsLogin(){
        val uid=FirebaseAuth.getInstance().uid
        if(uid==null) {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.new_msg ->{
            val intent=Intent(this, NewMessageActivity::class.java)
                startActivity(intent)
            }
            R.id.signout ->{
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
}