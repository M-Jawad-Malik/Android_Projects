package com.example.deservico

    import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.view.isVisible
import com.example.deservico.databinding.ActivityCustomerMapBinding
import com.example.deservico.message.ChatLogActivity
import com.example.deservico.message.LatestMessageActivity
import com.example.deservico.message.NewMessageActivity
import com.example.deservico.messenger.MessengerUserStruct
import com.firebase.geofire.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.latestmessage_row.view.*
import java.util.*
class CustomerMapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener {

    //Map Variable to set marker, zoom in and other properties
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment;
    lateinit var mGoogleApiClient: GoogleApiClient;
    lateinit var mLocation: Location;
    lateinit var mLocationRequest: LocationRequest;
    //User Location Currentlu
    lateinit var pickUpLocation:LatLng;
    //binding View
    lateinit var destinationLatLng:LatLng;
    lateinit var binding:ActivityCustomerMapBinding;
    lateinit var pickUpMarker:Marker;
    var requestBol:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCustomerMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
        //Status bar Color
       Utils.blackIconStatusBar(this, R.color.white)

        //Check if user already login
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestForFineLocation()
        }else{
            mapFragment.getMapAsync(this)
        }
        destinationLatLng= LatLng(0.0,0.0)
        //Logout Button on the Map
        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            var intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Drop down
        ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.Cusspinner.adapter = adapter

        }
        //
        //Customer Requesting for Service Provider
        binding.request.setOnClickListener {
        if(requestBol){
            requestBol=false;
            geoQuery.removeAllListeners()
            if(SerProFoundId!=null){
                if (serProFound){
                var SerProRef=FirebaseDatabase.getInstance().getReference().child("Users").child("/SerPro").child("/${binding.Cusspinner.selectedItem.toString()}/").child(SerProFoundId!!).child("/hireRequest")
                SerProRef.setValue("")
                    SerProLocationRef.removeEventListener(SerProLocationRefListner)
                    binding.driverInfo.visibility=View.GONE
                    binding.Cusspinner.isEnabled=false;
                    Log.d("ioi","Hy")
                }
                SerProFoundId=null
            }
            serProFound=false
            radius=1.0

            var userid= FirebaseAuth.getInstance().currentUser.uid
            var ref= FirebaseDatabase.getInstance().getReference("customerrequest")
            var geoFire=GeoFire(ref)
            geoFire.removeLocation(userid)
            if(pickUpMarker!=null){
                pickUpMarker.remove()
            }
            if(mSerProMarker !=null){
                mSerProMarker!!.remove()

            }
            binding.request.setText("Request DeServico")
            binding.driverInfo.isVisible=false
            binding.Cusspinner.isEnabled=true;
        }
            else{
            binding.Cusspinner.isEnabled=false;
                requestBol=true;
            var userId=FirebaseAuth.getInstance().currentUser.uid
            var ref=FirebaseDatabase.getInstance().getReference("customerrequest")
            var geoFire=GeoFire(ref)
            geoFire.setLocation(userId, GeoLocation(mLocation.latitude, mLocation.longitude))
            pickUpLocation= LatLng(mLocation.latitude, mLocation.longitude)
            pickUpMarker= mMap.addMarker(MarkerOptions().position(pickUpLocation).title("My Location").icon(bitmapDescriptorFromVector(this@CustomerMapActivity,R.mipmap.client)))
            binding.request.setText("Getting your Service Provider.....")
            getClossetSerPro()
        }

        }
    binding.Customerfab.setOnClickListener {
        var intent=Intent(this,LatestMessageActivity::class.java)
        startActivity(intent)
        return@setOnClickListener
    }
        binding.icMsgCusSer.setOnClickListener {
            try {
            val ref=FirebaseDatabase.getInstance().getReference("/msgUsersInfo/$SerProFoundId")
                Log.d("ChatLogErr",SerProFoundId.toString())
            var chatPartnerUser:MessengerUserStruct?=null
            ref.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    chatPartnerUser=snapshot.getValue(MessengerUserStruct::class.java)
                    val intent=Intent(this@CustomerMapActivity, ChatLogActivity::class.java)
                    intent.putExtra(NewMessageActivity.USER_KEY,chatPartnerUser!!)
                    startActivity(intent)
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        }catch (e:Exception){
                Log.d("ChatLogErr",e.message.toString())
            }
    }
        if(!isLocationEnabled(this))
        {
            AlertDialog.Builder(this)
                    .setTitle("Location Enable Problem") // GPS not found
                    .setMessage("Do you want to Enable Location") // Want to enable?
                    .setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
                    .setNegativeButton("No", null)
                    .show()
        }
    }
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==Fin_Location)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {
                mapFragment.getMapAsync(this)
            }
            else
            {
                Toast.makeText(this,"Permission Required", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private var radius=1.0;
    private var serProFound=false;
    private var SerProFoundId:String?="";
   lateinit var geoQuery:GeoQuery;
    var refSerType:DatabaseReference?=null;
    var refSerTypeSuccessListener:EventListener?=null;
    private fun getClossetSerPro(){
        var ref= FirebaseDatabase.getInstance().getReference().child("SerProAvailable")
        var geofire=GeoFire(ref)
        //To create radius around cumtomer request
        geoQuery=geofire.queryAtLocation(
                GeoLocation(
                        pickUpLocation.latitude,
                        pickUpLocation.longitude
                ), radius
        )
        geoQuery.removeAllListeners()
        geoQuery.addGeoQueryEventListener(object : GeoQueryEventListener {
            override fun onKeyEntered(key: String, location: GeoLocation) {
                //here key is the id of Service Provider
                //location is the location of Service Provider
                if (!serProFound && requestBol) {
                    SerProFoundId = key
                    val refSerType= FirebaseDatabase.getInstance().getReference("/SerType/$SerProFoundId/serType")
                refSerType.get().addOnSuccessListener {
                       if(binding.Cusspinner.selectedItem.toString()== it.value.toString()) {
                           var SerProRef = FirebaseDatabase.getInstance().getReference().child("Users").child("/SerPro").child("/${binding.Cusspinner.selectedItem}/").child(SerProFoundId!!).child("/hireRequest")
                           var customerId = FirebaseAuth.getInstance().currentUser.uid
                           SerProRef.setValue(customerId!!)
                           serProFound = true;
                           binding.Cusspinner.isEnabled=false;
                           binding.request.setText("Looking for SerProvider Location")
                           getSerProLocation()
                       } }.addOnFailureListener{

            }

                }
            }


            override fun onKeyExited(key: String) {

            }

            override fun onKeyMoved(key: String, location: GeoLocation) {
            }

            //Increase Radius
            override fun onGeoQueryReady() {
                if (!serProFound && radius<6000) {
                    radius += 10;
                    getClossetSerPro()
                }else if(!serProFound && radius>6000){
                    binding.request.setText("Cancel Hiring")
                    Toast.makeText(this@CustomerMapActivity,"No Service Provider Found",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onGeoQueryError(error: DatabaseError) {

            }
        })
    }
     var  mCustomerDatabase:DatabaseReference?=null;
    private fun getAssignDriverInfo(){

        mCustomerDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child("/SerPro").child("/${binding.Cusspinner.selectedItem.toString()}/").child(SerProFoundId!!)
     mCustomerDatabase!!.get().addOnSuccessListener  {
                if (it.exists() &&it.childrenCount>0) {
                    val mCustomerInfo=it.getValue(SerProStruct::class.java)
                    binding.Cusspinner.isEnabled=false;
                    binding.driverInfo.visibility= View.VISIBLE
                    binding.DriverName.text=mCustomerInfo!!.username
                    binding.Driverphone.text=mCustomerInfo!!.phoneno
                    binding.Sertype.text=mCustomerInfo!!.serType
                    Picasso.get().load(mCustomerInfo!!.profileImageUrl).into(binding.DriverProfileImage)
                }else{
                    binding.driverInfo.isVisible=false
                    binding.Cusspinner.isEnabled=true;
                }

            }.addOnFailureListener{

     }

    }
    var mSerProMarker:Marker?=null;
    lateinit var myList: MutableList<Double>;
    lateinit var SerProLocationRef:DatabaseReference;
    lateinit var SerProLocationRefListner:ValueEventListener;
    private fun getSerProLocation(){
        //2. here we show the Service provider marker on the customer map through a red marker
        SerProLocationRef=FirebaseDatabase.getInstance().getReference().child("SerProWorking").child(SerProFoundId!!).child("l")
        SerProLocationRefListner= SerProLocationRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && requestBol) {
                    try {
                        myList = snapshot!!.value!! as MutableList<Double>
                        var locationLat = 0.0
                        var locationlong = 0.0
                        binding.request.setText("Driver Found")
                        if (myList.get(0) != null) {
                            locationLat = myList.get(0)

                        }
                        if (myList.get(1) != null) {
                            locationlong = myList.get(1).toString().toDouble()

                        }
                        var SerProLatLong = LatLng(locationLat, locationlong)
                        if (mSerProMarker != null) {
                            mSerProMarker!!.remove()
                        }
                        var loc1 = Location("");
                        loc1.latitude = pickUpLocation.latitude
                        loc1.longitude = pickUpLocation.longitude
                        var loc2 = Location("");
                        loc2.latitude = SerProLatLong.latitude
                        loc2.longitude = SerProLatLong.longitude
                        var distance: Float = loc1.distanceTo(loc2)
                        if (distance < 100) {
                            binding.request.setText("Service Provider's here!")
                        } else {
                            binding.request.setText("SerProvider Found at " + (distance / 1000).toString() + " Km")
                        }
                        mSerProMarker = mMap.addMarker(MarkerOptions().position(SerProLatLong).title("Your Service Provider").icon( bitmapDescriptorFromVector(this@CustomerMapActivity,R.mipmap.serpro1)))
                        getAssignDriverInfo()
                    } catch (e: Exception) {
                        Log.d("radius", e.toString())
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    public fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestForFineLocation()
            return
        }
        try {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.mapType=GoogleMap.MAP_TYPE_NORMAL
        buildGoogleApiClient();
        mMap.isMyLocationEnabled=true
        /* // Add a marker in Sydney and move the camera
         val sydney = LatLng(-34.0, 151.0)
         mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
        }catch (e:Exception){
            Log.d("Maptype",e.message.toString())
        }}
    private fun requestForFineLocation(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION.toString()
                )){
            Toast.makeText(this, "Location Permission Required", Toast.LENGTH_LONG).show()
        }
        ActivityCompat.requestPermissions(
                this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ),
                Fin_Location
        )
    }
    companion object//Data class contain variable constant
    {
        private val Fin_Location=1
        private val Location_Coarse=2
    }
    protected fun buildGoogleApiClient(){
        mGoogleApiClient= GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener { this }
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()
    }

    override fun onLocationChanged(location: Location) {
        mLocation=location
        var latlng = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0F))
    }


    override fun onConnected(p0: Bundle?) {
        mLocationRequest= LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            requestForFineLocation()
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient,
                mLocationRequest,
                this
        )
    }


    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

}