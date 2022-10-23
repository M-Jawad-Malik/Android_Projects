package com.customdialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.dialog_custom.*

class MainActivity : AppCompatActivity() {

    /**
     * This method is autocreated by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        /*This is implemented by android studio it self when we select the Basic Activity while creating the project.*/
        setSupportActionBar(toolbar)

        /**
         * Here we have handled onclick of ImageButton. And we have used SnackBar to notify.
         */
        image_button.setOnClickListener { view ->
            Snackbar.make(view, "You have clicked image button.", Snackbar.LENGTH_LONG).show()

        }

        /**
         * Here we have handled onClick of Alert Dialog Button.
         */
        btn_alert_dialog.setOnClickListener { view ->

            //Launch Alert Dialog
            alertDialogFunction1()
        }

        /**
         * Here we have handled onClick of Custom Dialog Button.
         */
        btn_custom_dialog.setOnClickListener { view ->

            //Launch Custom Dialog
            customDialogFunction1()
        }

        /**
         * Here we have handled onClick of Custom Progress Dialog Button.
         */
        btn_custom_progress_dialog.setOnClickListener { view ->

            //Launch Custom Progress Dialog
            customProgressDialogFunction()
        }
    }

    /**
     * Method is used to show the Alert Dialog.
     */
    private fun alertDialogFunction() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Alert")
        //set message for alert dialog
        builder.setMessage("This is Alert Dialog. Which is used to show alerts in our app.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing cancel action
        builder.setNeutralButton("Cancel") { dialogInterface, which ->
            Toast.makeText(
                applicationContext,
                "clicked cancel\n operation cancel",
                Toast.LENGTH_LONG
            ).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which ->
            Toast.makeText(applicationContext, "clicked No", Toast.LENGTH_LONG).show()
            dialogInterface.dismiss() // Dialog will be dismissed
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false) // Will not allow user to cancel after clicking on remaining screen area.
        alertDialog.show()  // show the dialog to UI
    }


    /**
     * Method is used to show the Custom Dialog.
     */
    private fun customDialogFunction() {
        val customDialog = Dialog(this)
        /*Set the screen content from a layout resource.
    The resource will be inflated, adding all top-level views to the screen.*/
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.tv_submit.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "clicked submit", Toast.LENGTH_LONG).show()
            customDialog.dismiss() // Dialog will be dismissed
        })
        customDialog.tv_cancel.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "clicked cancel", Toast.LENGTH_LONG).show()
            customDialog.dismiss()
        })
        //Start the dialog and display it on screen.
        customDialog.show()
    }






    private fun customDialogFunction1()
    {
        val customDialog=Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom)
        customDialog.tv_submit.setOnClickListener {
            Toast.makeText(this,"Submit Clicked",Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }
        customDialog.tv_cancel.setOnClickListener {
            Toast.makeText(this,"Cancel Clicked",Toast.LENGTH_SHORT).show()
            customDialog.dismiss()
        }
        customDialog.show()
    }

    /**
     * Method is used to show the Custom Progress Dialog.
     */
    private fun customProgressDialogFunction() {
        val customProgressDialog = Dialog(this)

        /*Set the screen content from a layout resource.
        The resource will be inflated, adding all top-level views to the screen.*/
        customProgressDialog.setContentView(R.layout.dialog_custom_progress)

        //Start the dialog and display it on screen.
        customProgressDialog.show()
    }


    private fun alertDialogFunction1()
    {
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Alert")
        builder.setMessage("HEllo Jawad")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes"){
            dialogInterface, i ->Toast.makeText(applicationContext,"Clicked Yes",Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        builder.setNeutralButton("Cancel"){
            dialogInterface, i ->Toast.makeText(applicationContext,"Clicked Canceled\nOperation Canceled",Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No"){
            dialogInterface, i ->Toast.makeText(applicationContext,"Clicked No",Toast.LENGTH_LONG).show()
            dialogInterface.dismiss()
        }
        val alertDialog:AlertDialog=builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}
