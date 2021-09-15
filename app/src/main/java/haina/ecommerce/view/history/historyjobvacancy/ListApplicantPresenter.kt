package haina.ecommerce.view.history.historyjobvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.model.vacancy.ResponseGetListApplicant
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ListApplicantPresenter(private val view:MyVacancyContract.ViewListApplicant, private val context: Context) {

    fun getListApplicant(idVacancy:Int){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).getListApplicant(idVacancy)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGetListApplicant>{
            override fun onResponse(call: Call<ResponseGetListApplicant>, response: Response<ResponseGetListApplicant>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListApplicant(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataListApplicant(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListApplicant>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListApplicant(t.localizedMessage.toString())
            }
        })
    }

    fun getListApplicantShortListed(idVacancy:Int){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).getListApplicantShortListed(idVacancy)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGetListApplicant>{
            override fun onResponse(call: Call<ResponseGetListApplicant>, response: Response<ResponseGetListApplicant>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListApplicant(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataListApplicant(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListApplicant>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListApplicant(t.localizedMessage.toString())
            }
        })
    }

    fun getListApplicantInterview(idVacancy:Int){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).getListApplicantInterview(idVacancy)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGetListApplicant>{
            override fun onResponse(call: Call<ResponseGetListApplicant>, response: Response<ResponseGetListApplicant>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListApplicant(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataListApplicant(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListApplicant>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListApplicant(t.localizedMessage.toString())
            }
        })
    }

    fun getListApplicantAccepted(idVacancy:Int){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).getListApplicantAccepted(idVacancy)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGetListApplicant>{
            override fun onResponse(call: Call<ResponseGetListApplicant>, response: Response<ResponseGetListApplicant>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListApplicant(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataListApplicant(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListApplicant(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListApplicant>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListApplicant(t.localizedMessage.toString())
            }
        })
    }


    fun rejectAppliocant(idApplicant:Int, status:String){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).updateApplicantStatus(idApplicant, status)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateApplicantStatus(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageUpdateApplicantStatus(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageUpdateApplicantStatus(t.localizedMessage.toString())
            }
        })
    }
    fun inviteInterview(idApplicant:Int){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).updateApplicantStatus(idApplicant)
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateApplicantStatus(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageUpdateApplicantStatus(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageUpdateApplicantStatus(t.localizedMessage.toString())
            }
        })
    }

}