package haina.ecommerce.view.property.fragmentaddphoto

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.property.ResponseCreatePostProperty
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class AddPhotoPresenter(val view:AddPhotoContract.View, val context: Context) {

    fun createPostProperty(propertyType: RequestBody, condition:RequestBody, title:RequestBody, year:RequestBody, idCity:RequestBody,
                           floorLevel:RequestBody, bedRoom:RequestBody?, bathRoom:RequestBody?, buildingArea:Int, landArea:Int, certificateType:RequestBody?, address:RequestBody, latitude:RequestBody?, longitude:RequestBody?,
                           sellingPrice:RequestBody?, rentalPrice:RequestBody?, facilities:RequestBody?, description:RequestBody, images:ArrayList<MultipartBody.Part>){
        view.showLoading()
        val getList = NetworkConfig().getConnectionHainaBearer(context).createPostProperty(propertyType, condition, title, year, idCity, floorLevel, bedRoom, bathRoom, buildingArea,landArea, certificateType, address, latitude, longitude, sellingPrice, rentalPrice, facilities, description, images)
        getList.enqueue(object : retrofit2.Callback<ResponseCreatePostProperty>{
            override fun onResponse(call: Call<ResponseCreatePostProperty>, response: Response<ResponseCreatePostProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreatePost(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCreatePost(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreatePostProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageCreatePost(t.localizedMessage.toString())
            }
        })
    }

}