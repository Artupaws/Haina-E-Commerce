package haina.ecommerce.view.history.historyinterviewapplicant

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetShortList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistoryInterviewPresenter(val view: HistoryInterviewContract, val context: Context) {

    fun getInterviewApplicant(status:String){
        val getInterviewApplicant = NetworkConfig().getConnectionHainaBearer(context).getShortlistApplicant(status)
        getInterviewApplicant.enqueue(object : retrofit2.Callback<ResponseGetShortList>{
            override fun onResponse(call: Call<ResponseGetShortList>, response: Response<ResponseGetShortList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getInterviewApplicant(data)
                    view.messageStatusSuccess(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageStatusFailed(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetShortList>, t: Throwable) {
                view.messageStatusSuccess(t.localizedMessage.toString())
            }

        })
    }

}