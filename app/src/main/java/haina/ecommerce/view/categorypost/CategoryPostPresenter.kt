package haina.ecommerce.view.categorypost

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.categorypost.ResponseGetCategoryPost
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class CategoryPostPresenter(val view:CategoryPostContract.View, val context: Context) {

    fun getCategoryPost(){
        view.showLoading()
        val getCategoryPost = NetworkConfig().getConnectionToDarma(context).getCategoryPost()
        getCategoryPost.enqueue(object : retrofit2.Callback<ResponseGetCategoryPost>{
            override fun onResponse(call: Call<ResponseGetCategoryPost>, response: Response<ResponseGetCategoryPost>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetCategory(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getListCategory(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetCategory(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseGetCategoryPost>, t: Throwable) {
                view.dismissLoading()
                view.messageGetCategory(t.localizedMessage.toString())
            }

        })
    }

}