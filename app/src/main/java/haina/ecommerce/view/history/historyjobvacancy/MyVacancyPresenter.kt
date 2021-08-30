package haina.ecommerce.view.history.historyjobvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.vacancy.ResponseGetListMyVacancy
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class MyVacancyPresenter(val view:MyVacancyContract.View, val context: Context) {

    fun getListMyVacancy(){
        view.showLoading()
        val getListMyVacancy = NetworkConfig().getConnectionHainaBearer(context).getListMyVacancy()
        getListMyVacancy.enqueue(object : retrofit2.Callback<ResponseGetListMyVacancy>{
            override fun onResponse(call: Call<ResponseGetListMyVacancy>, response: Response<ResponseGetListMyVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListMyVacancy(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListMyVacancy(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListMyVacancy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetListMyVacancy>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListMyVacancy(t.localizedMessage.toString())
            }

        })
    }

}