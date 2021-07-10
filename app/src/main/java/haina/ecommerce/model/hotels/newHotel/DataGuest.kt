package haina.ecommerce.model.hotels.newHotel

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dataguest")
@Parcelize
data class DataGuest (

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "title")
    var title:String,

    @ColumnInfo(name = "first_name")
    var first_name:String,

    @ColumnInfo(name = "last_name")
    var last_name:String,
):Parcelable