package haina.ecommerce.view.posting.applyapplicant

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseAddShortlistApplicant
import retrofit2.Call
import retrofit2.Response

class ApplyApplicantPresenter(val view: ApplyApplicantContract, val context: Context) {

    fun addShortlistApplicant(idApplicant:Int, status:String){
        val addShortlistApplicant = NetworkConfig().getConnectionHainaBearer(context).addShortlistApplicant(idApplicant, status)
        addShortlistApplicant.enqueue(object : retrofit2.Callback<ResponseAddShortlistApplicant>{
            override fun onResponse(call: Call<ResponseAddShortlistApplicant>, response: Response<ResponseAddShortlistApplicant>) {
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddShortlistApplicant(response.body()?.message.toString())
                } else {
                    view.messageAddShortlistApplicant(response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<ResponseAddShortlistApplicant>, t: Throwable) {
                view.messageAddShortlistApplicant(t.localizedMessage.toString())
            }

        })
    }

}