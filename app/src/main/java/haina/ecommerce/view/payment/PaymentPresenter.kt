package haina.ecommerce.view.payment

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class PaymentPresenter(val view:PaymentContract, val context: Context) {

    fun getPaymentMethod(){
        val getPaymentMethod = NetworkConfig().getConnectionHainaBearer(context).getPaymentMethod()
        getPaymentMethod.enqueue(object :retrofit2.Callback<ResponsePaymentMethod>{
            override fun onResponse(call: Call<ResponsePaymentMethod>, response: Response<ResponsePaymentMethod>) {
                if (response.isSuccessful && response.body()?.value ==1 ){
                    val data = response.body()?.data
                    view.getDataPaymentMethod(data)
                    view.messageGetPaymentMethod(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetPaymentMethod(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponsePaymentMethod>, t: Throwable) {
                view.messageGetPaymentMethod(t.localizedMessage.toString())
            }

        })
    }

}