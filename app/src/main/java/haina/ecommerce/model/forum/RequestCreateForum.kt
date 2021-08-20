package haina.ecommerce.model.forum

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestCreateForum(

    @field:SerializedName("subforum_name")
    var subforumName:String,

    @field:SerializedName("subforum_description")
    var subforumDescription:String,

    @field:SerializedName("image")
    var image:String



): Parcelable

