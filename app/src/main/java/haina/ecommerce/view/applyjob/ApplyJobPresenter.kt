package haina.ecommerce.view.applyjob

import android.content.Context
import android.util.Log
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseApplyJob
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.ResponseLoadDocumentUser
import haina.ecommerce.util.Constants
import retrofit2.Call
import retrofit2.Response

class ApplyJobPresenter(val view: ApplyJobContract, val context: Context) {

    fun loadDocumentResume(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    Log.d("document", response.body()?.data.toString())
                    view.getDocumentResume(data)
                    view.messagetGetDocument(response.body()?.message.toString())
                } else {
                    view.messagetGetDocument(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messagetGetDocument(t.localizedMessage.toString())
            }

        })
    }

    fun getDataUserProfile(){
        NetworkConfig().getConnectionHainaBearer(context).getDataUser(Constants.APIKEY)
            .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                    if (response.isSuccessful && response.body()?.value == 1){
                        val data = response.body()?.data
                        view.getDataUser(data)
                        view.messageGetDataPersonal(response.body()?.message.toString())
                    } else {
                        view.messageGetDataPersonal(response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    view.messageGetDataPersonal(t.localizedMessage)
                }
            })
    }

    fun applyJob(idJobVacancy:Int, idUserDocs:Int){
        val applyJob = NetworkConfig().getConnectionHainaBearer(context).applyJob(idJobVacancy, idUserDocs)
        applyJob.enqueue(object :retrofit2.Callback<ResponseApplyJob>{
            override fun onResponse(call: Call<ResponseApplyJob>, response: Response<ResponseApplyJob>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageApplyJob(response.body()?.message.toString())
                } else {
                    view.messageApplyJob(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseApplyJob>, t: Throwable) {
                view.messageApplyJob(t.localizedMessage.toString())
            }

        })
    }

}