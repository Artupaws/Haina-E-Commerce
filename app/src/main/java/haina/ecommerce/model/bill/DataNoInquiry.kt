package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Parcelize
data class DataNoInquiry (
    @field:SerializedName ("rq_uuid")
    val rqUuid:String? = null,

    @field:SerializedName ("rs_datetime")
    val rsDatetime:String? = null,

    @field:SerializedName ("error_code")
    val errorCode:Int? = null,

    @field:SerializedName ("error_desc")
    val errorDesc:String? = null,

    @field:SerializedName ("order_id")
    val orderId:String? = null,

    @field:SerializedName ("bill_amount")
    val billAmount:String? = null,

    @field:SerializedName ("admin_fee")
    val adminFee:String? = null,

    @field:SerializedName ("product")
    val product:String? = null,

    @field:SerializedName ("category")
    val category:String? = null,

    @field:SerializedName ("category_zh")
    val categoryZh:String? = null,

    @field:SerializedName ("icon_code")
    val iconCode:String? = null,

    @field:SerializedName ("bill_data")
    val billData: DataBillNoInquiry? = null
):Parcelable