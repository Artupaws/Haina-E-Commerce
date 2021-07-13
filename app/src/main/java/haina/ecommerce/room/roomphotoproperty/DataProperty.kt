package haina.ecommerce.room.roomphotoproperty

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "dataproperty")
@Parcelize
data class DataProperty(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name ="uri")
    var uri: String,

):Parcelable