package haina.ecommerce.view.property.fragmentshowproperty

import android.content.Context
import haina.ecommerce.api.NetworkConfig
import haina.ecommerce.model.property.ResponseShowProperty
import haina.ecommerce.model.property.ResponseViewDetailProperty
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

class ShowPropertyPresenter(val view:ShowPropertyContract.View, val context: Context) {

    fun getShowProperty(){
        view.showLoading()
        val showProperty = NetworkConfig().getConnectionHainaBearer(context).showProperty()
        showProperty.enqueue(object : retrofit2.Callback<ResponseShowProperty>{
            override fun onResponse(call: Call<ResponseShowProperty>, response: Response<ResponseShowProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageGetListProperty(response.body()?.message.toString())
                    val data = response.body()?.data
                    view.getDataProperty(data)
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageGetListProperty(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseShowProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageGetListProperty(t.localizedMessage.toString())
            }

        })
    }

    fun addViews(idProperty:Int){
        view.showLoading()
        val addViews = NetworkConfig().getConnectionHainaBearer(context).addViewProperty(idProperty)
        addViews.enqueue(object : retrofit2.Callback<ResponseViewDetailProperty>{
            override fun onResponse(call: Call<ResponseViewDetailProperty>, response: Response<ResponseViewDetailProperty>) {
                view.dismissLoading()
                if (response.isSuccessful && response.body()?.value == 1){
                    view.messageAddViews(response.body()?.message.toString())
                } else {
                    val error = JSONObject(response.errorBody()?.string())
                    view.messageAddViews(error.getString("message"))
                }
            }

            override fun onFailure(call: Call<ResponseViewDetailProperty>, t: Throwable) {
                view.dismissLoading()
                view.messageAddViews(t.localizedMessage.toString())
            }
        })
    }

}