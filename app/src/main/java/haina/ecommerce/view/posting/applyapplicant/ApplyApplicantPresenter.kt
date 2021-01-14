package haina.ecommerce.view.posting.applyapplicant

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddShortlistApplicant
import haina.ecommerce.model.ResponseGetApplicantStatus
import haina.ecommerce.model.ResponseLoadDocumentUser
import haina.ecommerce.model.ResponseMyJob
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ApplyApplicantPresenter(val view: ApplyApplicantContract, val context: Context) {

    fun addShortlistApplicant(idApplicant:Int, status:String){
        val addShortlistApplicant = NetworkConfig().getConnectionHainaBearer(context).addShortlistApplicant(idApplicant, status)
        addShortlistApplicant.enqueue(object : retrofit2.Callback<ResponseAddShortlistApplicant>{
            override fun onResponse(call: Call<ResponseAddShortlistApplicant>, response: Response<ResponseAddShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddShortlistApplicant(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageAddShortlistApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseAddShortlistApplicant>, t: Throwable) {
                view.messageAddShortlistApplicant(t.localizedMessage.toString())
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

    fun getStatusApplicant(idApplicant:Int){
        val getStatusApplicant = NetworkConfig().getConnectionHainaBearer(context).getStatusApplicant(idApplicant)
        getStatusApplicant.enqueue(object : retrofit2.Callback<ResponseGetApplicantStatus>{
            override fun onResponse(call: Call<ResponseGetApplicantStatus>, response: Response<ResponseGetApplicantStatus>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.statusApplicant
                    view.getApplicantStatus(data)
                    view.messageGetApplicantStatus(response.body()?.message!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetApplicantStatus(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetApplicantStatus>, t: Throwable) {
                view.messageGetApplicantStatus(t.localizedMessage.toString())
            }

        })
    }
}