package haina.ecommerce.view.history.historysubmitapplication

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetJobApplications
import haina.ecommerce.model.vacancy.ResponseMyApplication
import retrofit2.Call
import retrofit2.Response

class HistorySubmitJobPresenter(val view:HistorySubmitJobContract, val context: Context) {
    fun getJobSubmit(){
        val getJobSubmit = NetworkConfig().getConnectionHainaBearer(context).getMyApplication()
        getJobSubmit.enqueue(object : retrofit2.Callback<ResponseMyApplication>{
            override fun onResponse(call: Call<ResponseMyApplication>, response: Response<ResponseMyApplication>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListSubmitJob(data)
                    view.messageGetSubmitJobSuccess(response.body()?.message.toString())
                } else {
                    view.messageGetSubmitJobError(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseMyApplication>, t: Throwable) {
                view.messageGetSubmitJobError(t.localizedMessage.toString())
            }

        })
    }

}