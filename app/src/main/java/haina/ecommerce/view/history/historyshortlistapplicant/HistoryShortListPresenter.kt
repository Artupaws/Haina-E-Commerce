package haina.ecommerce.view.history.historyshortlistapplicant

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetShortlistApplicant
import retrofit2.Call
import retrofit2.Response

class HistoryShortListPresenter(val view:HistoryShortListContract, val context: Context) {

    fun getShortlistApplicant(status:String){
        val getShortListApplicant = NetworkConfig().getConnectionHainaBearer(context).getShortlistApplicant(status)
        getShortListApplicant.enqueue(object : retrofit2.Callback<ResponseGetShortlistApplicant>{
            override fun onResponse(call: Call<ResponseGetShortlistApplicant>, response: Response<ResponseGetShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getShortListApplicant(data)
                    view.messageGetShortList(response.body()?.message.toString())
                } else {
                    view.messageGetShortList(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetShortlistApplicant>, t: Throwable) {
                view.messageGetShortList(t.localizedMessage.toString())
            }

        })
    }

}