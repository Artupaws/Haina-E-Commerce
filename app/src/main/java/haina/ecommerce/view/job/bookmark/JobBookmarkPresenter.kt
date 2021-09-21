package haina.ecommerce.view.job.bookmark

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseCheckRegisterCompany
import haina.ecommerce.model.ResponseGetJob
import haina.ecommerce.model.ResponseJobCategory
import haina.ecommerce.model.ResponseListJobLocation
import haina.ecommerce.model.vacancy.ResponseGetAllVacancy
import haina.ecommerce.model.vacancy.ResponseGetDataCreateVacancy
import haina.ecommerce.model.vacancy.ResponseJobBookmark
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class JobBookmarkPresenter(val view: JobBookmarkContract.View, val context: Context){

    fun loadListBookmark(){
        view.showLoading()
        val callListJobVacancy = NetworkConfig().getConnectionHainaBearer(context).getListJobBookmark()
            callListJobVacancy.enqueue(object : retrofit2.Callback<ResponseGetAllVacancy> {
                override fun onResponse(call: Call<ResponseGetAllVacancy>, response: Response<ResponseGetAllVacancy>) {
                    view.dismissLoading()
                    if (response.isSuccessful && response.body()?.value == 1) {
                        val data = response.body()?.data
                        view.getLoadListBookmark(data)
                        view.messageLoadListBookmark(response.body()!!.message.toString())
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.notAvailableBookmark()
                        view.messageLoadListBookmark(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseGetAllVacancy>, t: Throwable) {
                    view.dismissLoading()
                    view.messageLoadListBookmark(t.localizedMessage)
                }

            })
    }


    fun removeBookmark(idVacancy: Int){
        view.showLoading()
        val callListJobCategory = NetworkConfig().getConnectionHainaBearer(context).jobRemoveBookmark(idVacancy)
        callListJobCategory.enqueue(object : retrofit2.Callback<ResponseJobBookmark> {
            override fun onResponse(call: Call<ResponseJobBookmark>, response: Response<ResponseJobBookmark>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.messageChangeBookmark(response.body()?.value!!)

                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                }
            }

            override fun onFailure(call: Call<ResponseJobBookmark>, t: Throwable) {
                view.dismissLoading()
                view.messageChangeBookmark(0)

//                if(t is NoConnectivityException) {
//                    view.showLoading()
//                }
            }

        })
    }

}