package com.example.deservico

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat
import androidx.core.view.isVisible
import com.directions.route.*
import com.example.deservico.databinding.ActivityDriverMapsBinding
import com.example.deservico.message.LatestMessageActivity
import com.firebase.geofire.GeoFire
import com.firebase.geofire.GeoLocation
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
import java.util.*
import kotlin.collections.ArrayList


class DriverMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.location.LocationListener,RoutingListener{

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment;
    lateinit var mGoogleApiClient:GoogleApiClient;
    lateinit var mLocation:Location;
    lateinit var mLocationRequest:LocationRequest;
    lateinit var binding:ActivityDriverMapsBinding;
    var customerId:String=""
    var isLoggingOut:Boolean=false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDriverMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Utils.blackIconStatusBar(this, R.color.mapColor)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestForFineLocation()
        }else{
         mapFragment.getMapAsync(this)
        }
        binding.logoutbtn.setOnClickListener {
            try {
                disconnectDriver()
                mCustomerDatabase!!.removeEventListener(mCustomerDatabaseValueEventListener!!)
                assignClientPickUpLocationRef!!.removeEventListener(assignClientPickUpLocationRefListener)
                FirebaseAuth.getInstance().signOut()
                isLoggingOut = true;
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }catch (e:Exception){
                Log.d("ErrorLog",e.message.toString())
                Toast.makeText(this,e.message.toString(),Toast.LENGTH_LONG).show()
            }
        }

        getAssignCustomer()
        polylines = ArrayList()
        binding.Driverfab.setOnClickListener {
            val intent=Intent(this, LatestMessageActivity::class.java)
            startActivity(intent)
        }
