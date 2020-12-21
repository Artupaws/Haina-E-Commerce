package haina.ecommerce.view.posting

import android.content.Context
import android.view.View
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.ResponseGetMyPost
import retrofit2.Call
import retrofit2.Response

class PostingPresenter(val view: PostingContract, val context: Context) {

    fun getDataMyPost() {
        NetworkConfig().getConnectionHainaBearer(context).getMyPost()
                .enqueue(object : retrofit2.Callback<ResponseGetMyPost> {
                    override fun onResponse(call: Call<ResponseGetMyPost>, response: Response<ResponseGetMyPost>) {
                        if (response.isSuccessful && response.body()?.value == 1) {
                            val data = response.body()!!.data
                            view.getListMyPost(data)
                            view.successLoadMyPost(response.body()!!.message.toString())
                        } else {
                            view.errorLoadMyPost(response.body()?.message.toString())
                        }
                    }

                    override fun onFailure(call: Call<ResponseGetMyPost>, t: Throwable) {
                        view.errorLoadMyPost(t.localizedMessage)
                    }

                })
    }

}