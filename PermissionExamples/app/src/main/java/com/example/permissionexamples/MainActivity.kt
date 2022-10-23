package com.example.permissionexamples
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnCameraPermission.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"You already have Access",Toast.LENGTH_LONG).show()

            }else{
                //Request PermissionA
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA,android.Manifest.permission.ACCESS_FINE_LOCATION), CAMERA_AND_FINE_LOCATION)

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== CAMERA_AND_FINE_LOCATION)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Permission Granted for Camera",Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(this,"Permission Denied for Camera",Toast.LENGTH_SHORT).show()
            }

        }
    }

        companion object//Data class contain variable constant
        {
            private const val CAMERA_PERMISSION_CODE=1 //to check which permission i am looking for
            private const val FINE_LOCATION_PERMISSION_CODE=2
            private const  val CAMERA_AND_FINE_LOCATION=12
        }
}