//    if(!isLocationEnabled(this))
//        {
//            AlertDialog.Builder(this)
//                    .setTitle("Location Enable Problem") // GPS not found
//                    .setMessage("Do you want to Enable Location") // Want to enable?
//                    .setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
//                    .setNegativeButton("No", null)
//                    .show()
//        }
    }
    private fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "No working", Toast.LENGTH_LONG).show()
            requestForFineLocation()
            return
        }
        try {
            mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        buildGoogleApiClient();
        mMap.isMyLocationEnabled=true
        }catch (e:Exception){

        }/* // Add a marker in Sydney and move the camera
         val sydney = LatLng(-34.0, 151.0)
         mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))*/
    }
    protected fun buildGoogleApiClient(){
        mGoogleApiClient=GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener { this }
                .addApi(LocationServices.API)
                .build()
        mGoogleApiClient.connect()

    }
    fun getAddress(lat: Double, lng: Double) {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)

            Toast.makeText(this,  "Address: $add", Toast.LENGTH_SHORT).show()
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();
            // TennisAppActivity.showDialog(add);
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
    override fun onConnected(p0: Bundle?) {
        mLocationRequest= LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestForFineLocation()
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this)

    }
    override fun onLocationChanged(location: Location) {
        try {
        if(applicationContext!=null){
            mLocation=location
            var latlng = LatLng(location.latitude, location.longitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11.0F))
            var userid= FirebaseAuth.getInstance().currentUser.uid
            var refAvailable= FirebaseDatabase.getInstance().getReference("SerProAvailable")
            var refWorking=FirebaseDatabase.getInstance().getReference().child("SerProWorking")
            var geoFireAvailable=GeoFire(refAvailable)
            var geoFireWorking=GeoFire(refWorking)
            when(customerId){
                "" -> {
                    geoFireWorking.removeLocation(userid)
                    geoFireAvailable.setLocation(userid, GeoLocation(location.latitude, location.longitude))
                }
                else->{
                    geoFireAvailable.removeLocation(userid)
                    geoFireWorking.setLocation(userid, GeoLocation(location.latitude, location.longitude))
                }
            }
        }}catch (e:Exception){

        }
    }

    var serType:String?=null;
    private fun getAssignCustomer(){
        try {
        var serProId=FirebaseAuth.getInstance().currentUser.uid
        val refSerType= FirebaseDatabase.getInstance().getReference("/SerType/$serProId/serType")
            refSerType.get().addOnSuccessListener {
                serType=it.value.toString()
                Log.d("radius", serType.toString())
                var assignClientRef=FirebaseDatabase.getInstance().getReference().child("Users").child("SerPro").child(serType!!).child(serProId).child("hireRequest")
                assignClientRef.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            customerId = snapshot.value.toString()
                            getAssignCustomerPickUpLocation()
                            getAssignCustomerInfo()
                        }
                        if (snapshot.value.toString().equals("")) {
                            erasePolyLines()
                            customerId = ""
                            if (pickUpMarker != null) {
                                pickUpMarker!!.remove()
                            }
                            if (assignClientPickUpLocationRef != null) {
                                assignClientPickUpLocationRef!!.removeEventListener(assignClientPickUpLocationRefListener)
                                binding.customerInfo.visibility = View.GONE
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                })
            }.addOnFailureListener{

            }
            Log.d("radius", serType.toString())
        }catch (e: Exception){
            Log.d("radius", e.toString())
        }
    }
    var  mCustomerDatabase:DatabaseReference?=null;
    var mCustomerDatabaseValueEventListener:ValueEventListener?=null;
    private fun getAssignCustomerInfo(){
        mCustomerDatabase=FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(customerId)
         mCustomerDatabaseValueEventListener= mCustomerDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && snapshot.childrenCount > 0) {
                    val mCustomerInfo = snapshot.getValue(UserStruc::class.java)
                    binding.customerInfo.visibility = View.VISIBLE
                    binding.customerProfileImage.visibility = View.VISIBLE
                    binding.customerName.text = mCustomerInfo!!.username
                    binding.customerphone.text = mCustomerInfo!!.phoneno
                }
                if (customerId == "") {

                    binding.customerInfo.isVisible = false
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    var pickUpMarker:Marker?=null;
    var assignClientPickUpLocationRef:DatabaseReference?=null;
    lateinit var assignClientPickUpLocationRefListener: ValueEventListener;
    private fun getAssignCustomerPickUpLocation(){

        assignClientPickUpLocationRef=FirebaseDatabase.getInstance().getReference().child("customerrequest").child(customerId).child("l")
        assignClientPickUpLocationRefListener= assignClientPickUpLocationRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists() && !customerId.equals("")) {
                    var map = snapshot.value as List<Object>

                    var locationLat = 0.0
                    var locationlong = 0.0
                    if (map.get(0) != null) {
                        locationLat = map.get(0).toString().toDouble()

                    }
                    if (map.get(1) != null) {
                        locationlong = map.get(1).toString().toDouble()

                    }
                    var pickupLatLng = LatLng(locationLat, locationlong)
                    pickUpMarker = mMap.addMarker(MarkerOptions().position(pickupLatLng).title("Pick Up Location").icon(bitmapDescriptorFromVector(this@DriverMapsActivity, R.mipmap.client)))
                    getAddress(locationLat,locationlong)
                    getRouteToMarker(pickupLatLng)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })  }
    public fun getRouteToMarker(pickupLatLng: LatLng){
        val routing = Routing.Builder()
                .travelMode(AbstractRouting.TravelMode.DRIVING)
                .key(getString(R.string.google_api_key))
                .withListener(this)
                .alternativeRoutes(false)
                .waypoints(LatLng(mLocation.latitude, mLocation.longitude), pickupLatLng)
                .build()
              routing.execute()
    }
    public fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== Fin_Location)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)
            {

                mapFragment.getMapAsync(this)
            }
            else
            {
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun requestForFineLocation(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                Fin_Location)
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

    companion object//Data class contain variable constant
    {
        private val Fin_Location=1
        private val Location_Coarse=2
    }
    private fun disconnectDriver(){
        try{
        var userid= FirebaseAuth.getInstance().currentUser.uid
        var ref= FirebaseDatabase.getInstance().getReference("SerProAvailable")
        var geoFire=GeoFire(ref)
        geoFire.removeLocation(userid)
    }catch (e:Exception){
        Log.d("drivermaperr",e.message.toString())
    }
    }
    override fun onStop() {
        super.onStop()
        if(!isLoggingOut){
       disconnectDriver()
    }}
    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }




    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onRoutingFailure(p0: RouteException?) {
        if(p0 != null) {
            Log.d("Error", p0!!.message.toString())
            Toast.makeText(this, "Error: " + p0!!.message, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onRoutingStart() {
    }
    private var polylines: ArrayList<Polyline>? = null
    //Route Color
    private val COLORS = intArrayOf(R.color.black)
    override fun onRoutingSuccess(p0: ArrayList<Route>?, p1: Int) {
        if (polylines!!.size > 0) {
            for (poly in polylines!!) {
                poly.remove()
            }
        }

        polylines = ArrayList()
        //add route(s) to the map.
        //add route(s) to the map.
        for (i in 0 until p0!!.size) {

            //In case of more than 5 alternative routes
            val colorIndex: Int = i % COLORS.size
            val polyOptions = PolylineOptions()
            polyOptions.color(resources.getColor(COLORS.get(colorIndex)))
            polyOptions.width((10 + i * 3).toFloat())
            polyOptions.addAll(p0.get(i).getPoints())
            val polyline: Polyline = mMap.addPolyline(polyOptions)
            polylines!!.add(polyline)
            Toast.makeText(applicationContext, "Route " + (i + 1) + ": distance - " + p0.get(i).getDistanceValue() + ": duration - " + p0.get(i).getDurationValue(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRoutingCancelled() {
    }
    private fun erasePolyLines(){
        for(poly in polylines!!){
            poly.remove()
        }
        polylines!!.clear()

    }

}

//Route between two points is implemented using google direction android library
