package com.example.deservico
import android.app.Dialog
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.deservico.databinding.ActivityClientsignupBinding
import com.example.deservico.messenger.MessengerUserStruct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

//,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener
class ClientSignUp : AppCompatActivity(){
    private var auth: FirebaseAuth?=null;
    private var uid:String?=null;
   // private lateinit var gpsbinding:GoogleplayservCheckdialogBinding
    private lateinit var binding:ActivityClientsignupBinding
    private var str:String="";
   // private lateinit var nonce:String;
   // private lateinit var mGoogleApiClient:GoogleApiClient;
    /*private val connectionCallback = object : MediaBrowser.ConnectionCallback() {
        override fun onConnected() {
            println("ConnectionCallback.onConnected")
            super.onConnected()
        }
        override fun onConnectionFailed() {
            println("ConnectionCallback.onConnectionFailed")
            super.onConnectionFailed()
        }

    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.blackIconStatusBar(this, R.color.Light_background);
        binding= ActivityClientsignupBinding.inflate(layoutInflater)
       // nonce = UUID.randomUUID().toString()
     //   gpsbinding= GoogleplayservCheckdialogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //checkGooglePlayServVersion()
        binding.mSerProbtnC.setOnClickListener {
            var intent=Intent(this, ServProSignUp::class.java)
            startActivity(intent)
        }
      /* buildGoogleApiClient()
        createCompatReq()*/
        binding.ClientSignupbtn.setOnClickListener {
            ExecuteAsyncTask().execute()
        }
        binding.clientSignIntxt.setOnClickListener {
            var intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun regClient(){
        try{
        var result:String="";
        val email=binding.clientEmail.text.toString().trim(){it<=' '}
        val password=binding.clientPassword.text.toString().trim(){it<=' '}
        val phoneno=binding.clientphone.text.toString().trim(){it<=' '}
        val name=binding.clientName.text.toString()

        if(email.isEmpty()||password.isEmpty()||phoneno.isEmpty()||name.isEmpty())
        {
            object : Thread() {
                override fun run() {
                    this@ClientSignUp.runOnUiThread(Runnable {
                        //Do your UI operations like dialog opening or Toast here
            Toast.makeText(this@ClientSignUp, "Please Fill all the Box", Toast.LENGTH_LONG).show();
                    })
                }
            }.start()
        }else {
            auth = FirebaseAuth.getInstance()
            //FirebaseAuth to create  a user with email and password
            auth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@ClientSignUp) { task ->
                        if (task.isSuccessful) {
                            uid = auth!!.currentUser.uid
                            saveUserToFirebaseDatabase()
                            // Sign in success, update UI with the signed-in user's information
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(this@ClientSignUp, task.exception.toString(), Toast.LENGTH_LONG).show();

                        }

                    }
        }}catch (e: Exception){
            Log.d("clientsignuperr", e.message.toString())
        }
    }
    private fun saveUserToFirebaseDatabase(){
      try {
          val refMessenger=FirebaseDatabase.getInstance().getReference("/msgUsersInfo/$uid")
          val userMessenger= MessengerUserStruct(uid!!,
                  binding.clientEmail.text.toString(),
                  binding.clientName.text.toString(), "", binding.clientphone.text.toString())
          refMessenger.setValue(userMessenger)
              .addOnSuccessListener {

              }
              .addOnFailureListener{
              }
          val ref = FirebaseDatabase.getInstance().getReference("/Users").child("/Customer/${uid!!}")
          val user = UserClient(
                  uid!!,
                  binding.clientEmail.text.toString(),
                  binding.clientName.text.toString(),
                  binding.clientphone.text.toString(),
          )
          ref.setValue(user)
              .addOnSuccessListener {
                  val intent = Intent(this, CustomerMapActivity::class.java)
                  intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                  startActivity(intent)
              }
              .addOnFailureListener {
                  Toast.makeText(this, "Failed to Save User", Toast.LENGTH_LONG).show()
              }
      }catch (e: Exception){

      }

    }
    class UserClient(val uid: String, val email: String, val username: String, val phoneno: String) {
        constructor():this("", "", "", "")
    }
    private inner class ExecuteAsyncTask() :
            AsyncTask<Any, Void, String>() {

        var customProgressDialog: Dialog? = null

        /**
         * This function is for the task which we wants to perform before background execution.
         * Here we have shown the progress dialog to user that UI is not freeze but executing something in background.
         */
        override fun onPreExecute() {
            super.onPreExecute()

            showProgressDialog()
        }




        /**
         * This function will be used to perform background execution.
         */


        override fun doInBackground(vararg params: Any): String {

            //TODO(You can code here what you wants to execute in background execution without freezing the UI thread.)

            // This is just an for loop which is executed for 1000 times.
           /* for (i in 1..1000) {
                Log.e("jawad ", "" + i)
            }*/
           regClient()
            // You can notify with you custom message to onPostexecute.
            return  ""
        }

        /**
         * This function will be executed after the background execution is completed.
         */
        override fun onPostExecute(result: String) {
            super.onPostExecute(result)

            cancelProgressDialog()


           // Toast.makeText(this@ClientSignUp,str,Toast.LENGTH_LONG).show()
        }

        /**
         * Method is used to show the Custom Progress Dialog.
         */
        private fun showProgressDialog() {
            customProgressDialog = Dialog((this@ClientSignUp))

            /*Set the screen content from a layout resource.
            The resource will be inflated, adding all top-level views to the screen.*/
            customProgressDialog!!.setContentView(R.layout.dialog_custom_progress)

            //Start the dialog and display it on screen.
            customProgressDialog!!.show()
        }

        /**
         * This function is used to dismiss the progress dialog if it is visible to user.
         */
        private fun cancelProgressDialog() {
            if (customProgressDialog != null) {
                customProgressDialog!!.dismiss()
                customProgressDialog = null
            }
        }
    }
























