package haina.ecommerce.view.job

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetJob
import haina.ecommerce.model.ResponseJobCategory
import haina.ecommerce.model.ResponseListJobLocation
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class JobPresenter(val view: JobContract){

    fun loadListJobVacancy(data:MutableMap<String, Int> = HashMap()){
        val callListJobVacancy = NetworkConfig().getConnectionHaina().getListJobVacancy(data)
            callListJobVacancy.enqueue(object : retrofit2.Callback<ResponseGetJob> {
                override fun onResponse(
                    call: Call<ResponseGetJob>,
                    response: Response<ResponseGetJob>) {
                    if (response.isSuccessful && response.body()?.value == 1) {
                        val data = response.body()?.data
                        val size = response.body()?.data?.size
                        view.getLoadListJob(data)
                        view.successLoadListJob(response.message().toString())
                        view.getDataSize(size)
                    } else {
                        val error = JSONObject(response.errorBody()?.string()!!)
                        view.errorLoadListJob(error.getString("message"))
                    }
                }

                override fun onFailure(call: Call<ResponseGetJob>, t: Throwable) {
                    view.errorLoadListJob(t.localizedMessage)
                }

            })
    }

    fun loadListJobCategory(){
        val callListJobCategory = NetworkConfig().getConnectionHaina().getDataListJobCategory()
        callListJobCategory.enqueue(object : retrofit2.Callback<ResponseJobCategory> {
            override fun onResponse(
                call: Call<ResponseJobCategory>,
                response: Response<ResponseJobCategory>
            ) {
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.getLoadJobCategory(data)
                    view.successLoadJobCategory(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.errorLoadJobCategory(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseJobCategory>, t: Throwable) {
                view.errorLoadJobCategory(t.localizedMessage)
            }

        })
    }

    fun loadListJobLocation() {
        val callListJobLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        callListJobLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation> {
            override fun onResponse(
                call: Call<ResponseListJobLocation>,
                response: Response<ResponseListJobLocation>
            ) {
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.getLoadListLocation(data)
                    view.successLoadListLocation(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.errorLoadListLocation(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.errorLoadListLocation(t.localizedMessage)
            }

        })
    }

}