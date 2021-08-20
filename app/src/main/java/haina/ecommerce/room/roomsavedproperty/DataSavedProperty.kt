package haina.ecommerce.room.roomsavedproperty

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "datasavedproperty")
@Parcelize
data class DataSavedProperty(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name ="photo")
    var photo: String,

    @ColumnInfo(name = "selling_price")
    var sellingPrice: Int?,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "address")
    var address: String?,

    @ColumnInfo(name = "rental_price")
    var rentalPrice: Int?

):Parcelable