   /* private fun generateNonce(): ByteArray? {
        val nonce = ByteArray(16)
         kotlin.random.Random.Default.nextBytes(nonce)
        return nonce
    }
private fun createCompatReq(){
    SafetyNet.getClient(this).attest(generateNonce()!!, BuildConfig.ApiKey)
        .addOnSuccessListener(this) {
            // Indicates communication with the service was successful.
            // Use response.getJwsResult() to get three result data.
            try {
                Log.d("response","${decodeJws( SafetyNetApi.AttestationResponse(

                ).jwsResult.toString())}")
            }catch (e:Exception){
                Log.d("Exce","Aiii")
        }
        }
        .addOnFailureListener(this) { e ->
            // An error occurred while communicating with the service.
            if (e is ApiException) {
                // An error with the Google Play services API contains some
                // additional details.
                val apiException = e as ApiException

                // You can retrieve the status code using the
                // apiException.statusCode property.
            } else {
                // A different, unknown type of error occurred.
                Log.d("error", "Error: " + e.message)
            }
        }
}

    fun decodeJws(jwsResult: String?): String? {
        if (jwsResult == null) {
            return null
        }
        val jwtParts = jwsResult.split("\\.".toRegex()).toTypedArray()
        return if (jwtParts.size == 3) {
            String(Base64.getDecoder().decode(jwtParts[1]))
        } else {
            null
        }
    }
    private fun checkGooglePlayServVersion(){
        if (GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this, 13000000) ==
            ConnectionResult.SUCCESS) {

        } else {
            googlePlayServiceCheckDialog()
        }
    }
    fun googlePlayServiceCheckDialog(){
        val CustomDialog=Dialog(this)
        CustomDialog.setContentView(R.layout.googleplayserv_checkdialog)
        val okbtn=CustomDialog.findViewById<TextView>(R.id.tv_okbtn)
        okbtn.setOnClickListener {
            val url="https://play.google.com/store/apps/details?id=com.google.android.gms&hl=en&gl=US"
            val uri=Uri.parse(url)
            val intent=Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
            finish()
            CustomDialog.dismiss()
        }
        CustomDialog.show()
    }
    @Synchronized
    protected fun buildGoogleApiClient() {
         mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(SafetyNet.API)
            .addConnectionCallbacks(this) //ConnectionCallback interface
            .addOnConnectionFailedListener(this) //OnConnectionFailedListener interface
            .build()
    }

    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }*/







}