package haina.ecommerce.model.bill

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.math.BigInteger

@Parcelize
data class DataInquiry (

    @field:SerializedName ("datetime")
    val dateTime:String? = null,

    @field:SerializedName ("bill_amount")
    val billAmount:String? = null,

    @field:SerializedName ("admin_fee")
    val adminFee:String? = null,

    @field:SerializedName ("product")
    val product:String? = null,

    @field:SerializedName("product_code")
    val productCode: String? = null,

    @field:SerializedName ("category")
    val category:String? = null,

    @field:SerializedName ("category_zh")
    val categoryZh:String? = null,

    @field:SerializedName ("icon_code")
    val iconCode:String? = null,

    @field:SerializedName ("bill_data")
    val billData: DataBill? = null,

    @field:SerializedName("inquiry")
    val inquiry:Int? = null
):Parcelable