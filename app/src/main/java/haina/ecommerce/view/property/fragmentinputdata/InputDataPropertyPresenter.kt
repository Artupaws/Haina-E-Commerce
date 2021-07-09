package haina.ecommerce.view.property.fragmentinputdata

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseGetCity
import haina.ecommerce.model.property.ResponseGetFacilitiesProperty
import haina.ecommerce.model.property.ResponseGetProvince
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class InputDataPropertyPresenter(val view:InputDataPropertyContract.View, val context: Context) {

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

}