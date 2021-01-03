package haina.ecommerce.view.myaccount.addrequirement

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseLoadDocumentUser
import haina.ecommerce.model.ResponseUploadDocument
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class AddRequirementPresenter(val view:AddRequirementContract, val context: Context) {

    fun uploadDocument(file:MultipartBody.Part, name:RequestBody, idDocCategory:RequestBody){
        val uploadDocument = NetworkConfig().getConnectionHainaBearer(context).uploadDocument(file, name, idDocCategory)
        uploadDocument.enqueue(object : retrofit2.Callback<ResponseUploadDocument>{
            override fun onResponse(call: Call<ResponseUploadDocument>, response: Response<ResponseUploadDocument>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUploadDocument("upload file success")
                } else {
                    view.messageUploadDocument(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseUploadDocument>, t: Throwable) {
                view.messageUploadDocument("Failed")
            }

        })
    }

}