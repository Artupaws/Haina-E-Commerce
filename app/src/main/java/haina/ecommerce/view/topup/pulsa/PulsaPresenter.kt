package haina.ecommerce.view.topup.pulsa

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.pulsaanddata.ResponseGetProductPhone
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class PulsaPresenter (val view:PulsaContract.View, val context: Context) {

    fun checkProvider(phoneNumber:String){
        view.showLoading()
        val checkProvider = NetworkConfig().getConnectionHainaBearer(context).checkProvider(phoneNumber)
        checkProvider.enqueue(object : retrofit2.Callback<ResponseGetProductPhone>{
            override fun onResponse(call: Call<ResponseGetProductPhone>, response: Response<ResponseGetProductPhone>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.productPhone
                    view.messageCheckProviderAndProduct(response.body()?.message.toString())
                    view.getProductPhone(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCheckProviderAndProduct(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProductPhone>, t: Throwable) {
                view.dismissLoading()
                view.messageCheckProviderAndProduct(t.localizedMessage.toString())
            }

        })
    }

}