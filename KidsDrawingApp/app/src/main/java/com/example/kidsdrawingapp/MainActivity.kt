
package com.example.kidsdrawingapp

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_brush_size.*
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.provider.MediaStore
import android.util.Log
import android.widget.Gallery
import android.widget.Toast
import android.os.AsyncTask
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private var mImageBottonCurrentPaint:ImageButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawing_view.setSizeForBrush(20.toFloat())
        mImageBottonCurrentPaint = ll_paint_colors[1] as ImageButton
        mImageBottonCurrentPaint!!.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
        )
        ib_brush.setOnClickListener {
            showBrushSizeChooseDialog()
        }
        ib_gallery.setOnClickListener{
            if(isReadStorageAllowed())
            {

                val pickPhotoIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhotoIntent, GALLARY)
            }else{
                requestStoragePermission()
            }
        }
            ib_undo.setOnClickListener{
                drawing_view.onClickUndo()
            }
        ib_save.setOnClickListener {
            if (isReadStorageAllowed()) {
                BitmapAsyncTask(getBitmapFromView(fl_drawing_view_container)).execute()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode==Activity.RESULT_OK)
        {

            if(requestCode== GALLARY)
            {
                try{
                    if(data!!.data!=null)
                    {
                        iv_background.visibility=View.VISIBLE;
                        iv_background.setImageURI(data.data)
                    }
                    else{
                        Toast.makeText(this@MainActivity,
                                "Error in parsing Image!!",
                                Toast.LENGTH_LONG).show()
                    }
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }

        }
    }
    private fun showBrushSizeChooseDialog(){
        val brushDialog=Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.getWindow()!!.setLayout(150,250)
        brushDialog.setTitle("Brush Size: ")
        val smallbtn=brushDialog.ib_small_brush
        smallbtn.setOnClickListener {
            drawing_view.setSizeForBrush(10.toFloat())
            brushDialog.dismiss()
        }
        val mediumbtn=brushDialog.ib_medium_brush
        mediumbtn.setOnClickListener {
            drawing_view.setSizeForBrush(20.toFloat())
            brushDialog.dismiss()
        }
        val largebtn=brushDialog.ib_large_brush
        largebtn.setOnClickListener {
            drawing_view.setSizeForBrush(30.toFloat())
            brushDialog.dismiss()
        }
        brushDialog.show()

    }

    fun paint_click(view: View)
    {
        if(view!=mImageBottonCurrentPaint){
            val imageButton=view as ImageButton
            val colorTag=imageButton.tag.toString()
            drawing_view.setColor(colorTag)
            imageButton.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.pallet_pressed)
            )
            mImageBottonCurrentPaint!!.setImageDrawable(
                    ContextCompat.getDrawable(this,R.drawable.pallet_normal)
            )
            mImageBottonCurrentPaint=view
        }
    }
    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).toString()))
                {
            Toast.makeText(this,"Permission Required for Storage",Toast.LENGTH_LONG).show()

        }
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE) , STORAGE_PERMISSION_CODE
        )

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== STORAGE_PERMISSION_CODE)
        {
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this@MainActivity,
                        "Permission Granted now you can read the Storage files",
                        Toast.LENGTH_LONG).show()
            }else{

                Toast.makeText(this@MainActivity,
                        "Oops! you just denied the permission",
                        Toast.LENGTH_LONG).show()

            }
        }

    }
    private fun isReadStorageAllowed():Boolean{
        val result=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        return result==PackageManager.PERMISSION_GRANTED
    }
    private fun getBitmapFromView(view:View):Bitmap{
        val returnedBitmap=Bitmap.createBitmap(view.width,
                view.height,Bitmap.Config.ARGB_8888)
        val canvas=Canvas(returnedBitmap)
        val bgDrawable=view.background
        if(bgDrawable!=null){
            bgDrawable.draw(canvas)
        }else
        {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return returnedBitmap
    }
    private inner class BitmapAsyncTask(val mBitmap: Bitmap):AsyncTask<Any,Void,String>() {
        private lateinit var mProgressDialog:Dialog
        override fun onPreExecute() {
            super.onPreExecute()
            showProgressDialog()
        }
        override fun doInBackground(vararg params: Any?): String {
            var result=""
            if(mBitmap!=null){
                try{
                    val bytes=ByteArrayOutputStream()
                    mBitmap.compress(Bitmap.CompressFormat.PNG,90,bytes)
                    val f= File(externalCacheDir!!.absoluteFile.toString()
                            +File.separator+"myDrawing_"
                            +System.currentTimeMillis()/1000+".png")
                    val fos=FileOutputStream(f)
                    fos.write(bytes.toByteArray())
                    fos.close()
                        result=f.absolutePath
                }catch (e:java.lang.Exception){
                    result=""
                    e.printStackTrace()
                }

            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            cancelProgressDialog()
            if(!result!!.isEmpty())
            {
                Toast.makeText(
                        this@MainActivity,
                        "File Save Successfully :$result",
                        Toast.LENGTH_SHORT
                ).show()
            }else{
                Toast.makeText(
                        this@MainActivity,
                        "Something went wrong while saving the file. ",
                        Toast.LENGTH_SHORT
                ).show()
            }

        }

private fun showProgressDialog(){
    mProgressDialog= Dialog(this@MainActivity)
    mProgressDialog.setContentView(R.layout.dialog_custom_progress)
    mProgressDialog.show()
}
        private fun cancelProgressDialog(){
            mProgressDialog.dismiss()
        }
    }
    companion object {
       private  const val STORAGE_PERMISSION_CODE=1
        private const val GALLARY=2
    }

}