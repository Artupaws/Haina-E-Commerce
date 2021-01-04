package haina.ecommerce.view.myaccount.detailaccount

import android.content.Context
import android.util.Log
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseLoadDocumentUser
import retrofit2.Call
import retrofit2.Response

class DetailAccountPresenter(val view:DetailAccountContract, val context: Context) {

    fun loadDocumentResume(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    Log.d("document", response.body()?.data.toString())
                    view.getDocumentResume(data)
                    view.messageLoadDetailUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDetailUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDetailUser(t.localizedMessage.toString())
            }

        })
    }

    fun loadDocumentPortfolio(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDocumentPortfolio(data)
                    view.messageLoadDetailUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDetailUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDetailUser(t.localizedMessage.toString())
            }

        })
    }

    fun loadDocumentCertificate(idDocsCategory:Int){
        val loadDocumentUser = NetworkConfig().getConnectionHainaBearer(context).loadDocumentUser(idDocsCategory)
        loadDocumentUser.enqueue(object : retrofit2.Callback<ResponseLoadDocumentUser>{
            override fun onResponse(call: Call<ResponseLoadDocumentUser>, response: Response<ResponseLoadDocumentUser>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    val data = response.body()?.data
                    view.getDocumentCertificate(data)
                    view.messageLoadDetailUser(response.body()?.message.toString())
                } else {
                    view.messageLoadDetailUser(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseLoadDocumentUser>, t: Throwable) {
                view.messageLoadDetailUser(t.localizedMessage.toString())
            }

        })
    }



}