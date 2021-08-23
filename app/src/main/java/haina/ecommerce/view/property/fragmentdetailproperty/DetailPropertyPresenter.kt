package haina.ecommerce.view.property.fragmentdetailproperty

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseChangeAvailability
import haina.ecommerce.model.property.ResponseUpdateBookmarkProperty
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class DetailPropertyPresenter(val view:DetailPropertyContract.View, val context: Context) {

    fun changeAvailability(idProperty:Int, transactionType:String){
        view.showLoading()
        val changeAvailability = NetworkConfig().getConnectionHainaBearer(context).changeAvailability(idProperty, transactionType)
        changeAvailability.enqueue(object : retrofit2.Callback<ResponseChangeAvailability>{
            override fun onResponse(call: Call<ResponseChangeAvailability>, response: Response<ResponseChangeAvailability>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageChangeAvailability(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageChangeAvailability(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseChangeAvailability>, t: Throwable) {
                view.dismissLoading()
                view.messageChangeAvailability(t.localizedMessage.toString())
            }
        })
    }

    fun updateBookmark(idProperty:Int, bookmark:String){
        view.showLoading()
        val updateBookmark = NetworkConfig().getConnectionHainaBearer(context).updateBookmarkProperty(idProperty, bookmark)
        updateBookmark.enqueue(object : retrofit2.Callback<ResponseUpdateBookmarkProperty>{
            override fun onResponse(call: Call<ResponseUpdateBookmarkProperty>, response: Response<ResponseUpdateBookmarkProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageUpdateBookmarkProperty(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageUpdateBookmarkProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseUpdateBookmarkProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageUpdateBookmarkProperty(t.localizedMessage.toString())
            }
        })
    }

}