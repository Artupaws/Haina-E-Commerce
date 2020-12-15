package haina.ecommerce.view.postingjob

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseJobCategory
import haina.ecommerce.model.ResponseListJobLocation
import haina.ecommerce.model.ResponsePostingJobVacancy
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class PostingJobPresenter(val view: PostingJobContract, val context: Context) {

    fun loadListJobCategory(){
        val callListJobCategory = NetworkConfig().getConnectionHaina().getDataListJobCategory()
        callListJobCategory.enqueue(object : retrofit2.Callback<ResponseJobCategory> {
            override fun onResponse(
                call: Call<ResponseJobCategory>,
                response: Response<ResponseJobCategory>
            ) {
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.loadJobCategory(data)
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

    fun loadListJobLocation(){
        val callListJobLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        callListJobLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation> {
            override fun onResponse(
                call: Call<ResponseListJobLocation>,
                response: Response<ResponseListJobLocation>
            ) {
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.data
                    view.loadJobLocation(data)
                    view.successLoadJobCategory(response.body()?.message.toString())
                } else {
                    view.errorLoadJobCategory(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.errorLoadJobCategory(t.localizedMessage)
            }

        })
    }

    fun postingJobVacancy(imageCompany: MultipartBody.Part, title: RequestBody, idLocation: RequestBody, idCategory: RequestBody, description: RequestBody, salaryFrom: RequestBody, salaryTo: RequestBody){
        val callPostingJobVacancy = NetworkConfig().getConnectionHainaBearer(context).postingJobVacancy(imageCompany, title, idLocation, idCategory, description, salaryFrom, salaryTo)
        callPostingJobVacancy.enqueue(object : retrofit2.Callback<ResponsePostingJobVacancy> {
            override fun onResponse(call: Call<ResponsePostingJobVacancy>, response: Response<ResponsePostingJobVacancy>) {
                if (response.isSuccessful && response.body()?.value == 1) {
                    val data = response.body()?.dataPostingJob
                    view.getValuePostingJob(data)
                    view.successPostingJob(response.body()?.message.toString())
                } else {
                    view.errorPostingJob(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponsePostingJobVacancy>, t: Throwable) {
                view.errorPostingJob(t.localizedMessage)
            }

        })
    }

}