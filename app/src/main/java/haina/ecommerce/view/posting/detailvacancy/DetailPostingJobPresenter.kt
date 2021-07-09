package haina.ecommerce.view.posting.detailvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetListJobApplicant
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailPostingJobPresenter(val view:DetailPostingJobContract, val context: Context) {

    fun getListJobApplicant(idJob: Int){
        val getListJobApplicant = NetworkConfig().getConnectionHainaBearer(context).getListJobApplicant(idJob)
        getListJobApplicant.enqueue(object : retrofit2.Callback<ResponseGetListJobApplicant>{
            override fun onResponse(call: Call<ResponseGetListJobApplicant>, response: Response<ResponseGetListJobApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getListJobApplicant(data)
                    view.messageGetListSuccess(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetListFailed(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListJobApplicant>, t: Throwable) {
                view.messageGetListFailed(t.localizedMessage.toString())
            }

        })
    }

}