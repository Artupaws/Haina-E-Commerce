package haina.ecommerce.model.forum

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tableImagePost")
@Parcelize
data class ImagePostData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "image")
    var image:String
):Parcelable
