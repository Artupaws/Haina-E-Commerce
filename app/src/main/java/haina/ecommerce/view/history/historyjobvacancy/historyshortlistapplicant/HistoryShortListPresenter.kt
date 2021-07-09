package haina.ecommerce.view.history.historyjobvacancy.historyshortlistapplicant

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddShortlistApplicant
import haina.ecommerce.model.ResponseGetShortList
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class HistoryShortListPresenter(val view:HistoryShortListContract, val context: Context) {

    fun getShortlistApplicant(status:String){
        val getShortListApplicant = NetworkConfig().getConnectionHainaBearer(context).getShortlistApplicant(status)
        getShortListApplicant.enqueue(object : retrofit2.Callback<ResponseGetShortList>{
            override fun onResponse(call: Call<ResponseGetShortList>, response: Response<ResponseGetShortList>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getShortListApplicant(data)
                    view.messageGetShortListSuccess(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetShortListError(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetShortList>, t: Throwable) {
                view.messageGetShortListError(t.localizedMessage.toString())
            }

        })
    }

    fun declinedApplicant(idApplicant:Int, status:String){
        val addShortlistApplicant = NetworkConfig().getConnectionHainaBearer(context).addShortlistApplicant(idApplicant, status)
        addShortlistApplicant.enqueue(object : retrofit2.Callback<ResponseAddShortlistApplicant>{
            override fun onResponse(call: Call<ResponseAddShortlistApplicant>, response: Response<ResponseAddShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeclineApplicant(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageDeclineApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddShortlistApplicant>, t: Throwable) {
                view.messageDeclineApplicant(t.localizedMessage.toString())
            }

        })
    }

    fun interviewApplicant(idApplicant:Int, status:String){
        val addShortlistApplicant = NetworkConfig().getConnectionHainaBearer(context).addShortlistApplicant(idApplicant, status)
        addShortlistApplicant.enqueue(object : retrofit2.Callback<ResponseAddShortlistApplicant>{
            override fun onResponse(call: Call<ResponseAddShortlistApplicant>, response: Response<ResponseAddShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeclineApplicant(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageDeclineApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddShortlistApplicant>, t: Throwable) {
                view.messageDeclineApplicant(t.localizedMessage.toString())
            }

        })
    }

    fun acceptApplicant(idApplicant:Int, status:String){
        val addShortlistApplicant = NetworkConfig().getConnectionHainaBearer(context).addShortlistApplicant(idApplicant, status)
        addShortlistApplicant.enqueue(object : retrofit2.Callback<ResponseAddShortlistApplicant>{
            override fun onResponse(call: Call<ResponseAddShortlistApplicant>, response: Response<ResponseAddShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeclineApplicant(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageDeclineApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddShortlistApplicant>, t: Throwable) {
                view.messageDeclineApplicant(t.localizedMessage.toString())
            }

        })
    }

}