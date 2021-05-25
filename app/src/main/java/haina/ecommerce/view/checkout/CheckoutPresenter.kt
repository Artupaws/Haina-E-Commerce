package haina.ecommerce.view.checkout

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.bill.ResponseGetBillAmount
import haina.ecommerce.model.checkout.ResponseCheckout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CheckoutPresenter (val view:CheckoutContract, val context: Context) {

    fun checkout(customerNumber:String, idProduct:Int){
        val checkout = NetworkConfig().getConnectionHainaBearer(context).checkout(customerNumber, idProduct)
        checkout.enqueue(object : retrofit2.Callback<ResponseCheckout>{
            override fun onResponse(call: Call<ResponseCheckout>, response: Response<ResponseCheckout>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCheckout(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCheckout(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCheckout>, t: Throwable) {
                view.messageCheckout(t.localizedMessage.toString())
            }

        })
    }
}