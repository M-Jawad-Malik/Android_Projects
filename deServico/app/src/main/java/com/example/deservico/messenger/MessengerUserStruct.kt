package com.example.deservico.messenger

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class MessengerUserStruct (val uid:String,val email:String,val username:String,val profileImageUrl:String,val phoneno:String):Parcelable{
        constructor():this("","","","","")

}