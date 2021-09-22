package haina.ecommerce.view.job.filter

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseListJobLocation
import haina.ecommerce.model.vacancy.ResponseGetDataCreateVacancy
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class BottomSheetFilterPresenter(val view: BottomSheetFilterContract.View, val context: Context) {

    fun getDataCreateVacancy(){
        view.showLoading()
        val dataCreateVacancy = NetworkConfig().getConnectionHainaBearer(context).getDataCreateVacancy()
        dataCreateVacancy.enqueue(object : retrofit2.Callback<ResponseGetDataCreateVacancy>{
            override fun onResponse(call: Call<ResponseGetDataCreateVacancy>, response: Response<ResponseGetDataCreateVacancy>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetData(response.body()?.message.toString())
                    val data = response.body()?.dataCreateVacancy
                    view.getVacancyData(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetData(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetDataCreateVacancy>, t: Throwable) {
                view.messageGetData(t.localizedMessage.toString())
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
                    view.messageGetData(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageGetData(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.dismissLoading()
                view.messageGetData(t.localizedMessage)
            }

        })
    }



}