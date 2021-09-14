package haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddShortlistApplicant
import haina.ecommerce.model.ResponseGetJobApplications
import haina.ecommerce.model.ResponseGetShortList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistorySubmitPresenter(val view:HistorySubmitContract, val context: Context) {

    fun getJobSubmit(){
        val getJobSubmit = NetworkConfig().getConnectionHainaBearer(context).getJobApplication()
        getJobSubmit.enqueue(object : retrofit2.Callback<ResponseGetJobApplications>{
            override fun onResponse(call: Call<ResponseGetJobApplications>, response: Response<ResponseGetJobApplications>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListSubmitJob(data)
                    view.messageGetSubmitJobSuccess(response.body()?.message.toString())
                } else {
                    view.messageGetSubmitJobError(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseGetJobApplications>, t: Throwable) {
                view.messageGetSubmitJobError(t.localizedMessage.toString())
            }

        })
    }

}