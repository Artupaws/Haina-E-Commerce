package haina.ecommerce.view.applyjob

import android.content.Context
import android.util.Log
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseApplyJob
import haina.ecommerce.model.ResponseGetDataUser
import haina.ecommerce.model.ResponseLoadDocumentUser
import haina.ecommerce.model.ResponseUploadDocument
import haina.ecommerce.model.forum.ResponseGiveUpvote
import haina.ecommerce.util.Constants
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ApplyJobPresenter(val view: ApplyJobContract.View, val context: Context) {

    fun loadDocumentResume(idDocsCategory:Int){
        view.showLoading()
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    Log.d("document", response.body()?.data.toString())
                    view.getDocumentResume(data)
                    view.messagetGetDocument(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string()!!)
                    view.messagetGetDocument(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.dismissLoading()
                view.messagetGetDocument(t.localizedMessage.toString())
            }

        })
    }

    fun getDataUserProfile(){
        view.showLoading()
        NetworkConfig().getConnectionHainaBearer(context).getDataUser()
            .enqueue(object : retrofit2.Callback<ResponseGetDataUser>{
                override fun onResponse(call: Call<ResponseGetDataUser>, response: Response<ResponseGetDataUser>) {
                    view.dismissLoading()
                    if (response.isSuccessful && response.body()?.value == 1){
                        val data = response.body()?.data
                        view.getDataUser(data)
                        view.messageGetDataPersonal(response.body()?.message.toString())
                    } else {
                        view.messageGetDataPersonal(response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<ResponseGetDataUser>, t: Throwable) {
                    view.dismissLoading()
                    view.messageGetDataPersonal(t.localizedMessage)
                }
            })
    }

    fun applyJob(idJobVacancy:Int, applicantNotes:String, idResume:Int){
        view.showLoading()
        val applyJob = NetworkConfig().getConnectionHainaBearer(context).applyJobVacancy(idJobVacancy, applicantNotes, idResume)
        applyJob.enqueue(object :retrofit2.Callback<ResponseGiveUpvote>{
            override fun onResponse(call: Call<ResponseGiveUpvote>, response: Response<ResponseGiveUpvote>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageApplyJob(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageApplyJob(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGiveUpvote>, t: Throwable) {
                view.dismissLoading()
                view.messageApplyJob(t.localizedMessage.toString())
            }

        })
    }

    fun uploadDocument(file: MultipartBody.Part, name: RequestBody, idDocCategory: RequestBody){
        view.showLoading()
        val uploadDocument = NetworkConfig().getConnectionHainaBearer(context).uploadDocument(file, name, idDocCategory)
        uploadDocument.enqueue(object : retrofit2.Callback<ResponseUploadDocument>{
            override fun onResponse(call: Call<ResponseUploadDocument>, response: Response<ResponseUploadDocument>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUploadDocument("upload file success")
                } else {
                    view.messageUploadDocument(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseUploadDocument>, t: Throwable) {
                view.dismissLoading()
                view.messageUploadDocument("Failed")
            }

        })
    }

}