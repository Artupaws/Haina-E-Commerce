package haina.ecommerce.view.history.historyjobvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
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

}