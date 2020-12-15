package haina.ecommerce.view.job

import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetListJob
import haina.ecommerce.model.ResponseJobCategory
import retrofit2.Call
import retrofit2.Response

class JobPresenter (val view: JobContract){

    fun loadListJobVacancy(apiKey:String){
        val callListJobVacancy = NetworkConfig().getConnectionHaina().getListJobVacancy(apiKey)
            .enqueue(object : retrofit2.Callback<ResponseGetListJob>{
                override fun onResponse(call: Call<ResponseGetListJob>, response: Response<ResponseGetListJob>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        val data = response.body()?.data
                        view.getLoadListJob(data)
                        view.successLoadListJob(response.message().toString())
                    } else {
                        view.errorLoadListJob(response.message().toString())
                    }
                }

                override fun onFailure(call: Call<ResponseGetListJob>, t: Throwable) {
                    view.errorLoadListJob(t.localizedMessage)
                }

            })
    }

    fun loadListJobCategory(){
        val callListJobCategory = NetworkConfig().getConnectionHaina().getDataListJobCategory()
        callListJobCategory.enqueue(object : retrofit2.Callback<ResponseJobCategory>{
            override fun onResponse(call: Call<ResponseJobCategory>, response: Response<ResponseJobCategory>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getLoadJobCategory(data)
                    view.successLoadJobCategory(response.body()?.message.toString())
                } else {
                   view.errorLoadJobCategory(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseJobCategory>, t: Throwable) {
                view.errorLoadJobCategory(t.localizedMessage)
            }

        })
    }


}