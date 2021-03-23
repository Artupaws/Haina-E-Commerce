package haina.ecommerce.view.topup

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.pulsaanddata.ResponseGetProductPhone
import haina.ecommerce.util.Constants
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class TopupPresenter(val view: TopupContract, val context: Context) {

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser(Constants.APIKEY)
                .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                    override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                        if (response.isSuccessful && response.body()?.value == 1){
                            val data = response.body()?.data
                            view.getDataUser(data)
                            view.messageGetDataUser(response.body()?.message.toString())
                        } else {
                            val error = JSONObject(response.errorBody()?.string()!!)
                            view.messageGetDataUser(error.getString("message"))
                        }
                    }

                    override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                        view.messageGetDataUser(t.localizedMessage)
                    }

                })
    }

    fun getProviderName(phoneNumber:String){
        val checkProvider = NetworkConfig().getConnectionHainaBearer(context).checkProvider(phoneNumber)
        checkProvider.enqueue(object : retrofit2.Callback<ResponseGetProductPhone>{
            override fun onResponse(call: Call<ResponseGetProductPhone>, response: Response<ResponseGetProductPhone>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.productPhone
                    view.messageGetProviderName(response.body()?.message.toString())
                    view.getProviderName(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetProviderName(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProductPhone>, t: Throwable) {
                view.messageGetProviderName(t.localizedMessage.toString())
            }

        })
    }

}