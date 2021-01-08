package haina.ecommerce.view.history.historysubmitapplication

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetJobApplications
import retrofit2.Call
import retrofit2.Response

class HistorySubmitJobPresenter(val view:HistorySubmitJobContract, val context: Context) {
    fun getJobSubmit(){
        val getJobSubmit = NetworkConfig().getConnectionHainaBearer(context).getJobApplication()
        getJobSubmit.enqueue(object : retrofit2.Callback<ResponseGetJobApplications>{
            override fun onResponse(call: Call<ResponseGetJobApplications>, response: Response<ResponseGetJobApplications>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListSubmitJob(data)
                    view.messageGetSubmitJob(response.body()?.message.toString())
                } else {
                    view.messageGetSubmitJob(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetJobApplications>, t: Throwable) {
                view.messageGetSubmitJob(t.localizedMessage.toString())
            }

        })
    }

}