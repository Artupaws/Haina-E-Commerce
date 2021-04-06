package haina.ecommerce.view.paymentmethod

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.paymentmethod.ResponsePaymentMethod
import haina.ecommerce.model.transaction.ResponseCreateTransactionProductPhone
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

    fun createTransaction(customerNumber:String, idProduct:Int, idPaymentMethod:Int){
        val createTransaction = NetworkConfig().getConnectionHainaBearer(context).createTransactionProductPhone(customerNumber, idProduct, idPaymentMethod)
        createTransaction.enqueue(object : retrofit2.Callback<ResponseCreateTransactionProductPhone>{
            override fun onResponse(call: Call<ResponseCreateTransactionProductPhone>, response: Response<ResponseCreateTransactionProductPhone>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCreateTransaction(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageCreateTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseCreateTransactionProductPhone>, t: Throwable) {
                view.messageCreateTransaction(t.localizedMessage.toString())
            }

        })
    }

}