package haina.ecommerce.view.property.fragmentaddphoto

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.property.ResponseCreatePostProperty
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class AddPhotoPresenter(val view:AddPhotoContract.View, val context: Context) {

    fun createPostProperty(propertyType:String, condition:String, title:String, year:String, idCity:Int,
                           floorLevel:String, bedRoom:Int, bathRoom:Int, buildingArea:Int, certificateType:String, address:String, latitude:Int, longitude:Int, sellingPrice:Int, rentalPrice:Int, facilities:String, images:List<MultipartBody.Part>){
        view.showLoading()
        val getList = NetworkConfig().getConnectionHainaBearer(context).createPostProperty(propertyType, condition, title, year, idCity, floorLevel, bedRoom, bathRoom, buildingArea, certificateType, address, latitude, longitude, sellingPrice, rentalPrice, facilities, images)
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