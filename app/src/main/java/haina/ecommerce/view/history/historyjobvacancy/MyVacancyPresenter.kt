package haina.ecommerce.view.history.historyjobvacancy

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseListJobLocation
import haina.ecommerce.model.vacancy.ResponseGetDataCreateVacancy
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

    fun getDataCreateVacancy(){
        view.showLoading()
        val dataCreateVacancy = NetworkConfig().getConnectionHainaBearer(context).getDataCreateVacancy()
        dataCreateVacancy.enqueue(object : retrofit2.Callback<ResponseGetDataCreateVacancy>{
            override fun onResponse(call: Call<ResponseGetDataCreateVacancy>, response: Response<ResponseGetDataCreateVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetDataCreateVacancy(response.body()?.message.toString())
                    val data = response.body()?.dataCreateVacancy
                    view.getDataCreateVacancy(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetDataCreateVacancy(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetDataCreateVacancy>, t: Throwable) {
                view.messageGetDataCreateVacancy(t.localizedMessage.toString())
            }

        })
    }

    fun loadListJobLocation() {
        view.showLoading()
        val callListJobLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        callListJobLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation> {
            override fun onResponse(call: Call<ResponseListJobLocation>, response: Response<ResponseListJobLocation>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.getLoadListLocation(data)
                    view.messageLoadListLocation(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageLoadListLocation(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.dismissLoading()
                view.messageLoadListLocation(t.localizedMessage)
            }

        })
    }

}