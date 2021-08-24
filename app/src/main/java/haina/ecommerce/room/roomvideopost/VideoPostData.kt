package haina.ecommerce.room.roomvideopost

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "tableVideoPost")
@Parcelize
data class VideoPostData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "video")
    var video:String
):Parcelable
