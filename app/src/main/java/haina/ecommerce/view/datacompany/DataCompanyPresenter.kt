package haina.ecommerce.view.datacompany

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

class DataCompanyPresenter(val view: DataCompanyContract, val context: Context) {

    fun getDataCompany(){
        val callGetDataCompany = NetworkConfig().getConnectionHainaBearer(context).getDataCompany()
        callGetDataCompany.enqueue(object : retrofit2.Callback<ResponseCheckRegisterCompany>{
            override fun onResponse(call: Call<ResponseCheckRegisterCompany>, response: Response<ResponseCheckRegisterCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.dataCompany
                    view.getDataCompany(data!!)
                    view.messageGetDataCompany(response.body()?.message.toString())
                } else {
                    view.messageGetDataCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseCheckRegisterCompany>, t: Throwable) {
                view.messageGetDataCompany(t.localizedMessage.toString())
            }

        })
    }

    fun loadListLocation(){
        val loadListLocation = NetworkConfig().getConnectionHaina().getDataListJobLocation()
        loadListLocation.enqueue(object : retrofit2.Callback<ResponseListJobLocation>{
            override fun onResponse(call: Call<ResponseListJobLocation>, response: Response<ResponseListJobLocation>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.loadJobLocation(data)
                    view.messageGetDataCompany(response.body()?.message.toString())
                } else {
                    view.messageGetDataCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseListJobLocation>, t: Throwable) {
                view.messageGetDataCompany(t.localizedMessage.toString())
            }

        })
    }

    fun addImageCompany(imageCompany: MultipartBody.Part, name: RequestBody, idCompany: RequestBody){
        val addImageCompany = NetworkConfig().getConnectionHainaBearer(context).addPhotoCompany(imageCompany, name, idCompany)
        addImageCompany.enqueue(object : retrofit2.Callback<ResponseAddImageCompany>{
            override fun onResponse(call: Call<ResponseAddImageCompany>, response: Response<ResponseAddImageCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddPhotoCompany("Success add photo company")
                } else {
                    view.messageAddPhotoCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseAddImageCompany>, t: Throwable) {
                view.messageAddPhotoCompany(t.localizedMessage.toString())
            }

        })
    }

    fun deleteImageCompany(id:Int){
        val deleteImageCompany = NetworkConfig().getConnectionHainaBearer(context).deleteImageCompany(id)
        deleteImageCompany.enqueue(object : retrofit2.Callback<ResponseDeletedPhotoCompany>{
            override fun onResponse(call: Call<ResponseDeletedPhotoCompany>, response: Response<ResponseDeletedPhotoCompany>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageDeleteImageCompany("1")
                } else {
                    view.messageDeleteImageCompany(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseDeletedPhotoCompany>, t: Throwable) {
                view.messageDeleteImageCompany(t.localizedMessage.toString())
            }

        })
    }

}