package haina.ecommerce.view.history.historytransaction

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.transactionlist.ResponseGetListTransaction
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistoryTransactionPresenter(val view:HistoryTransactionContract, val context: Context) {

    fun getListTransaction(){
        val getListTransaction = NetworkConfig().getConnectionHainaBearer(context).getListTransaction()
        getListTransaction.enqueue(object : retrofit2.Callback<ResponseGetListTransaction>{
            override fun onResponse(call: Call<ResponseGetListTransaction>, response: Response<ResponseGetListTransaction>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListTransaction(response.body()?.message.toString())
                    val data = response.body()?.dataTransaction
                    view.getListTransaction(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListTransaction(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListTransaction>, t: Throwable) {
                view.messageGetListTransaction(t.localizedMessage.toString())
            }

        })
    }

}