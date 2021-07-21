package haina.ecommerce.view.property.editmyproperty

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class EditPropertyPresenter(val view:EditPropertyContract.View, val context: Context) {

    fun getFacilities(){
        view.showLoading()
        val getFacilities = NetworkConfig().getConnectionToDarma(context).getFacilities()
        getFacilities.enqueue(object : retrofit2.Callback<ResponseGetFacilitiesProperty>{
            override fun onResponse(call: Call<ResponseGetFacilitiesProperty>, response: Response<ResponseGetFacilitiesProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetFacilities(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataFacilites(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetFacilities(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetFacilitiesProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageGetFacilities(t.localizedMessage.toString())
            }

        })
    }

    fun getProvince(){
//        view.showLoading()
        val getProvince = NetworkConfig().getConnectionToDarma(context).getProvince()
        getProvince.enqueue(object : retrofit2.Callback<ResponseGetProvince>{
            override fun onResponse(call: Call<ResponseGetProvince>, response: Response<ResponseGetProvince>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetProvince(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataProvince(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetProvince(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProvince>, t: Throwable) {
                view.dismissLoading()
                view.messageGetProvince(t.localizedMessage.toString())
            }

        })
    }

    fun getCity(idProvince:Int){
//        view.showLoading()
        val getCity = NetworkConfig().getConnectionToDarma(context).getCity(idProvince)
        getCity.enqueue(object : retrofit2.Callback<ResponseGetCity>{
            override fun onResponse(call: Call<ResponseGetCity>, response: Response<ResponseGetCity>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetCity(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataCity(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetCity(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCity>, t: Throwable) {
                view.dismissLoading()
                view.messageGetCity(t.localizedMessage.toString())
            }

        })
    }

    fun updateProperty(propertyType: String, condition: String, title: String, year: String, idCity: Int,
                           floorLevel: Int, bedRoom: Int?, bathRoom: Int?, buildingArea:Int, landArea:Int, certificateType:String?, address: String, latitude: String?, longitude: String?,
                           sellingPrice: String?, rentalPrice: String?, facilities: String, description: String){
        view.showLoading()
        val getList = NetworkConfig().getConnectionHainaBearer(context).updateMyProperty(propertyType, condition, title, year, idCity, floorLevel, bedRoom, bathRoom, buildingArea,landArea, certificateType, address, latitude, longitude, sellingPrice, rentalPrice, facilities, description)
        getList.enqueue(object : retrofit2.Callback<ResponseCreatePostProperty>{
            override fun onResponse(call: Call<ResponseCreatePostProperty>, response: Response<ResponseCreatePostProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateProperty(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageUpdateProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreatePostProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageUpdateProperty(t.localizedMessage.toString())
            }
        })
    }

}