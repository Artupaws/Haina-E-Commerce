package haina.ecommerce.view.job

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckRegisterCompany
import haina.ecommerce.model.ResponseGetJob
import haina.ecommerce.model.ResponseJobCategory
import haina.ecommerce.model.ResponseListJobLocation
import haina.ecommerce.model.vacancy.ResponseGetDataCreateVacancy
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class JobPresenter(val view: JobContract.View, val context: Context){

    fun loadListJobVacancy(data:MutableMap<String, Int> = HashMap()){
        view.showLoading()
        val callListJobVacancy = NetworkConfig().getConnectionHaina().getListJobVacancy(data)
            callListJobVacancy.enqueue(object : retrofit2.Callback<ResponseGetJob> {
                override fun onResponse(call: Call<ResponseGetJob>, response: Response<ResponseGetJob>) {
                    view.dismissLoading()
                    if (response.isSuccessful && response.body()?.value == 1) {
                        val data = response.body()?.data
                        val size = response.body()?.data?.size
                        view.getLoadListJob(data)
                        view.messageLoadListJob(response.message().toString())
                        view.getDataSize(size)
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.messageLoadListJob(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseGetJob>, t: Throwable) {
                    view.dismissLoading()
                    view.messageLoadListJob(t.localizedMessage)
                }

            })
    }

    fun loadListJobCategory(){
        view.showLoading()
        val callListJobCategory = NetworkConfig().getConnectionHaina().getDataListJobCategory()
        callListJobCategory.enqueue(object : retrofit2.Callback<ResponseJobCategory> {
            override fun onResponse(call: Call<ResponseJobCategory>, response: Response<ResponseJobCategory>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.getLoadJobCategory(data)
                    view.messageLoadJobCategory(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messageLoadJobCategory(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseJobCategory>, t: Throwable) {
                view.dismissLoading()
                view.messageLoadJobCategory(t.localizedMessage)
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

    fun checkRegisterCompany(){
        view.showLoading()
        val callCheckRegisterCompany = NetworkConfig().getConnectionHainaBearer(context).checkRegisterCompany()
        callCheckRegisterCompany.enqueue(object : retrofit2.Callback<ResponseCheckRegisterCompany>{
            override fun onResponse(call: Call<ResponseCheckRegisterCompany>, response: Response<ResponseCheckRegisterCompany>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageCheckRegisterCompany(response.body()?.message.toString())
                    val data = response.body()?.dataCompany
                    view.getDataRegisterCompany(data!!)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageCheckRegisterCompany(error.getString("message"))
                }
            }
            override fun onFailure(call: Call<ResponseCheckRegisterCompany>, t: Throwable) {
                view.dismissLoading()
                view.messageCheckRegisterCompany(t.localizedMessage.toString())
            }

        })
    }

}