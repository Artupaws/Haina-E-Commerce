package haina.ecommerce.roomdatapassenger

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "datapassenger")
@Parcelize
data class DataPassenger (
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "fullname")
    var fullname:String,

    @ColumnInfo(name = "birthdate")
    var birthdate:String,

    @ColumnInfo(name = "idcard")
    var idcard:String?
):Parcelable