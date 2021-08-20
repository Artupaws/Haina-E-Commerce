package haina.ecommerce.view.property.detailmyproperty

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.hotels.newHotel.ResponseGetCityHotel
import haina.ecommerce.model.property.ResponseDeleteProperty
import haina.ecommerce.model.property.ResponseUpdateStatusProperty
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailMyPropertyPresenter(val view:DetailMyPropertyContract.View, val context: Context) {

    fun deleteProperty(idProperty:Int){
        view.showLoading()
        val deleteProperty = NetworkConfig().getConnectionHainaBearer(context).deleteProperty(idProperty)
        deleteProperty.enqueue(object : retrofit2.Callback<ResponseDeleteProperty>{
            override fun onResponse(call: Call<ResponseDeleteProperty>, response: Response<ResponseDeleteProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteProperty(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageDeleteProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseDeleteProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageDeleteProperty(t.localizedMessage.toString())
            }
        })
    }

    fun updateStatusProperty(idTransaction:Int, status:String){
        view.showLoading()
        val deleteProperty = NetworkConfig().getConnectionHainaBearer(context).updateStatusProperty(idTransaction,status)
        deleteProperty.enqueue(object : retrofit2.Callback<ResponseUpdateStatusProperty>{
            override fun onResponse(call: Call<ResponseUpdateStatusProperty>, response: Response<ResponseUpdateStatusProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateStatusProperty(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageUpdateStatusProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUpdateStatusProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageUpdateStatusProperty(t.localizedMessage.toString())
            }
        })
    }

}