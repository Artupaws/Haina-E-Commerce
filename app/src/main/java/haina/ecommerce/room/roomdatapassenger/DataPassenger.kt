package haina.ecommerce.room.roomdatapassenger

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

    @ColumnInfo(name = "first_name")
    var first_name:String,

    @ColumnInfo(name = "last_name")
    var last_name:String,

    @ColumnInfo(name = "birth_date")
    var birth_date:String,

    @ColumnInfo(name = "gender")
    var gender:String,

    @ColumnInfo(name = "nationality")
    var nationality:String,

    @ColumnInfo(name = "birth_country")
    var birth_country:String,

    @ColumnInfo(name = "id_number")
    var id_number:String?,

    @ColumnInfo(name = "title")
    var title:String,

    @ColumnInfo(name = "parent")
    var parent:String,

    @ColumnInfo(name = "passport_number")
    var passport_number:String,

    @ColumnInfo(name = "passport_issued_country")
    var passport_issued_country:String?,

    @ColumnInfo(name = "passport_issued_date")
    var passport_issued_date:String?,

    @ColumnInfo(name = "passport_expired_date")
    var passport_expired_date:String?,

    @ColumnInfo(name = "type")
    var type:String,
):Parcelable