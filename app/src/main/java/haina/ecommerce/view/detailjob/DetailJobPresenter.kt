package haina.ecommerce.view.detailjob

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckAppliedJob
import retrofit2.Call
import retrofit2.Response

class DetailJobPresenter(val view: DetailJobContract, val context: Context) {

    fun checkAppliedJob(idJob:Int){
        val checkAppliedJob = NetworkConfig().getConnectionHainaBearer(context).checkAppliedJob(idJob)
        checkAppliedJob.enqueue(object : retrofit2.Callback<ResponseCheckAppliedJob>{
            override fun onResponse(call: Call<ResponseCheckAppliedJob>, response: Response<ResponseCheckAppliedJob>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCheckAppliedJob(response.body()?.message.toString())
                } else {
                    view.messageCheckAppliedJob(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCheckAppliedJob>, t: Throwable) {
                view.messageCheckAppliedJob(t.localizedMessage.toString())
            }

        })
    }

}