package haina.ecommerce.view.history.historysubmitapplication.detailapplication

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckAppliedJob
import haina.ecommerce.model.vacancy.ResponseApplicationDetail
import retrofit2.Call
import retrofit2.Response

class DetailApplicationPresenter(val view: DetailApplicationContract, val context: Context)  {

    fun getApplicationDetail(idApplication:Int){
        val checkAppliedJob = NetworkConfig().getConnectionHainaBearer(context).getJobApplicationDetail(idApplication)
        checkAppliedJob.enqueue(object : retrofit2.Callback<ResponseApplicationDetail>{
            override fun onResponse(call: Call<ResponseApplicationDetail>, response: Response<ResponseApplicationDetail>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetApplicationDetail(response.body()?.message.toString())
                    view.getApplicationDetail(response.body()?.data!!)
                } else {
                    view.messageGetApplicationDetail(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseApplicationDetail>, t: Throwable) {
                view.messageGetApplicationDetail(t.localizedMessage.toString())
            }

        })
    }
}