package haina.ecommerce.room.roomdataaddons

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class DataAddons(

    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "first_name")
    var first_name:String,
)
