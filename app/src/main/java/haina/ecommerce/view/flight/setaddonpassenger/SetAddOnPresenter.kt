package haina.ecommerce.view.flight.setaddonpassenger

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.flight.ResponseGetAddOn
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class SetAddOnPresenter(val view:SetAddOnContract, val context: Context) {

    fun getDataAddOn(){
        val getAddOn = NetworkConfig().getConnectionHainaBearer(context).getDataAddOn()
        getAddOn.enqueue(object : retrofit2.Callback<ResponseGetAddOn>{
            override fun onResponse(call: Call<ResponseGetAddOn>, response: Response<ResponseGetAddOn>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAddOn(response.body()?.message.toString())
                    val data = response.body()?.dataAddOn
                    view.getDataAddOn(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAddOn(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetAddOn>, t: Throwable) {
                view.messageGetAddOn(t.localizedMessage.toString())
            }
        })
    }
    fun getDataSeat(){
        val getAddOn = NetworkConfig().getConnectionHainaBearer(context).getDataAddOn()
        getAddOn.enqueue(object : retrofit2.Callback<ResponseGetAddOn>{
            override fun onResponse(call: Call<ResponseGetAddOn>, response: Response<ResponseGetAddOn>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetAddOn(response.body()?.message.toString())
                    val data = response.body()?.dataAddOn
                    view.getDataAddOn(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetAddOn(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetAddOn>, t: Throwable) {
                view.messageGetAddOn(t.localizedMessage.toString())
            }
        })
    }

}