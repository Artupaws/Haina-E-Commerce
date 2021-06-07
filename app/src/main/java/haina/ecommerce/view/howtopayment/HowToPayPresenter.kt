package haina.ecommerce.view.howtopayment

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.howtopay.ResponseGetHowToPay
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HowToPayPresenter(val view:HowToPayContract, val context: Context) {

    fun getHowToPay(idPaymentMetod:Int){
        val getHowToPay = NetworkConfig().getConnectionHaina().getHowToPay(idPaymentMetod)
        getHowToPay.enqueue(object : retrofit2.Callback<ResponseGetHowToPay>{
            override fun onResponse(call: Call<ResponseGetHowToPay>, response: Response<ResponseGetHowToPay>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetHowToPay(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataHowToPay(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetHowToPay(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetHowToPay>, t: Throwable) {
                view.messageGetHowToPay(t.localizedMessage.toString())
            }
        })
    }
}