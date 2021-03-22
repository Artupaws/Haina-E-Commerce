package haina.ecommerce.view.topup.paketdata

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.pulsaanddata.ResponseGetProductPhone
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class PaketDataPresenter(val view:PaketDataContract, val context: Context) {

    fun checkProvider(phoneNumber:String){
        val checkProvider = NetworkConfig().getConnectionHainaBearer(context).checkProvider(phoneNumber)
        checkProvider.enqueue(object : retrofit2.Callback<ResponseGetProductPhone>{
            override fun onResponse(call: Call<ResponseGetProductPhone>, response: Response<ResponseGetProductPhone>) {
                if (response.isSuccessful && response.body()?.value == 1){
//                    val data = response.body()?.productPhone?.group?.data
                    view.messageCheckProviderAndProduct(response.body()?.message.toString())
//                    view.getProductPhone(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCheckProviderAndProduct(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetProductPhone>, t: Throwable) {
                view.messageCheckProviderAndProduct(t.localizedMessage.toString())
            }

        })
    }
}